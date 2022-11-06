package pl.edziennik.eDziennik.teacher;

import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;

/**
 * Util class for Teacher integration tests {@link TeacherIntegrationTest}
 */
class TeacherIntegrationTestUtil {

    protected final String defaultRole = Role.RoleConst.ROLE_TEACHER.name();

    protected TeacherRequestApiDto prepareTeacherRequestDto(){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                null,
                "123123123",
                "asdasd",
                1L
        );
    }


    protected TeacherRequestApiDto prepareTeacherRequestDto(final Long idSchool){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                null,
                "123123123",
                "asdasd",
                idSchool
        );
    }

    protected TeacherRequestApiDto prepareTeacherRequestDto(final Role.RoleConst role){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                role.name(),
                "123123123",
                "asdasd",
                1L
        );
    }

    protected TeacherRequestApiDto prepareTeacherRequestDto(final String role){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                role,
                "123123123",
                "asdasd",
                1L
        );
    }

    protected TeacherRequestApiDto prepareTeacherRequestDto(final String username, final String firstName, final String pesel){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                null,
                "123123123",
                "asdasd",
                1L
        );
    }

}