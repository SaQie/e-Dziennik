package pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper;

import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.AllStudentSubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.StudentSubjectRatingResponseDto;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.SubjectGradesResponseDto;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;

import java.util.ArrayList;
import java.util.List;

public class StudentSubjectMapper {

    private StudentSubjectMapper() {
    }

    public static AllStudentSubjectGradesResponseDto toAllStudentSubjectRatingDto(List<StudentSubject> entites) {

        List<SubjectGradesResponseDto> subjectRatingsList = new ArrayList<>();
        for (StudentSubject entity : entites){
            List<SubjectGradesResponseDto.GradesDto> grades = new ArrayList<>();
            for (Grade grade : entity.getGrades()) {
                grades.add(new SubjectGradesResponseDto.GradesDto(grade.getId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription()));
            }
            Subject subject = entity.getSubject();
            subjectRatingsList.add(new SubjectGradesResponseDto(subject.getId(), subject.getName(), grades));
        }
        Student student = entites.get(0).getStudent();
        return new AllStudentSubjectGradesResponseDto(student.getId(), student.getFirstName(), student.getLastName(), subjectRatingsList);
    }

    public static StudentSubjectRatingResponseDto toStudentSubjectRatingsDto(StudentSubject entity) {
        List<SubjectGradesResponseDto.GradesDto> ratings = new ArrayList<>();
        for (Grade grade : entity.getGrades()) {
            ratings.add(new SubjectGradesResponseDto.GradesDto(grade.getId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription()));
        }
        Subject subject = entity.getSubject();
        SubjectGradesResponseDto subjectGradesResponseDto = new SubjectGradesResponseDto(subject.getId(), subject.getName(), ratings);
        Student student = entity.getStudent();
        return new StudentSubjectRatingResponseDto(student.getId(), student.getFirstName(), student.getLastName(), subjectGradesResponseDto);
    }

}
