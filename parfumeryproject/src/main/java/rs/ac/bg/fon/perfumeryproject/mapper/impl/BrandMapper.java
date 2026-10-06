package rs.ac.bg.fon.perfumeryproject.mapper.impl;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.perfumeryproject.dto.impl.BrandDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Brand;
import rs.ac.bg.fon.perfumeryproject.mapper.DtoEntityMapper;
/**
 *
 * @author Milica
 */
@Component
public class BrandMapper implements DtoEntityMapper<BrandDto, Brand> {

    @Override
    public BrandDto toDto(Brand e) {
        if (e == null) return null;
        return new BrandDto(e.getId(), e.getName(), e.getCountry(), e.getImageUrl());
    }

    @Override
    public Brand toEntity(BrandDto t) {
        if (t == null) return null;
        return new Brand(t.getId(), t.getName(), t.getCountry(), t.getImageUrl());
    }
    
}
