package rs.ac.bg.fon.perfumeryproject.dto.impl;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import rs.ac.bg.fon.perfumeryproject.dto.Dto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.FragranceType;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Gender;
/**
 *
 * @author Milica
 */
public class PerfumeDto implements Dto {
    
    private Integer id;

    @NotNull(message = "name is required.")
    private String name;

    @NotNull(message = "gender is required.")
    private Gender gender;

    @NotNull(message = "fragranceType is required.")
    private FragranceType fragranceType;

    @NotNull(message = "volumeMl is required.")
    @Positive(message = "volumeMl must be positive")
    private Integer volumeMl;

    @NotNull(message = "price is required.")
    @Positive(message = "price must be positive")
    private BigDecimal price;

    private Integer stockQuantity;

    @NotNull(message = "brandId is required.")
    private Integer brandId;
    
    private String imageUrl;

    
    public PerfumeDto() {
    }

    public PerfumeDto(Integer id, String name, Gender gender, FragranceType fragranceType,
            Integer volumeMl, BigDecimal price, Integer stockQuantity, Integer brandId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.fragranceType = fragranceType;
        this.volumeMl = volumeMl;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandId = brandId;
    }
    
    public PerfumeDto(Integer id, String name, Gender gender, FragranceType fragranceType,
            Integer volumeMl, BigDecimal price, Integer stockQuantity, Integer brandId, String imageUrl) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.fragranceType = fragranceType;
        this.volumeMl = volumeMl;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandId = brandId;
        this.imageUrl = imageUrl;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public FragranceType getFragranceType() {
        return fragranceType;
    }

    public void setFragranceType(FragranceType fragranceType) {
        this.fragranceType = fragranceType;
    }

    public Integer getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(Integer volumeMl) {
        this.volumeMl = volumeMl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}