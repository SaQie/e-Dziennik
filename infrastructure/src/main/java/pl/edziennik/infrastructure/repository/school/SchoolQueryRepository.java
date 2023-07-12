package pl.edziennik.infrastructure.repository.school;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.dto.school.DetailedSchoolDto;
import pl.edziennik.common.dto.school.SchoolSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.school.School;

@RepositoryDefinition(domainClass = School.class, idClass = SchoolId.class)
public interface SchoolQueryRepository {


    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.school.SchoolSummaryDto(s.schoolId, s.name, sl.schoolLevelId, sl.name, d.personInformation.fullName) " +
            "FROM School s " +
            "LEFT JOIN s.director d " +
            "JOIN s.schoolLevel sl " +
            "ORDER BY s.name ")
    Page<SchoolSummaryDto> findAllWithPagination(Pageable pageable);


    @Query("SELECT NEW " +
            "pl.edziennik.common.dto.school.DetailedSchoolDto(s.schoolId, s.name, a.postalCode, a.city, s" +
            ".nip, s.regon, a.address, s.phoneNumber, sl.schoolLevelId, sl.name, d.personInformation.fullName)" +
            "FROM School s " +
            "JOIN s.address a " +
            "JOIN s.schoolLevel sl " +
            "LEFT JOIN s.director d " +
            "WHERE s.schoolId = :schoolId")
    DetailedSchoolDto getSchool(SchoolId schoolId);

}
