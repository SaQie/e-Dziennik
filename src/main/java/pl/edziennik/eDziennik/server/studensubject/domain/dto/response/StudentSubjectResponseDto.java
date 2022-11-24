package pl.edziennik.eDziennik.server.studensubject.domain.dto.response;

import lombok.Getter;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;

import java.util.List;

@Getter
public class StudentSubjectResponseDto {

    private final Long idStudent;
    private final String firstName;
    private final String lastName;

    private final List<SubjectResponseApiDto> subjects;


    public StudentSubjectResponseDto(Long idStudent, String firstName, String lastName, List<SubjectResponseApiDto> subjects) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = subjects;
    }
}
