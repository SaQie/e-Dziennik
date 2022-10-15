package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.RatingSubjectStudentService;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentRequestApiDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentResponseApiDto;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ratingsubjectstudents")
public class RatingSubjectStudentController {

    private final RatingSubjectStudentService service;

    @PostMapping()
    public ResponseEntity<RatingSubjectStudentResponseApiDto> assginRatingToStudent(@RequestBody RatingSubjectStudentRequestApiDto dto){
        RatingSubjectStudentResponseApiDto dtoResult = service.assignRatingToStudentSubject(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResult.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dtoResult);
    }

}
