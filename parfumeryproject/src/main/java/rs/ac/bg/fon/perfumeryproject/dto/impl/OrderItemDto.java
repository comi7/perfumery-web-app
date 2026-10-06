package rs.ac.bg.fon.perfumeryproject.dto.impl;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import rs.ac.bg.fon.perfumeryproject.dto.Dto;
/**
 *
 * @author Milica
 */
public class OrderItemDto implements Dto {
    
    private Integer id;

    @NotNull(message = "perfumeId is required")
    private Integer perfumeId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    // cuvam cenu u trenutku porucivanja (read/write u DTO-u)
    private BigDecimal unitPrice;

    public OrderItemDto() {
    }

    public OrderItemDto(Integer id, Integer perfumeId, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.perfumeId = perfumeId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPerfumeId() {
        return perfumeId;
    }

    public void setPerfumeId(Integer perfumeId) {
        this.perfumeId = perfumeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}