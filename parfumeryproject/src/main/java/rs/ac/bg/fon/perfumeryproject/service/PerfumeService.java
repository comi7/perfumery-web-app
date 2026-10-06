package rs.ac.bg.fon.perfumeryproject.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.perfumeryproject.dto.impl.PerfumeDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Perfume;
import rs.ac.bg.fon.perfumeryproject.mapper.impl.PerfumeMapper;
import rs.ac.bg.fon.perfumeryproject.repository.impl.PerfumeRepository;
/**
 *
 * @author Milica
 */
@Service
public class PerfumeService {
    private final PerfumeRepository perfumeRepository;
    private final PerfumeMapper perfumeMapper;

    @Autowired
    public PerfumeService(PerfumeRepository perfumeRepository, PerfumeMapper perfumeMapper) {
        this.perfumeRepository = perfumeRepository;
        this.perfumeMapper = perfumeMapper;
    }

    public List<PerfumeDto> findAll() {
        return perfumeRepository.findAll()
                .stream()
                .map(perfumeMapper::toDto)
                .collect(Collectors.toList());
    }

    public PerfumeDto create(PerfumeDto dto) throws Exception {
        Perfume perfume = perfumeMapper.toEntity(dto);
        perfumeRepository.save(perfume);
        return perfumeMapper.toDto(perfume);
    }

    public void deleteById(Integer id) {
        perfumeRepository.deleteById(id);
    }

    public PerfumeDto update(PerfumeDto dto) {
        Perfume updated = perfumeMapper.toEntity(dto);
        perfumeRepository.save(updated);
        return perfumeMapper.toDto(updated);
    }

    public List<PerfumeDto> findByBrand(Integer brandId) {
        return perfumeRepository.findByBrand(brandId)
                .stream()
                .map(perfumeMapper::toDto)
                .collect(Collectors.toList());
    }
}