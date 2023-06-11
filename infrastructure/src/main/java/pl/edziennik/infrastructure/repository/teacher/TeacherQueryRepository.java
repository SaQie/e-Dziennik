package pl.edziennik.infrastructure.repository.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.teacher.Teacher;

@RepositoryDefinition(domainClass = Teacher.class, idClass = TeacherId.class)
public interface TeacherQueryRepository {

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.teacher.DetailedTeacherDto(t.teacherId, u.username, u.email," +
            " t.personInformation.fullName, a.address, a.postalCode, a.city, u.pesel," +
            "t.personInformation.phoneNumber, s.schoolId, s.name) " +
            "FROM Teacher t " +
            "JOIN t.user u " +
            "JOIN t.school s " +
            "JOIN t.address a " +
            "WHERE t.teacherId = :teacherId")
    DetailedTeacherDto getTeacher(TeacherId teacherId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.teacher.TeacherSummaryDto(t.teacherId, u.userId ,u.username," +
            "t.personInformation.fullName, s.schoolId, s.name) " +
            "FROM Teacher t " +
            "JOIN t.user u " +
            "JOIN t.school s ")
    Page<TeacherSummaryDto> findAllWithPagination(Pageable pageable);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto(s.subjectId, s.name, s.description, sc.schoolClassId, sc.className) " +
            "FROM Subject s " +
            "JOIN s.teacher t " +
            "JOIN s.schoolClass sc " +
            "WHERE t.teacherId = :teacherId ")
    Page<TeacherSubjectsSummaryDto> getTeacherSubjectsSummaryWithPagination(Pageable pageable,TeacherId teacherId);

}
