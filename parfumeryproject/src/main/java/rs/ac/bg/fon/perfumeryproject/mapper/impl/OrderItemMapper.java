package rs.ac.bg.fon.perfumeryproject.mapper.impl;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.perfumeryproject.dto.impl.OrderItemDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.OrderItem;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Perfume;
import rs.ac.bg.fon.perfumeryproject.mapper.DtoEntityMapper;
/**
 *
 * @author Milica
 */
@Component
public class OrderItemMapper implements DtoEntityMapper<OrderItemDto, OrderItem> {

    @Override
    public OrderItemDto toDto(OrderItem e) {
        return new OrderItemDto(
                e.getId(),
                e.getPerfume().getId(),
                e.getQuantity(),
                e.getUnitPrice()
        );
    }

    @Override
    public OrderItem toEntity(OrderItemDto t) {
        OrderItem oi = new OrderItem();
        oi.setId(t.getId());
        oi.setPerfume(new Perfume(t.getPerfumeId()));
        oi.setQuantity(t.getQuantity());
        oi.setUnitPrice(t.getUnitPrice());
        return oi;
    }
}