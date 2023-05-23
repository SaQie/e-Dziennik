package pl.edziennik.infrastructure.repository.schoolclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto;
import pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto;
import pl.edziennik.common.dto.schoolclass.StudentSummaryForSchoolClassDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.schoolclass.SchoolClass;

import java.util.List;

@RepositoryDefinition(domainClass = SchoolClass.class, idClass = SchoolClassId.class)
public interface SchoolClassQueryRepository {

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto(sc.schoolClassId, sc.className, t.teacherId, t.personInformation.fullName)" +
            "FROM SchoolClass sc " +
            "LEFT JOIN sc.teacher t " +
            "WHERE sc.schoolClassId = :schoolClassId ")
    DetailedSchoolClassDto getSchoolClass(SchoolClassId schoolClassId);

    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.schoolclass.StudentSummaryForSchoolClassDto(s.studentId, s.personInformation.fullName) " +
            "FROM Student s " +
            "JOIN s.schoolClass sc " +
            "WHERE sc.schoolClassId = :schoolClassId")
    List<StudentSummaryForSchoolClassDto> getSchoolClassStudentsSummary(SchoolClassId schoolClassId);


    @Query("SELECT NEW pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto(sc.schoolClassId, sc.className) " +
            "FROM SchoolClass sc " +
            "JOIN sc.school s " +
            "WHERE s.schoolId = :schoolId ")
    Page<SchoolClassSummaryForSchoolDto> findAllWithPaginationForSchool(Pageable pageable, SchoolId schoolId);
}

