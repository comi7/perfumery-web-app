package rs.ac.bg.fon.perfumeryproject.mapper.impl;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.perfumeryproject.dto.impl.PerfumeDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Brand;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Perfume;
import rs.ac.bg.fon.perfumeryproject.mapper.DtoEntityMapper;
/**
 *
 * @author Milica
 */
@Component
public class PerfumeMapper implements DtoEntityMapper<PerfumeDto, Perfume> {

    @Override
    public PerfumeDto toDto(Perfume e) {
        if (e == null) return null;
        Integer brandId = e.getBrand() != null ? e.getBrand().getId() : null;
        return new PerfumeDto(
                e.getId(),
                e.getName(),
                e.getGender(),
                e.getFragranceType(),
                e.getVolumeMl(),
                e.getPrice(),
                e.getStockQuantity(),
                brandId,
                e.getImageUrl()
        );
    }

    @Override
    public Perfume toEntity(PerfumeDto t) {
        if (t == null) return null;
        Perfume p = new Perfume();
        p.setId(t.getId());
        p.setName(t.getName());
        p.setGender(t.getGender());
        p.setFragranceType(t.getFragranceType());
        p.setVolumeMl(t.getVolumeMl());
        p.setPrice(t.getPrice());
        p.setStockQuantity(t.getStockQuantity() != null ? t.getStockQuantity() : 0);
        p.setImageUrl(t.getImageUrl());

        Brand brand = t.getBrandId() != null ? new Brand(t.getBrandId()) : null;
        p.setBrand(brand);

        return p;
    }
}