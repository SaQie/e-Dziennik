package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.services.SchoolClassService;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/schoolclasses")
public class SchoolClassController {

    private final SchoolClassService service;


    @PostMapping()
    public ResponseEntity<?> createSchoolClass(@RequestBody SchoolClassRequestApiDto dto){
        SchoolClassResponseApiDto newSchoolClass = service.createSchoolClass(dto);
       URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSchoolClass.getId())
                .toUri();
        return ResponseEntity.created(uri).body(uri);
    }

}
