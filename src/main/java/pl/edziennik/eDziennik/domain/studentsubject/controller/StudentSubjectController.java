package pl.edziennik.eDziennik.domain.studentsubject.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.AllStudentsGradesInSubjectsDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentSubjectsResponseDto;
import pl.edziennik.eDziennik.domain.studentsubject.services.StudentSubjectService;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponseCreator;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students/")
public class StudentSubjectController {

    private final StudentSubjectService service;


    @PostMapping("{studentId}/subjects")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Assign student to subject",
            description = "This method assigns subject to specific student")
    public ApiResponse<?> assignStudentToSubject(@RequestBody StudentSubjectRequestDto requestApiDto, @PathVariable StudentId studentId) {
        requestApiDto = new StudentSubjectRequestDto(requestApiDto.subjectId(), studentId);
        StudentSubjectResponseDto responseApiDto = service.assignStudentToSubject(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("{studentId}/subjects")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific student subjects",
            description = "This method returns list of subjects for specific student")
    public ApiResponse<?> getStudentSubjects(@PathVariable StudentId studentId) {
        StudentSubjectsResponseDto responseApiDto = service.getStudentSubjects(studentId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("{studentId}/subjects/{subjectId}/grades")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific student subject grades",
            description = "This method returns list of grades for specific student subject")
    public ApiResponse<?> getStudentSubjectRatings(@PathVariable StudentId studentId, @PathVariable SubjectId subjectId) {
        StudentGradesInSubjectDto responseApiDto = service.getStudentSubjectGrades(studentId, subjectId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping("{studentId}/subjects/grades")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get list of all student grades",
            description = "This method returns list of all specific student grades for all subjects")
    public ApiResponse<?> getStudentAllSubjectsRatings(@PathVariable StudentId studentId) {
        AllStudentsGradesInSubjectsDto responseApiDto = service.getStudentAllSubjectsGrades(studentId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

}
