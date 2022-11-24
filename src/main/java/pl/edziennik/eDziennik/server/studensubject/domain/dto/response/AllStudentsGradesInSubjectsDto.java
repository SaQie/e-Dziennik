package pl.edziennik.eDziennik.server.studensubject.domain.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class AllStudentsGradesInSubjectsDto {

    private final Long id;

    private final String firstName;
    private final String lastName;

    private final List<SubjectGradesResponseDto> subjects;

    public AllStudentsGradesInSubjectsDto(Long id, String firstName, String lastName, List<SubjectGradesResponseDto> subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = subjects;
    }
}
