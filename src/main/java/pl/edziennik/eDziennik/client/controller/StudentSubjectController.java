package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectGradeRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.services.StudentSubjectService;

import java.net.URI;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentSubjectController {

    private final StudentSubjectService service;


    @PatchMapping("/{idStudent}/subjects")
    public ResponseEntity<?> assignStudentToSubject(@RequestBody StudentSubjectRequestDto dto, @PathVariable Long idStudent){
        service.assignStudentToSubject(dto, idStudent);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idStudent}/subjects/{idSubject}/ratings")
    public ResponseEntity<?> assignGradeToStudentSubject(@RequestBody StudentSubjectGradeRequestDto dto, @PathVariable Long idStudent, @PathVariable Long idSubject, Principal teacher){
        service.assignGradeToStudentSubject(idStudent, idSubject, dto, teacher.getName());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand()
                .toUri();
        return ResponseEntity.ok(uri);
    }

    @GetMapping("/{idStudent}/subjects/{idSubject}/ratings")
    public ResponseEntity<StudentSubjectGradesResponseDto> getStudentSubjectRatings(@PathVariable Long idStudent, @PathVariable Long idSubject){
        StudentSubjectGradesResponseDto responseDto = service.getStudentSubjectRatings(idStudent, idSubject);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{idStudent}/subjects/ratings")
    public ResponseEntity<AllStudentSubjectGradesResponseDto> getStudentAllSubjectsRatings(@PathVariable Long idStudent){
        AllStudentSubjectGradesResponseDto responseDto = service.getStudentAllSubjectsRatings(idStudent);
        return ResponseEntity.ok(responseDto);
    }


}
