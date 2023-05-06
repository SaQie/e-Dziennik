package pl.edziennik.eDziennik.domain.studentsubject.dto.mapper;

import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.schoolclass.dto.mapper.SchoolClassMapper;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.studentsubject.domain.StudentSubject;
import pl.edziennik.eDziennik.domain.studentsubject.dto.response.*;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherSimpleResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.mapper.TeacherMapper;

import java.util.ArrayList;
import java.util.List;

public class StudentSubjectMapper {

    private StudentSubjectMapper() {
    }

    public static StudentGradesInSubjectsDto toAllStudentSubjectRatingDto(List<StudentSubject> entites, Student student) {

        List<SubjectGradesResponseDto> subjectRatingsList = new ArrayList<>();
        for (StudentSubject entity : entites) {
            List<SubjectGradesResponseDto.GradesDto> grades = new ArrayList<>();
            for (Grade grade : entity.getGrades()) {
                grades.add(new SubjectGradesResponseDto.GradesDto(grade.getGradeId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription(), grade.getTeacher().getTeacherId(), grade.getTeacher().getPersonInformation().fullName(), grade.getCreatedDate()));
            }
            Subject subject = entity.getSubject();
            subjectRatingsList.add(new SubjectGradesResponseDto(subject.getSubjectId(), subject.getName(), grades));
        }
        return new StudentGradesInSubjectsDto(student.getStudentId(), student.getPersonInformation().firstName(), student.getPersonInformation().lastName(), subjectRatingsList);
    }

    public static StudentGradesInSubjectResponseApiDto toStudentSubjectRatingsDto(StudentSubject entity) {
        List<SubjectGradesResponseDto.GradesDto> ratings = new ArrayList<>();

        for (Grade grade : entity.getGrades()) {
            ratings.add(new SubjectGradesResponseDto.GradesDto(grade.getGradeId(), grade.getGrade().grade, grade.getWeight(), grade.getDescription(), grade.getTeacher().getTeacherId(), grade.getTeacher().getPersonInformation().fullName(), grade.getCreatedDate()));
        }
        Subject subject = entity.getSubject();
        SubjectGradesResponseDto subjectGradesResponseDto = new SubjectGradesResponseDto(subject.getSubjectId(), subject.getName(), ratings);
        Student student = entity.getStudent();
        return new StudentGradesInSubjectResponseApiDto(student.getStudentId(), student.getPersonInformation().fullName(), subjectGradesResponseDto);
    }

    public static StudentSubjectResponseDto toStudentSubjectResponseDto(StudentSubject entity) {
        Student student = entity.getStudent();
        Subject subject = entity.getSubject();
        TeacherSimpleResponseApiDto teacherDto = subject.getTeacher() != null ? TeacherMapper.toSimpleDto(subject.getTeacher()) : null;
        List<SubjectResponseApiDto> subjects = List.of(new SubjectResponseApiDto(subject.getSubjectId(), subject.getName(), subject.getDescription(), SchoolClassMapper.toSimpleDto(subject.getSchoolClass()), teacherDto));
        return new StudentSubjectResponseDto(student.getStudentId().id(), student.getPersonInformation().firstName(), student.getPersonInformation().lastName(), subjects);
    }


    public static StudentSubjectsResponseDto toStudentSubjectsResponseDto(List<StudentSubject> entites, Student student) {
        List<SubjectResponseApiDto> subjects = new ArrayList<>();
        for (StudentSubject entity : entites) {
            Subject subject = entity.getSubject();
            TeacherSimpleResponseApiDto teacherDto = subject.getTeacher() != null ? TeacherMapper.toSimpleDto(subject.getTeacher()) : null;
            SubjectResponseApiDto subjectDto = new SubjectResponseApiDto(subject.getSubjectId(), subject.getName(), subject.getDescription(), SchoolClassMapper.toSimpleDto(subject.getSchoolClass()), teacherDto);
            subjects.add(subjectDto);
        }

        return new StudentSubjectsResponseDto(student.getStudentId().id(), student.getPersonInformation().firstName(), student.getPersonInformation().lastName(), subjects);
    }


    public static List<StudentGradesInSubjectResponseApiDto> toStudentGradesInSubject(List<StudentSubject> studentSubjects) {
        return studentSubjects.stream().map(studentSubject ->
                // create StudentGradesInSubject that contains student information and specific subject data
                new StudentGradesInSubjectResponseApiDto(
                        studentSubject.getStudent().getStudentId(),
                        studentSubject.getStudent().getPersonInformation().fullName(),
                        // create specific student subject data
                        new SubjectGradesResponseDto(
                                studentSubject.getSubject().getSubjectId(),
                                studentSubject.getSubject().getName(),
                                // Create list of grades data per student subject
                                studentSubject.getGrades()
                                        .stream()
                                        .map(grade -> new SubjectGradesResponseDto.GradesDto(
                                                grade.getGradeId(),
                                                grade.getGrade().grade,
                                                grade.getWeight(),
                                                grade.getDescription(),
                                                grade.getTeacher().getTeacherId(),
                                                grade.getTeacher().getPersonInformation().fullName(),
                                                grade.getCreatedDate()))
                                        .toList())
                )).toList();
    }
}
