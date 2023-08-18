package pl.edziennik.infrastructure.repository.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;
import pl.edziennik.domain.teacher.Teacher;

@RepositoryDefinition(domainClass = Teacher.class, idClass = TeacherId.class)
public interface TeacherQueryRepository {

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.teacher.DetailedTeacherView(t.teacherId, u.username, u.email," +
            " t.personInformation.fullName, a.address, a.postalCode, a.city, u.pesel," +
            "t.personInformation.phoneNumber, s.schoolId, s.name) " +
            "FROM Teacher t " +
            "JOIN t.user u " +
            "JOIN t.school s " +
            "JOIN t.address a " +
            "WHERE t.teacherId = :teacherId")
    DetailedTeacherView getTeacherView(TeacherId teacherId);


    @Query("SELECT NEW " +
            "pl.edziennik.common.view.teacher.TeacherSummaryView(t.teacherId, u.userId ,u.username," +
            "t.personInformation.fullName, s.schoolId, s.name) " +
            "FROM Teacher t " +
            "JOIN t.user u " +
            "JOIN t.school s ")
    Page<TeacherSummaryView> findAllWithPagination(Pageable pageable);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView(s.subjectId, s.name, s.description, sc.schoolClassId, sc.className) " +
            "FROM Subject s " +
            "JOIN s.teacher t " +
            "JOIN s.schoolClass sc " +
            "WHERE t.teacherId = :teacherId ")
    Page<TeacherSubjectsSummaryView> getTeacherSubjectsSummaryWithPagination(Pageable pageable, TeacherId teacherId);

}
