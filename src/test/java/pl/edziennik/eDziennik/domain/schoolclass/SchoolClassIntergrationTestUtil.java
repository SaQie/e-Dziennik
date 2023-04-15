package pl.edziennik.eDziennik.domain.schoolclass;

import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.user.domain.User;

/**
 * Util class for student integration tests {@link SchoolClassIntegrationTest}
 */
public class SchoolClassIntergrationTestUtil {


    public SchoolClassRequestApiDto prepareSchoolClassRequest() {
        return new SchoolClassRequestApiDto(
                "6B",
                null,
                SchoolId.wrap(100L)
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final String className, final TeacherId teacherId) {
        return new SchoolClassRequestApiDto(
                className,
                teacherId,
                SchoolId.wrap(100L)
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final TeacherId teacherId) {
        return new SchoolClassRequestApiDto(
                "3B",
                teacherId,
                SchoolId.wrap(100L)
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final TeacherId teacherId, final SchoolId schoolId) {
        return new SchoolClassRequestApiDto(
                "3B",
                teacherId,
                schoolId
        );
    }


    public Teacher prepareTeacherForTests(){
        Teacher teacher = new Teacher();
        User user = new User();
        PersonInformation personInformation = new PersonInformation();
        teacher.setPersonInformation(personInformation);
        teacher.setUser(user);
        return teacher;
    }
}