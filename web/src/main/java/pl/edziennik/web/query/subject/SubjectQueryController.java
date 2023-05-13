package pl.edziennik.web.query.subject;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.subject.detailed.GetDetailedSubjectQuery;
import pl.edziennik.common.dto.subject.DetailedSubjectDto;
import pl.edziennik.common.valueobject.id.SubjectId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subjects")
public class SubjectQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailedSubjectDto> getSubject(@PathVariable SubjectId subjectId) {
        GetDetailedSubjectQuery query = new GetDetailedSubjectQuery(subjectId);

        DetailedSubjectDto dto = dispatcher.callHandler(query);

        return ResponseEntity.ok(dto);
    }




}
