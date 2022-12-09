package pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper;

import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.*;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;

import java.util.ArrayList;
import java.util.List;

public class StudentSubjectMapper {

    private StudentSubjectMapper() {
    }

    public static AllStudentsGradesInSubjectsDto toAllStudentSubjectRatingDto(List<StudentSubject> entites) {

        List<SubjectGradesResponseDto> subjectRatingsList = new ArrayList<>();
        for (StudentSubject entity : entites){
            List<SubjectGradesResponseDto.GradesDto> grades = new ArrayList<>();
            for (Grade grade : entity.getGrades()) {
                grades.add(new SubjectGradesResponseDto.GradesDto(grade.getId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription(), grade.getTeacher().getId(),grade.getCreatedDate()));
            }
            Subject subject = entity.getSubject();
            subjectRatingsList.add(new SubjectGradesResponseDto(subject.getId(), subject.getName(), grades));
        }
        Student student = entites.get(0).getStudent();
        return new AllStudentsGradesInSubjectsDto(student.getId(), student.getFirstName(), student.getLastName(), subjectRatingsList);
    }

    public static StudentGradesInSubjectDto toStudentSubjectRatingsDto(StudentSubject entity) {
        List<SubjectGradesResponseDto.GradesDto> ratings = new ArrayList<>();

        for (Grade grade : entity.getGrades()) {
            ratings.add(new SubjectGradesResponseDto.GradesDto(grade.getId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription(), grade.getTeacher().getId(), grade.getCreatedDate()));
        }
        Subject subject = entity.getSubject();
        SubjectGradesResponseDto subjectGradesResponseDto = new SubjectGradesResponseDto(subject.getId(), subject.getName(), ratings);
        Student student = entity.getStudent();
        return new StudentGradesInSubjectDto(student.getId(), student.getFirstName(), student.getLastName(), subjectGradesResponseDto);
    }

    public static StudentSubjectResponseDto toStudentSubjectResponseDto(StudentSubject entity){
        Student student = entity.getStudent();
        Subject subject = entity.getSubject();
        Long idTeacher = subject.getTeacher() == null ? null : subject.getTeacher().getId();
        List<SubjectResponseApiDto> subjects = List.of(new SubjectResponseApiDto(subject.getId(), subject.getName(), subject.getDescription(), idTeacher));
        return new StudentSubjectResponseDto(student.getId(), student.getFirstName(),student.getLastName(), subjects);
    }


    public static StudentSubjectsResponseDto toStudentSubjectsResponseDto(List<StudentSubject> entites){
        Student student = entites.get(0).getStudent();
        List<SubjectResponseApiDto> subjects = new ArrayList<>();
        for (StudentSubject entity : entites) {
            Subject subject = entity.getSubject();
            SubjectResponseApiDto subjectDto = new SubjectResponseApiDto(subject.getId(), subject.getName(), subject.getDescription(), subject.getTeacher().getId());
            subjects.add(subjectDto);
        }
        return new StudentSubjectsResponseDto(student.getId(), student.getFirstName(), student.getLastName(), subjects);
    }



}
