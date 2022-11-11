package pl.edziennik.eDziennik.student;

import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;

/**
 * Util class for student integration tests {@link StudentIntegrationTest}
 */
public class StudentIntegrationTestUtil {

    public StudentRequestApiDto prepareStudentRequestDto() {
        return new StudentRequestApiDto(
                "Test",
                "Test123",
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                "123123123",
                "Tomasz",
                "Nowak",
                "100200300",
                1L,
                1L
        );
    }

    protected StudentRequestApiDto prepareStudentRequestDto(String username, String firstName, String lastName, String pesel) {
        return new StudentRequestApiDto(
                "Test",
                "Test123",
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                "123123123",
                "Tomasz",
                "Nowak",
                "100200300",
                1L,
                1L
        );
    }

    protected StudentRequestApiDto prepareStudentRequestDto(final Long idSchool, final Long idSchoolClass) {
        return new StudentRequestApiDto(
                "Test",
                "Test123",
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                "123123123",
                "Tomasz",
                "Nowak",
                "100200300",
                idSchool,
                idSchoolClass
        );

    }

    protected StudentRequestApiDto prepareStudentRequestDto(final Long idSchool) {
        return new StudentRequestApiDto(
                "Test",
                "Test123",
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                "123123123",
                "Tomasz",
                "Nowak",
                "100200300",
                idSchool,
                1L
        );
    }
}
