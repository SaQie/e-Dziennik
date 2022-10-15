package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.rating.RatingService;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingRequestApiDto;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService service;

    @PostMapping()
    public ResponseEntity<RatingResponseApiDto> createNewRating(@RequestBody RatingRequestApiDto dto){
        RatingResponseApiDto savedRating = service.addNewRating(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRating.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedRating);
    }

    @GetMapping()
    public ResponseEntity<List<RatingResponseApiDto>> findAllRatings(){
        return ResponseEntity.ok(service.findAllRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseApiDto> findRatingById(@PathVariable Long id){
        return ResponseEntity.ok(service.findRatingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRatingById(@PathVariable Long id){
        service.deleteRatingById(id);
        return ResponseEntity.noContent().build();
    }
}
