package rs.ac.bg.fon.perfumeryproject.controller;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.bg.fon.perfumeryproject.dto.impl.PerfumeDto;
import rs.ac.bg.fon.perfumeryproject.service.PerfumeService;
/**
 *
 * @author Milica
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/perfume")
public class PerfumeController {
    private final PerfumeService perfumeService;

    public PerfumeController(PerfumeService perfumeService) {
        this.perfumeService = perfumeService;
    }

    @GetMapping()
    @Operation(summary = "Retrieve all Perfume entities.")
    public ResponseEntity<List<PerfumeDto>> getAll() {
        return new ResponseEntity<>(perfumeService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new Perfume.")
    public ResponseEntity<PerfumeDto> addPerfume(@Valid @RequestBody PerfumeDto perfumeDto) {
        try {
            PerfumeDto saved = perfumeService.create(perfumeDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while saving Perfume " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {
        try {
            perfumeService.deleteById(id);
            return new ResponseEntity<>("Perfume successfully deleted.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Perfume does not exist: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Perfume entity.")
    public ResponseEntity<PerfumeDto> updatePerfume(
            @PathVariable Integer id,
            @Valid @RequestBody PerfumeDto perfumeDto) {
        try {
            perfumeDto.setId(id);
            PerfumeDto updated = perfumeService.update(perfumeDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while updating perfume " + e.getMessage());
        }
    }

    @GetMapping("/brand/{brandId}")
    @Operation(summary = "Retrieve all perfumes for a given brand.")
    public ResponseEntity<List<PerfumeDto>> getByBrand(@PathVariable Integer brandId) {
        List<PerfumeDto> perfumes = perfumeService.findByBrand(brandId);
        return new ResponseEntity<>(perfumes, HttpStatus.OK);
    }
}
