package pl.edziennik.eDziennik.domain.schoollevel.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.domain.schoollevel.dto.SchoolLevelResponseApiDto;
import pl.edziennik.eDziennik.domain.schoollevel.service.SchoolLevelService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/schoollevels")
public class SchoolLevelController {

    private final SchoolLevelService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get list of available school levels")
    public ApiResponse<?> getSchoolLevelList() {
        List<SchoolLevelResponseApiDto> responseApiDtos = service.getSchoolLevelList();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

}
