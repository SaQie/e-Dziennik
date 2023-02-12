package pl.edziennik.eDziennik.domain.studensubject.dto.response;

import lombok.Getter;

@Getter
public class StudentGradesInSubjectDto {

    private final Long id;

    private final String firstName;
    private final String lastName;

    private final SubjectGradesResponseDto subject;

    public StudentGradesInSubjectDto(Long id, String firstName, String lastName, SubjectGradesResponseDto subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subjects;
    }


}
