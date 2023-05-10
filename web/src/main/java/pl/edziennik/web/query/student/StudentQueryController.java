package pl.edziennik.web.query.student;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.student.getlistofstudent.GetListOfStudentQuery;
import pl.edziennik.application.query.student.getstudent.GetStudentQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.StudentDto;
import pl.edziennik.common.valueobject.id.StudentId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students/")
public class StudentQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentDto> getStudentById(@PathVariable StudentId studentId) {
        GetStudentQuery getStudentQuery = new GetStudentQuery(studentId);

        StudentDto studentDto = dispatcher.callHandler(getStudentQuery);

        return ResponseEntity.ok(studentDto);

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<StudentDto>> getAllStudents(Pageable pageable){
        GetListOfStudentQuery getListOfStudentQuery = new GetListOfStudentQuery(pageable);

        PageDto<StudentDto> studentDtoPageDto = dispatcher.callHandler(getListOfStudentQuery);

        return ResponseEntity.ok(studentDtoPageDto);
    }

}
