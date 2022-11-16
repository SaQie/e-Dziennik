package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.services.SchoolClassService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/schoolclasses")
public class SchoolClassController {

    private final SchoolClassService service;


    @PostMapping()
    public ResponseEntity<?> createSchoolClass(@RequestBody SchoolClassRequestApiDto dto) {
        SchoolClassResponseApiDto newSchoolClass = service.createSchoolClass(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSchoolClass.getId())
                .toUri();
        return ResponseEntity.created(uri).body(uri);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassResponseApiDto> findSchoolClassById(@PathVariable Long id) {
        SchoolClassResponseApiDto findedSchoolClassDto = service.findSchoolClassById(id);
        return ResponseEntity.ok(findedSchoolClassDto);
    }

    @GetMapping()
    public ResponseEntity<List<SchoolClassResponseApiDto>> findAllSchoolClasses(){
        return ResponseEntity.ok(service.findAllSchoolClasses());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchoolClassById(@PathVariable Long id){
        service.deleteSchoolClassById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClassRequestApiDto dto){
        SchoolClassResponseApiDto responseDto = service.updateSchoolClass(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schoolclasses")
                .path("{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();
        if (responseDto.getId().equals(id)){
            return ResponseEntity.ok(uri);
        }
        return ResponseEntity.created(uri).body(uri);
    }


}
