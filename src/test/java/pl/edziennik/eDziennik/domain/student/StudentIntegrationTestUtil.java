package pl.edziennik.eDziennik.domain.student;

import org.apache.commons.lang3.RandomStringUtils;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;

import java.util.ArrayList;
import java.util.List;

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
                SchoolId.wrap(100L),
                SchoolClassId.wrap(100L)
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
                SchoolId.wrap(100L),
                SchoolClassId.wrap(100L)
        );
    }

    public StudentRequestApiDto prepareStudentRequestDto(String name, String email, SchoolClassId schoolClassId) {
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
                SchoolId.wrap(100L),
                schoolClassId
        );
    }

    public StudentRequestApiDto prepareStudentRequestDto(String username, String firstName, String lastName, String pesel, String email) {
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
                SchoolId.wrap(100L),
                SchoolClassId.wrap(100L)
        );
    }

    protected StudentRequestApiDto prepareStudentRequestDto(final SchoolId schoolId, final SchoolClassId schoolClassId) {
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
                schoolId,
                schoolClassId
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
                SchoolId.wrap(100L),
                SchoolClassId.wrap(100L)
        );

    }

    protected StudentRequestApiDto prepareStudentRequestDto(final SchoolId schoolId) {
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
                schoolId,
                SchoolClassId.wrap(100L)
        );
    }

    public List<StudentRequestApiDto> prepareTestsStudents(int studentsToGenerate) {
        List<StudentRequestApiDto> students = new ArrayList<>();
        for (int i = 0; i < studentsToGenerate; i++) {
            StudentRequestApiDto dto = new StudentRequestApiDto(
                    RandomStringUtils.random(5),
                    RandomStringUtils.random(5),
                    RandomStringUtils.random(5),
                    RandomStringUtils.random(5),
                    RandomStringUtils.random(5),
                    RandomStringUtils.random(5),
                    RandomStringUtils.random(5),
                    RandomStringUtils.randomNumeric(9),
                    RandomStringUtils.random(8),
                    RandomStringUtils.randomNumeric(9),
                    SchoolId.wrap(100L),
                    SchoolClassId.wrap(100L)
            );
            students.add(dto);
        }
        return students;
    }
}
