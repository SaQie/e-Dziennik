package pl.edziennik.eDziennik.domain.schoolclass;

import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.user.domain.User;

/**
 * Util class for student integration tests {@link SchoolClassIntegrationTest}
 */
public class SchoolClassIntergrationTestUtil {


    public SchoolClassRequestApiDto prepareSchoolClassRequest() {
        return new SchoolClassRequestApiDto(
                "6B",
                null,
                100L
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final String className, final Long idTeacher) {
        return new SchoolClassRequestApiDto(
                className,
                idTeacher,
                100L
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final Long idTeacher) {
        return new SchoolClassRequestApiDto(
                "3B",
                idTeacher,
                100L
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final Long idTeacher, final Long idSchool) {
        return new SchoolClassRequestApiDto(
                "3B",
                idTeacher,
                idSchool
        );
    }


    public Teacher prepareTeacherForTests(){
        Teacher teacher = new Teacher();
        User user = new User();
        PersonInformation personInformation = new PersonInformation();
        user.setPersonInformation(personInformation);
        teacher.setUser(user);
        return teacher;
    }
}