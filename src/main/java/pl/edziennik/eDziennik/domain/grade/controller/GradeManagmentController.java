package pl.edziennik.eDziennik.domain.grade.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.service.managment.GradeManagmentService;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectSeparateId;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.StudentGradesInSubjectDto;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/grade-managment/")
@AllArgsConstructor
public class GradeManagmentController {

    private final GradeManagmentService service;

    @PostMapping("students/{studentId}/subjects/{subjectId}/grades")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<?> assignGradeToStudentSubject(@RequestBody GradeRequestApiDto requestApiDto,
                                                      @ModelAttribute(name = "studentSubjectId") StudentSubjectSeparateId studentSubjectId) {
        StudentGradesInSubjectDto responseApiDto = service.assignGradeToStudentSubject(studentSubjectId, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(studentSubjectId.studentId().id(), studentSubjectId.subjectId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @DeleteMapping("students/{studentId}/subjects/{subjectId}/grades/{gradeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> deleteGradeFromStudentSubject(@ModelAttribute(name = "studentSubjectId") StudentSubjectSeparateId studentSubjectId,
                                                        @PathVariable GradeId gradeId) {
        service.deleteGradeFromStudentSubject(studentSubjectId, gradeId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(studentSubjectId.studentId().id(), studentSubjectId.subjectId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Grade deleted successfully !", uri);
    }

    @PutMapping("students/{studentId}/subjects/{subjectId}/grades/{gradeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> updateStudentSubjectGrade(@ModelAttribute(name = "studentSubjectId") StudentSubjectSeparateId studentSubjectId,
                                                    @PathVariable GradeId gradeId,
                                                    @RequestBody GradeRequestApiDto requestApiDto) {
        // Wywalic to przypisywanie a dodac w serwisie cos co pobierze aktualnego usera i tam doda nazwe nauczyciela a nie tutaj
        StudentGradesInSubjectDto responseApiDto = service.updateStudentSubjectGrade(studentSubjectId, gradeId, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(studentSubjectId.studentId().id(), studentSubjectId.subjectId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }

    @ModelAttribute("studentSubjectId")
    private StudentSubjectSeparateId getStudentSubjectId(@PathVariable StudentId studentId, @PathVariable SubjectId subjectId){
        return StudentSubjectSeparateId.wrap(studentId,subjectId);
    }


}
