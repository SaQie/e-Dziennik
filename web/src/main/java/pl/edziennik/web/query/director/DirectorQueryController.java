package pl.edziennik.web.query.director;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.director.DirectorQueryDao;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.view.director.DetailedDirectorView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/directors")
@Tag(name = "Director API")
public class DirectorQueryController {

    private final DirectorQueryDao dao;

    @Operation(summary = "Get detailed information about given director")
    @GetMapping("/{directorId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedDirectorView getDirector(@PathVariable DirectorId directorId) {
        return dao.getDetailedDirectorView(directorId);
    }

}
