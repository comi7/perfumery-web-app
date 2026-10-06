package rs.ac.bg.fon.perfumeryproject.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.perfumeryproject.dto.impl.BrandDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Brand;
import rs.ac.bg.fon.perfumeryproject.mapper.impl.BrandMapper;
import rs.ac.bg.fon.perfumeryproject.repository.impl.BrandRepository;
/**
 *
 * @author Milica
 */
@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Autowired
    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public List<BrandDto> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    public BrandDto findById(Integer id) throws Exception {
        return brandMapper.toDto(brandRepository.findById(id));
    }

    public BrandDto create(BrandDto dto) {
        Brand brand = brandMapper.toEntity(dto);
        brandRepository.save(brand);
        return brandMapper.toDto(brand);
    }

    public void deleteById(Integer id) {
        brandRepository.deleteById(id);
    }

    // stara metoda
//    public BrandDto update(BrandDto dto) {
//        Brand updated = brandMapper.toEntity(dto);
//        brandRepository.save(updated);
//        return brandMapper.toDto(updated);
//    }
    
    public BrandDto update(BrandDto dto) throws Exception {
        Brand existing = brandRepository.findById(dto.getId());

        if (existing == null) {
            throw new RuntimeException("Brand not found: " + dto.getId());
        }

        existing.setName(dto.getName());
        existing.setCountry(dto.getCountry());
        existing.setImageUrl(dto.getImageUrl());

        brandRepository.save(existing);

        return brandMapper.toDto(existing);
    }

}