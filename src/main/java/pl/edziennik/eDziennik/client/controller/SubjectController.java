package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subject.services.SubjectService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService service;

    @PostMapping()
    public ResponseEntity<?> createSubject(@RequestBody SubjectRequestApiDto dto){
        SubjectResponseApiDto subject = service.createNewSubject(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(subject.getId())
                .toUri();
        return ResponseEntity.created(uri).body(uri);
    }

    @GetMapping()
    public ResponseEntity<List<SubjectResponseApiDto>> findAllSubjects(){
        return ResponseEntity.ok(service.findAllSubjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseApiDto> findSubjectById(@PathVariable Long id){
        SubjectResponseApiDto responseApiDto = service.findSubjectById(id);
        return ResponseEntity.ok(responseApiDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long id){
        service.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable Long id, SubjectRequestApiDto requestApiDto){
        SubjectResponseApiDto responseApiDto = service.updateSubject(id, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/subjects")
                .path("/{id}")
                .buildAndExpand(responseApiDto.getId())
                .toUri();
        if (responseApiDto.getId().equals(id)){
            return ResponseEntity.ok(uri);
        }
        return ResponseEntity.created(uri).body(uri);
    }

}
