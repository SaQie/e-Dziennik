package pl.edziennik.eDziennik.server.studensubject.domain.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SubjectGradesResponseDto {

    private final Long id;
    private final String name;
    private final List<GradesDto> grades;

    public SubjectGradesResponseDto(Long id, String name, List<GradesDto> grades) {
        this.id = id;
        this.name = name;
        this.grades = grades;
    }

    @Getter
    public static class GradesDto{

        private final Long id;
        private final int grade;
        private final int weight;
        private final String description;
        private final Long teacher;
        private final LocalDate createdDate;

        public GradesDto(Long id, int grade, int weight, String description, Long teacher, LocalDate createdDate) {
            this.id = id;
            this.grade = grade;
            this.weight = weight;
            this.description = description;
            this.createdDate = createdDate;
            this.teacher = teacher;
        }
    }

}
