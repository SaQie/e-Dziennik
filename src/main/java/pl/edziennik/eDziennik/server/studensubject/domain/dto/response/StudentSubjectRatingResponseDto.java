package pl.edziennik.eDziennik.server.studensubject.domain.dto.response;

import lombok.Getter;

@Getter
public class StudentSubjectRatingResponseDto {

    private final Long id;

    private final String firstName;
    private final String lastName;

    private final SubjectGradesResponseDto subjects;

    public StudentSubjectRatingResponseDto(Long id, String firstName, String lastName, SubjectGradesResponseDto subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = subjects;
    }


}
