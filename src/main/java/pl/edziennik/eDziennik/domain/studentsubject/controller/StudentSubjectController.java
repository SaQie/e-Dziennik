package pl.edziennik.eDziennik.domain.studentsubject.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.services.StudentSubjectService;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentSubjectController {

    private final StudentSubjectService service;


    @PostMapping("/{idStudent}/subjects")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Assign student to subject",
            description = "This method assigns subject to specific student")
    public ApiResponse<?> assignStudentToSubject(@RequestBody StudentSubjectRequestDto requestApiDto, @PathVariable Long idStudent) {
        requestApiDto.setIdStudent(idStudent);
        StudentSubjectResponseDto responseApiDto = service.assignStudentToSubject(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{idStudent}/subjects")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific student subjects",
            description = "This method returns list of subjects for specific student")
    public ApiResponse<?> getStudentSubjects(@PathVariable Long idStudent) {
        StudentSubjectsResponseDto responseApiDto = service.getStudentSubjects(idStudent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{idStudent}/subjects/{idSubject}/grades")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific student subject grades",
            description = "This method returns list of grades for specific student subject")
    public ApiResponse<?> getStudentSubjectRatings(@PathVariable Long idStudent, @PathVariable Long idSubject) {
        StudentGradesInSubjectDto responseApiDto = service.getStudentSubjectGrades(idStudent, idSubject);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("/{idStudent}/subjects/grades")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get list of all student grades",
            description = "This method returns list of all specific student grades for all subjects")
    public ApiResponse<?> getStudentAllSubjectsRatings(@PathVariable Long idStudent) {
        AllStudentsGradesInSubjectsDto responseApiDto = service.getStudentAllSubjectsGrades(idStudent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

}
