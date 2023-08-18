package pl.edziennik.infrastructure.repository.schoolclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView;
import pl.edziennik.common.view.schoolclass.StudentSummaryForSchoolClassView;
import pl.edziennik.domain.schoolclass.SchoolClass;

import java.util.List;

@RepositoryDefinition(domainClass = SchoolClass.class, idClass = SchoolClassId.class)
public interface SchoolClassQueryRepository {

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.schoolclass.DetailedSchoolClassView(sc.schoolClassId, sc.className, t.teacherId, t.personInformation.fullName)" +
            "FROM SchoolClass sc " +
            "LEFT JOIN sc.teacher t " +
            "WHERE sc.schoolClassId = :schoolClassId ")
    DetailedSchoolClassView getSchoolClassView(SchoolClassId schoolClassId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.view.schoolclass.StudentSummaryForSchoolClassView(s.studentId,s.journalNumber, s.personInformation.fullName) " +
            "FROM Student s " +
            "JOIN s.schoolClass sc " +
            "WHERE sc.schoolClassId = :schoolClassId")
    List<StudentSummaryForSchoolClassView> getSchoolClassStudentsSummaryView(SchoolClassId schoolClassId);


    @Query("SELECT NEW pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView(sc.schoolClassId, sc.className) " +
            "FROM SchoolClass sc " +
            "JOIN sc.school s " +
            "WHERE s.schoolId = :schoolId ")
    Page<SchoolClassSummaryForSchoolView> findAllWithPaginationForSchool(Pageable pageable, SchoolId schoolId);
}

