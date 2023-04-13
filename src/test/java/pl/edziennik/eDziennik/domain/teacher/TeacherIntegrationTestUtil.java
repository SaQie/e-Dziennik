package pl.edziennik.eDziennik.domain.teacher;

import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;

/**
 * Util class for Teacher integration tests {@link TeacherIntegrationTest}
 */
public class TeacherIntegrationTestUtil {

    protected final String defaultRole = Role.RoleConst.ROLE_TEACHER.name();

    public TeacherRequestApiDto prepareTeacherRequestDto(){
        return new TeacherRequestApiDto(
                "Kamil",
                "Kamil",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                "test@example.com",
                null,
                "123123123",
                "asdasd",
                100L
        );
    }

    public TeacherRequestApiDto prepareTeacherRequestDto(String username, String pesel, String email){
        return new TeacherRequestApiDto(
                username,
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                pesel,
                email,
                null,
                "123123123",
                "asdasd",
                100L
        );
    }



    public TeacherRequestApiDto prepareTeacherRequestDto(final Long idSchool){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "12312344",
                "test@examp2le.com",
                null,
                "1231231123",
                "asdasd",
                idSchool
        );
    }

    public TeacherRequestApiDto prepareTeacherRequestDto(final Role.RoleConst role){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                "test@example.com",
                role.name(),
                "123123123",
                "asdasd",
                100L
        );
    }

    public TeacherRequestApiDto prepareTeacherRequestDto(final String role){
        return new TeacherRequestApiDto(
                "Kamil",
                "Nowak",
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                "123123",
                "test@example.com",
                role,
                "123123123",
                "asdasd",
                100L
        );
    }

    public TeacherRequestApiDto prepareTeacherRequestDto(final String username, final String firstName, final String pesel, final String email){
        return new TeacherRequestApiDto(
                username,
                firstName,
                "Nowak",
                "Lubavia",
                "20-200",
                "Warszawa",
                pesel,
                email,
                null,
                "123123123",
                "asdasd",
                100L
        );
    }

}
