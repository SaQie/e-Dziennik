package pl.edziennik.eDziennik.domain.parent.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;
import pl.edziennik.eDziennik.domain.parent.services.ParentService;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/parents")
@AllArgsConstructor
class ParentController {

    private final ParentService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Add new parent")
    public ApiResponse<?> createNewParent(@Valid @RequestBody ParentRequestApiDto requestApiDto) {
        ParentResponseApiDto responseApiDto = service.register(requestApiDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseApiDto.id())
                .toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.CREATED, responseApiDto, uri);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get parents list",
//            description = "Returns list of all parents")
    public ApiResponse<?> findAllParents(Pageable pageable) {
        PageDto<ParentResponseApiDto> responseApiDtos = service.findAll(pageable);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDtos, uri);
    }

    @GetMapping("/{parentId}")
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Get specific parent",
//            description = "Returns information about specific parent")
    public ApiResponse<?> findParentById(@PathVariable ParentId parentId) {
        ParentResponseApiDto responseApiDto = service.findById(parentId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.GET, HttpStatus.OK, responseApiDto, uri);
    }

    @DeleteMapping("/{parentId}")
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Delete parent")
    public ApiResponse<?> deleteParentById(@PathVariable ParentId parentId) {
        service.deleteById(parentId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.DELETE, HttpStatus.OK, "Parent deleted successfully", uri);
    }

    @PutMapping("/{parentId}")
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Update parent",
//            description = "This method will update specific parent or create new if not exists")
    public ApiResponse<?> updateParentById(@PathVariable ParentId parentId, @RequestBody ParentRequestApiDto dto){
        ParentResponseApiDto responseApiDto = service.update(parentId, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ApiResponseCreator.buildApiResponse(HttpMethod.PUT, HttpStatus.OK, responseApiDto, uri);
    }


}
