package pl.edziennik.eDziennik.domain.teacher.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.services.TeacherService;
import pl.edziennik.eDziennik.server.authentication.AuthCredentials;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/teachers/")
@AllArgsConstructor
class TeacherController {

    private final TeacherService service;

    @PostMapping("/login")
    public void login(@RequestBody AuthCredentials authCredentials) {

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new teacher")
    public ApiResponse<?> register(@RequestBody @Valid TeacherRequestApiDto requestApiDto) {
        TeacherResponseApiDto responseApiDto = service.register(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.teacherId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping("{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get specific teacher",
            description = "Returns specific information about admin")
    public ApiResponse<?> findTeacher(@PathVariable TeacherId teacherId) {
        TeacherResponseApiDto responseApiDto = service.findTeacherById(teacherId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get teacher list",
            description = "Returns list of all teachers")
    public ApiResponse<PageDto<TeacherResponseApiDto>> findAllTeachers(Pageable pageable) {
        PageDto<TeacherResponseApiDto> responseApiDtos = service.findAllTeachers(pageable);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @DeleteMapping("{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete teacher")
    public ApiResponse<?> deleteTeacher(@PathVariable TeacherId teacherId) {
        service.deleteTeacherById(teacherId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Teacher deleted successfully",
                uri);
    }

    @PutMapping("{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update teacher",
            description = "This method will update specific teacher or create new if not exists")
    public ApiResponse<?> updateTeacher(@PathVariable TeacherId teacherId, TeacherRequestApiDto requestApiDto) {
        TeacherResponseApiDto responseApiDto = service.updateTeacher(teacherId, requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/teachers")
                .path("/{id}")
                .buildAndExpand(responseApiDto.teacherId().id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }

}
