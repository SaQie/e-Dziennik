package pl.edziennik.eDziennik.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.RatingSubjectStudentService;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentRequestApiDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;
    private final RatingSubjectStudentService assignRatingService;

    @PostMapping()
    public ResponseEntity<StudentResponseApiDto> register(@RequestBody StudentRequestApiDto dto){
        StudentResponseApiDto savedStudent = service.register(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.getId())
                .toUri();
        return ResponseEntity.created(uri).body(savedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        service.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseApiDto> findStudentById(@PathVariable Long id){
        StudentResponseApiDto dto = service.findStudentById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseApiDto>> findAllStudents(){
        return ResponseEntity.ok(service.findAllStudents());
    }

    @GetMapping("/{studentId}/subjects/ratings")
    public ResponseEntity<?> getStudentsSubjectsRatings(){
        return null;
    }

    @GetMapping("/{studentId}/subjects/{subjectId}/ratings")
    public ResponseEntity<?> getStudentsSubjectRatings(){
        return null;
    }

}
