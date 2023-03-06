package pl.edziennik.eDziennik.domain.student;

import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;

/**
 * Util class for student integration tests {@link StudentIntegrationTest}
 */
public class StudentIntegrationTestUtil {

    public StudentRequestApiDto prepareStudentRequestDto() {
        return new StudentRequestApiDto(
                "Test",
                RandomStringUtils.random(8),
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.random(8),
                "100200300",
                100L,
                100L
        );
    }

    public StudentRequestApiDto prepareStudentRequestDto(String name, String email) {
        return new StudentRequestApiDto(
                "Test",
                name,
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                RandomStringUtils.randomNumeric(9),
                email,
                "100200300",
                100L,
                100L
        );
    }

    public StudentRequestApiDto prepareStudentRequestDto(String name, String email, Long idSchoolClass) {
        return new StudentRequestApiDto(
                "Test",
                name,
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                RandomStringUtils.randomNumeric(9),
                email,
                "100200300",
                100L,
                idSchoolClass
        );
    }

    protected StudentRequestApiDto prepareStudentRequestDto(String username, String firstName, String lastName, String pesel, String email) {
        return new StudentRequestApiDto(
                "Testt",
                username,
                firstName,
                lastName,
                "Lubawka",
                "58-100",
                "Lubawka2",
                pesel,
                email,
                "100200300",
                100L,
                100L
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
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.random(8),
                "100200300",
                idSchool,
                idSchoolClass
        );

    }

    protected StudentRequestApiDto prepareStudentRequestDto(final String pesel) {
        return new StudentRequestApiDto(
                "Test",
                "Test123",
                "Kamil",
                "Nowak",
                "Lubawka",
                "58-100",
                "Lubawka2",
                pesel,
                "test@example.com",
                "100200300",
                100L,
                100L
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
                "test@example.com",
                "100200300",
                idSchool,
                100L
        );
    }
}
