package rs.ac.bg.fon.perfumeryproject.mapper.impl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.perfumeryproject.dto.impl.OrderDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Order;
import rs.ac.bg.fon.perfumeryproject.entity.impl.User;
import rs.ac.bg.fon.perfumeryproject.mapper.DtoEntityMapper;
/**
 *
 * @author Milica
 */
@Component
public class OrderMapper implements DtoEntityMapper<OrderDto, Order> {
    private final OrderItemMapper itemMapper;

    public OrderMapper(OrderItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public OrderDto toDto(Order e) {
        List<rs.ac.bg.fon.perfumeryproject.dto.impl.OrderItemDto> items = e.getItems()
                .stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());

        return new OrderDto(
                e.getId(),
                e.getStatus(),
                e.getNote(),
                e.getCreatedAt(),
                e.getUser().getId(),
                items
        );
    }

    @Override
    public Order toEntity(OrderDto t) {
        Order order = new Order();
        order.setId(t.getId());

        if (t.getStatus() != null) {
            order.setStatus(t.getStatus());
        }
        order.setNote(t.getNote());

        if (t.getUserId() != null) {
            order.setUser(new User(t.getUserId()));
        }

        if (t.getItems() != null) {
            t.getItems().forEach(d -> order.addItem(itemMapper.toEntity(d)));
        }
        return order;
    }
}