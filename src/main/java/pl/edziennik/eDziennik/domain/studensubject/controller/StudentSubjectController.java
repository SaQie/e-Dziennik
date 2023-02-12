package pl.edziennik.eDziennik.domain.studensubject.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.domain.studensubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.domain.studensubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studensubject.services.StudentSubjectService;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentSubjectController {

    private final StudentSubjectService service;


    @PostMapping("/{idStudent}/subjects")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> assignStudentToSubject(@RequestBody StudentSubjectRequestDto requestApiDto, @PathVariable Long idStudent) {
        requestApiDto.setIdStudent(idStudent);
        StudentSubjectResponseDto responseApiDto = service.assignStudentToSubject(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{idStudent}/subjects")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getStudentSubjects(@PathVariable Long idStudent) {
        StudentSubjectsResponseDto responseApiDto = service.getStudentSubjects(idStudent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{idStudent}/subjects/{idSubject}/grades")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getStudentSubjectRatings(@PathVariable Long idStudent, @PathVariable Long idSubject) {
        StudentGradesInSubjectDto responseApiDto = service.getStudentSubjectGrades(idStudent, idSubject);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{idStudent}/subjects/grades")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> getStudentAllSubjectsRatings(@PathVariable Long idStudent) {
        AllStudentsGradesInSubjectsDto responseApiDto = service.getStudentAllSubjectsGrades(idStudent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

}
