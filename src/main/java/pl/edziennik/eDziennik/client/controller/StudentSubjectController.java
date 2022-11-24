package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentGradesInSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.server.studensubject.services.StudentSubjectService;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
@SuppressWarnings("rawtypes")
public class StudentSubjectController {

    private final StudentSubjectService service;


    @PostMapping("/{idStudent}/subjects")
    public ResponseEntity<ApiResponse> assignStudentToSubject(@RequestBody StudentSubjectRequestDto requestApiDto, @PathVariable Long idStudent){
        StudentSubjectResponseDto responseApiDto = service.assignStudentToSubject(requestApiDto, idStudent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri));
    }

    @GetMapping("/{idStudent}/subjects")
    public ResponseEntity<?> getStudentSubjects(@PathVariable Long idStudent){
        StudentSubjectsResponseDto responseApiDto = service.getStudentSubjects(idStudent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri));
    }

    @GetMapping("/{idStudent}/subjects/{idSubject}/grades")
    public ResponseEntity<ApiResponse> getStudentSubjectRatings(@PathVariable Long idStudent, @PathVariable Long idSubject){
        StudentGradesInSubject responseApiDto = service.getStudentSubjectGrades(idStudent, idSubject);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri));
    }

    @GetMapping("/{idStudent}/subjects/grades")
    public ResponseEntity<ApiResponse> getStudentAllSubjectsRatings(@PathVariable Long idStudent){
        AllStudentsGradesInSubjectsDto responseApiDto = service.getStudentAllSubjectsGrades(idStudent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri));
    }

}
