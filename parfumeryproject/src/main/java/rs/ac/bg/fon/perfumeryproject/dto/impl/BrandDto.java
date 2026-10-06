package rs.ac.bg.fon.perfumeryproject.dto.impl;
import jakarta.validation.constraints.NotEmpty;
import rs.ac.bg.fon.perfumeryproject.dto.Dto;
/**
 *
 * @author Milica
 */
public class BrandDto implements Dto {

    private Integer id;

    @NotEmpty(message = "name is required.")
    private String name;

    private String country;
    
    private String imageUrl;


    public BrandDto() {
    }

    public BrandDto(Integer id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
    
    public BrandDto(Integer id, String name, String country, String imageUrl) {
    this.id = id;
    this.name = name;
    this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}