package rs.ac.bg.fon.perfumeryproject.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.perfumeryproject.dto.impl.OrderDto;
import rs.ac.bg.fon.perfumeryproject.dto.impl.OrderItemDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Order;
import rs.ac.bg.fon.perfumeryproject.entity.impl.OrderItem;
import rs.ac.bg.fon.perfumeryproject.entity.impl.OrderStatus;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Perfume;
import rs.ac.bg.fon.perfumeryproject.entity.impl.User;
import rs.ac.bg.fon.perfumeryproject.mapper.impl.OrderMapper;
import rs.ac.bg.fon.perfumeryproject.repository.impl.OrderRepository;
import rs.ac.bg.fon.perfumeryproject.repository.impl.UserRepository;
/**
 *
 * @author Milica
 */
@Service
public class OrderService {
    private final OrderRepository orders;
    private final OrderMapper mapper;
    private final PdfService pdfService;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    public OrderService(OrderRepository orders, OrderMapper mapper,
            PdfService pdfService, EmailService emailService,
            UserRepository userRepository) {
        this.orders = orders;
        this.mapper = mapper;
        this.pdfService = pdfService;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public List<OrderDto> findAll() {
        return orders.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public OrderDto findById(Integer id) throws Exception {
        return mapper.toDto(orders.findById(id));
    }

    @Transactional
    public OrderDto create(OrderDto dto) throws Exception {
        Order order = new Order();
        order.setStatus(dto.getStatus() != null ? dto.getStatus() : OrderStatus.CREATED);
        order.setNote(dto.getNote());

        if (dto.getUserId() == null) throw new Exception("userId is required");
        order.setUser(em.getReference(User.class, dto.getUserId()));

        if (dto.getItems() == null || dto.getItems().isEmpty())
            throw new Exception("Order must contain at least one item");

        for (OrderItemDto it : dto.getItems()) {
            OrderItem oi = new OrderItem();
            Perfume p = em.getReference(Perfume.class, it.getPerfumeId());
            oi.setPerfume(p);
            oi.setQuantity(it.getQuantity());

            java.math.BigDecimal price = it.getUnitPrice() != null
                    ? it.getUnitPrice()
                    : p.getPrice();
            oi.setUnitPrice(price);
            order.addItem(oi);
        }

        orders.save(order);
        OrderDto savedDto = mapper.toDto(order);

        // slanje PDF-a na email
        try {
            User user = userRepository.findById(dto.getUserId());
            byte[] pdf = pdfService.generateOrderPdf(savedDto);
            emailService.sendOrderConfirmationEmail(user.getEmail(), pdf, savedDto.getId());
        } catch (Exception e) {
            System.err.println("PDF/email error: " + e.getMessage());
        }

        return savedDto;
    }

    @Transactional
    public OrderDto updateStatus(Integer id, OrderStatus status) throws Exception {
        Order o = orders.findById(id);
        o.setStatus(status);
        orders.save(o);
        return mapper.toDto(o);
    }

    @Transactional
    public void deleteById(Integer id) {
        orders.deleteById(id);
    }
}