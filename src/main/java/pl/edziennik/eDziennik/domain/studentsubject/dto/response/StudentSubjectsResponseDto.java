package pl.edziennik.eDziennik.domain.studentsubject.dto.response;

import lombok.Getter;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;

import java.util.List;

@Getter
public class StudentSubjectsResponseDto {

    private final Long id;

    private final String firstName;
    private final String lastName;

    private final List<SubjectResponseApiDto> subjects;

    public StudentSubjectsResponseDto(Long id, String firstName, String lastName, List<SubjectResponseApiDto> subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = subjects;
    }
}
