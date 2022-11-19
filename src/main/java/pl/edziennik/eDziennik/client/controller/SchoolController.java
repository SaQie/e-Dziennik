package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolResponseApiDto;
import pl.edziennik.eDziennik.server.school.services.SchoolService;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
class SchoolController {

    private final SchoolService service;

    @PostMapping()
    public ResponseEntity<?> createNewSchool(@RequestBody SchoolRequestApiDto requestApiDto) {
        SchoolResponseApiDto responseApiDto = service.createNewSchool(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(uri);
    }

    @GetMapping()
    public ResponseEntity<List<SchoolResponseApiDto>> findAllSchools(){
        return ResponseEntity.ok(service.findAllSchools());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolResponseApiDto> findSchoolById(@PathVariable Long id){
        SchoolResponseApiDto responseApiDto = service.findSchoolById(id);
        return ResponseEntity.ok(responseApiDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchoolById(@PathVariable Long id){
        service.deleteSchoolById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchool(@PathVariable Long id, @RequestBody SchoolRequestApiDto requestApiDto){
        SchoolResponseApiDto responseApiDto = service.updateSchool(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schools")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)){
            return ResponseEntity.ok(uri);
        }
        return ResponseEntity.created(uri).body(uri);
    }




}
