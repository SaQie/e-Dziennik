package pl.edziennik.eDziennik.domain.student.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.services.StudentService;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basic.page.PageDto;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/students/")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new student")
    public ApiResponse<?> register(@RequestBody StudentRequestApiDto requestApiDto) {
        StudentResponseApiDto responseApiDto = service.register(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.studentId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @DeleteMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> deleteStudent(@PathVariable StudentId studentId) {
        service.deleteStudentById(studentId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Student deleted successfully", uri);
    }

    @GetMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific student",
            description = "Returns specific student information")
    public ApiResponse<?> findStudentById(@PathVariable StudentId studentId) {
        StudentResponseApiDto responseApiDto = service.findStudentById(studentId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get list of students",
            description = "Returns list of all students")
    public ApiResponse<PageDto<StudentResponseApiDto>> findAllStudents(Pageable pageable) {
        PageDto<StudentResponseApiDto> responseApiDtos = service.findAllStudents(pageable);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @PutMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update student",
            description = "This method will update specific student or create new if not exists")
    public ApiResponse<?> updateStudent(@PathVariable StudentId studentId, @RequestBody StudentRequestApiDto requestApiDto) {
        StudentResponseApiDto responseApiDto = service.updateStudent(studentId, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/students")
                .path("/{id}")
                .buildAndExpand(responseApiDto.studentId().id())
                .toUri();
        if (responseApiDto.studentId().equals(studentId)) {
            ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
        }
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }



}
