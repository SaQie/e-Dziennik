package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.services.managment.GradeManagmentService;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentGradesInSubjectDto;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/api/grade-managment")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class GradeManagmentController {

    private final GradeManagmentService service;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/students/{idStudent}/subjects/{idSubject}/grades")
    public ResponseEntity<ApiResponse> assignGradeToStudentSubject(@PathVariable Long idStudent, @PathVariable Long idSubject, @RequestBody GradeRequestApiDto requestApiDto, Principal teacher){
        requestApiDto.setTeacherName(teacher.getName());
        StudentGradesInSubjectDto responseApiDto = service.assignGradeToStudentSubject(idStudent, idSubject, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(idStudent,idSubject)
                .toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.OK, responseApiDto, uri));
    }

    @DeleteMapping("/students/{idStudent}/subjects/{idSubject}/grades/{idGrade}")
    public ResponseEntity<ApiResponse> deleteGradeFromStudentSubject(@PathVariable Long idStudent, @PathVariable Long idSubject, @PathVariable Long idGrade){
        service.deleteGradeFromStudentSubject(idStudent,idSubject,idGrade);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(idStudent,idSubject)
                .toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Grade deleted successfully !", uri));
    }

    @PutMapping("/students/{idStudent}/subjects/{idSubject}/grades/{idGrade}")
    public ResponseEntity<ApiResponse> updateStudentSubjectGrade(@PathVariable Long idStudent, @PathVariable Long idSubject, @PathVariable Long idGrade, @RequestBody GradeRequestApiDto requestApiDto, Principal teacher){
        requestApiDto.setTeacherName(teacher.getName());
        StudentGradesInSubjectDto responseApiDto = service.updateStudentSubjectGrade(idStudent, idSubject, idGrade, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students/{idStudent}/subjects/{idSubject}/grades")
                .buildAndExpand(idStudent,idSubject)
                .toUri();
        return ResponseEntity.ok(ApiResponse.buildApiResponse(HttpMethod.PUT,HttpStatus.OK, responseApiDto, uri));
    }
}
