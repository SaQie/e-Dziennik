package pl.edziennik.eDziennik.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.dto.SchoolDto;
import pl.edziennik.eDziennik.services.SchoolService;

import java.net.URI;

@RestController
@AllArgsConstructor
public class SchoolController {

    private final SchoolService service;

    @PostMapping("/schools")
    public ResponseEntity<SchoolDto> createNewSchool(@RequestBody SchoolDto dto) {
        SchoolDto newSchool = service.createNewSchool(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSchool.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newSchool);
    }

    @GetMapping("/schools/{id}")
    public ResponseEntity<SchoolDto> findSchoolById(@PathVariable Long id){
        SchoolDto findedSchool = service.findSchoolById(id);
        return ResponseEntity.ok(findedSchool);
    }


}
