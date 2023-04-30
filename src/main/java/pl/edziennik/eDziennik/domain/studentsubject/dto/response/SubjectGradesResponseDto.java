package pl.edziennik.eDziennik.domain.studentsubject.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SubjectGradesResponseDto {

    @JsonUnwrapped
    private final SubjectId subjectId;
    private final String name;
    private final List<GradesDto> grades;

    public SubjectGradesResponseDto(SubjectId subjectId, String name, List<GradesDto> grades) {
        this.subjectId = subjectId;
        this.name = name;
        this.grades = grades;
    }

    @Getter
    public static class GradesDto {

        @JsonUnwrapped
        private final GradeId gradeId;
        private final int grade;
        private final int weight;
        private final String description;
        @JsonUnwrapped
        private final TeacherId teacher;
        private final String teacherName;
        private final LocalDate createdDate;

        public GradesDto(GradeId gradeId, int grade, int weight, String description, TeacherId teacher, String teacherName, LocalDate createdDate) {
            this.gradeId = gradeId;
            this.grade = grade;
            this.weight = weight;
            this.description = description;
            this.createdDate = createdDate;
            this.teacher = teacher;
            this.teacherName = teacherName;
        }
    }

}
