package rs.ac.bg.fon.perfumeryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import rs.ac.bg.fon.perfumeryproject.dto.impl.BrandDto;
import rs.ac.bg.fon.perfumeryproject.service.BrandService;

/**
 *
 * @author Milica
 */

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all Brand entities.")
    public ResponseEntity<List<BrandDto>> getAll() {
        return new ResponseEntity<>(brandService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable(value = "id") Integer id) {
        try {
            return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BrandController exception");
        }
    }

    @PostMapping
    @Operation(summary = "Create a new Brand entity.")
    public ResponseEntity<BrandDto> addBrand(@Valid @RequestBody @NotNull BrandDto brandDto) {
        try {
            BrandDto saved = brandService.create(brandDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while saving brand " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id) {
        try {
            brandService.deleteById(id);
            return new ResponseEntity<>("Brand successfully deleted.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Brand does not exist: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // stara metoda
//    @PutMapping("/{id}")
//    @Operation(summary = "Update an existing Brand entity.")
//    public ResponseEntity<BrandDto> updateBrand(
//            @PathVariable Integer id,
//            @Valid @RequestBody BrandDto brandDto) {
//        try {
//            brandDto.setId(id);
//            BrandDto updated = brandService.update(brandDto);
//            return new ResponseEntity<>(updated, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while updating brand " + e.getMessage());
//        }
//    }
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Brand entity.")
    public ResponseEntity<BrandDto> updateBrand(
            @PathVariable Integer id,
            @Valid @RequestBody BrandDto brandDto) {

        try {
            brandDto.setId(id);
            BrandDto updated = brandService.update(brandDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error while updating brand " + e.getMessage()
            );
        }
    }

}
