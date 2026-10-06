package rs.ac.bg.fon.perfumeryproject.dto.impl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import rs.ac.bg.fon.perfumeryproject.dto.Dto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.OrderStatus;
/**
 *
 * @author Milica
 */
public class OrderDto implements Dto {
    
    private Integer id;

    private OrderStatus status;

    private String note;

    private LocalDateTime createdAt;

    @NotNull(message = "userId is required")
    private Integer userId;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Integer id, OrderStatus status, String note, LocalDateTime createdAt,
            Integer userId, List<OrderItemDto> items) {
        this.id = id;
        this.status = status;
        this.note = note;
        this.createdAt = createdAt;
        this.userId = userId;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}