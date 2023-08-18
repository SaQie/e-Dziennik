package pl.edziennik.web.query.director;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.director.detailed.GetDetailedDirectorQuery;
import pl.edziennik.common.view.director.DetailedDirectorView;
import pl.edziennik.common.valueobject.id.DirectorId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/directors")
public class DirectorQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{directorId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedDirectorView getDirector(@PathVariable DirectorId directorId) {
        GetDetailedDirectorQuery query = new GetDetailedDirectorQuery(directorId);

        return dispatcher.dispatch(query);
    }

}
