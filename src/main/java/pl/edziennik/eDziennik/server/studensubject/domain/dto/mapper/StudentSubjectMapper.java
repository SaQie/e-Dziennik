package pl.edziennik.eDziennik.server.studensubject.domain.dto.mapper;

import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.response.*;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherSimpleResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.mapper.TeacherMapper;

import java.util.ArrayList;
import java.util.List;

public class StudentSubjectMapper {

    private StudentSubjectMapper() {
    }

    public static AllStudentsGradesInSubjectsDto toAllStudentSubjectRatingDto(List<StudentSubject> entites, Student student) {

        List<SubjectGradesResponseDto> subjectRatingsList = new ArrayList<>();
        for (StudentSubject entity : entites){
            List<SubjectGradesResponseDto.GradesDto> grades = new ArrayList<>();
            for (Grade grade : entity.getGrades()) {
                grades.add(new SubjectGradesResponseDto.GradesDto(grade.getId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription(), grade.getTeacher().getId(),grade.getCreatedDate()));
            }
            Subject subject = entity.getSubject();
            subjectRatingsList.add(new SubjectGradesResponseDto(subject.getId(), subject.getName(), grades));
        }
        return new AllStudentsGradesInSubjectsDto(student.getId(), student.getUser().getPersonInformation().getFirstName(), student.getUser().getPersonInformation().getLastName(), subjectRatingsList);
    }

    public static StudentGradesInSubjectDto toStudentSubjectRatingsDto(StudentSubject entity) {
        List<SubjectGradesResponseDto.GradesDto> ratings = new ArrayList<>();

        for (Grade grade : entity.getGrades()) {
            ratings.add(new SubjectGradesResponseDto.GradesDto(grade.getId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription(), grade.getTeacher().getId(), grade.getCreatedDate()));
        }
        Subject subject = entity.getSubject();
        SubjectGradesResponseDto subjectGradesResponseDto = new SubjectGradesResponseDto(subject.getId(), subject.getName(), ratings);
        Student student = entity.getStudent();
        return new StudentGradesInSubjectDto(student.getId(), student.getUser().getPersonInformation().getFirstName(), student.getUser().getPersonInformation().getLastName(), subjectGradesResponseDto);
    }

    public static StudentSubjectResponseDto toStudentSubjectResponseDto(StudentSubject entity){
        Student student = entity.getStudent();
        Subject subject = entity.getSubject();
        TeacherSimpleResponseApiDto teacherDto = subject.getTeacher() != null ? TeacherMapper.toSimpleDto(subject.getTeacher()) : null;
        List<SubjectResponseApiDto> subjects = List.of(new SubjectResponseApiDto(subject.getId(), subject.getName(), subject.getDescription(), teacherDto, SchoolClassMapper.toSimpleDto(subject.getSchoolClass())));
        return new StudentSubjectResponseDto(student.getId(), student.getUser().getPersonInformation().getFirstName(), student.getUser().getPersonInformation().getLastName(), subjects);
    }


    public static StudentSubjectsResponseDto toStudentSubjectsResponseDto(List<StudentSubject> entites, Student student){
        List<SubjectResponseApiDto> subjects = new ArrayList<>();
        for (StudentSubject entity : entites) {
            Subject subject = entity.getSubject();
            TeacherSimpleResponseApiDto teacherDto = subject.getTeacher() != null ? TeacherMapper.toSimpleDto(subject.getTeacher()) : null;
            SubjectResponseApiDto subjectDto = new SubjectResponseApiDto(subject.getId(), subject.getName(), subject.getDescription(), teacherDto, SchoolClassMapper.toSimpleDto(subject.getSchoolClass()));
            subjects.add(subjectDto);
        }

        return new StudentSubjectsResponseDto(student.getId(), student.getUser().getPersonInformation().getFirstName(), student.getUser().getPersonInformation().getLastName(), subjects);
    }





}
