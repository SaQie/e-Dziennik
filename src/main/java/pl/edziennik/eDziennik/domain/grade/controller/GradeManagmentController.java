package pl.edziennik.eDziennik.domain.grade.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.service.managment.GradeManagmentService;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/grade-managment")
@AllArgsConstructor
public class GradeManagmentController {

    private final GradeManagmentService service;

    @PostMapping("/students/{idStudent}/subjects/{idSubject}/grades")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> assignGradeToStudentSubject(@PathVariable Long idStudent, @PathVariable Long idSubject, @RequestBody GradeRequestApiDto requestApiDto, Principal teacher) {
        requestApiDto.setTeacherName(teacher.getName());
        StudentGradesInSubjectDto responseApiDto = service.assignGradeToStudentSubject(idStudent, idSubject, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(idStudent, idSubject)
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @DeleteMapping("/students/{idStudent}/subjects/{idSubject}/grades/{idGrade}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> deleteGradeFromStudentSubject(@PathVariable Long idStudent, @PathVariable Long idSubject, @PathVariable Long idGrade) {
        service.deleteGradeFromStudentSubject(idStudent, idSubject, idGrade);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(idStudent, idSubject)
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Grade deleted successfully !", uri);
    }

    @PutMapping("/students/{idStudent}/subjects/{idSubject}/grades/{idGrade}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updateStudentSubjectGrade(@PathVariable Long idStudent, @PathVariable Long idSubject, @PathVariable Long idGrade, @RequestBody GradeRequestApiDto requestApiDto, Principal teacher) {
        requestApiDto.setTeacherName(teacher.getName());
        StudentGradesInSubjectDto responseApiDto = service.updateStudentSubjectGrade(idStudent, idSubject, idGrade, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(idStudent, idSubject)
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }
}
