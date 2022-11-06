package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.grade.services.GradeService;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeResponseApiDto;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService service;

    @PostMapping()
    public ResponseEntity<?> createNewRating(@RequestBody GradeRequestApiDto dto){
        GradeResponseApiDto savedRating = service.addNewGrade(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRating.getId())
                .toUri();
        return ResponseEntity.created(uri).body(uri);
    }

    @GetMapping()
    public ResponseEntity<List<GradeResponseApiDto>> findAllRatings(){
        return ResponseEntity.ok(service.findAllGrades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseApiDto> findRatingById(@PathVariable Long id){
        return ResponseEntity.ok(service.findGradeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRatingById(@PathVariable Long id){
        service.deleteRatingById(id);
        return ResponseEntity.noContent().build();
    }
}
