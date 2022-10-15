package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.SchoolService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
class SchoolController {

    private final SchoolService service;

    @PostMapping()
    public ResponseEntity<SchoolResponseApiDto> createNewSchool(@RequestBody SchoolRequestApiDto dto) {
        SchoolResponseApiDto newSchool = service.createNewSchool(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSchool.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newSchool);
    }

    @GetMapping()
    public ResponseEntity<List<SchoolResponseApiDto>> findAllSchools(){
        return ResponseEntity.ok(service.findAllSchools());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolResponseApiDto> findSchoolById(@PathVariable Long id){
        SchoolResponseApiDto findedSchool = service.findSchoolById(id);
        return ResponseEntity.ok(findedSchool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchoolById(@PathVariable Long id){
        service.deleteSchoolById(id);
        return ResponseEntity.noContent().build();
    }




}
