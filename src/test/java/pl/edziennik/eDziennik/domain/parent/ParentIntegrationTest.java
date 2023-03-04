package pl.edziennik.eDziennik.domain.parent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.services.ParentService;
import pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.domain.student.StudentIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.services.StudentService;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class ParentIntegrationTest extends BaseTest {


    @Test
    public void shouldSaveNewParent() {
        // given
        // i need to create student first because parent idStudent field is required
        Long idStudent = createBaseStudent();

        ParentRequestApiDto expected = parentUtil.prepareParentRequestApiDto(idStudent);

        // when
        Long id = parentService.register(expected).getId();

        // then
        assertNotNull(id);
        Parent actual = find(Parent.class, id);
        assertEquals(expected.getFirstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.getLastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getPostalCode(), actual.getAddress().getPostalCode());
        assertEquals(expected.getEmail(), actual.getUser().getEmail());
        assertEquals(expected.getUsername(), actual.getUser().getUsername());
        assertEquals(ROLE_PARENT_TEXT, actual.getUser().getRole().getName());
        assertEquals(expected.getIdStudent(), actual.getStudent().getId());
    }

    @Test
    public void shouldDeleteParent() {
        // given
        Long idParent = createBaseParent();

        // when
        parentService.deleteById(idParent);

        // then
        Parent parent = find(Parent.class, idParent);
        assertNull(parent);
    }

    @Test
    public void shouldFindParentById() {
        // given
        Long idParent = createBaseParent();
        Parent expected = find(Parent.class, idParent);

        // when
        ParentResponseApiDto actual = parentService.findById(idParent);

        // then
        assertNotNull(actual);
        assertEquals(expected.getPersonInformation().getFirstName(), actual.getFirstName());
        assertEquals(expected.getPersonInformation().getLastName(), actual.getLastName());
        assertEquals(expected.getAddress().getCity(), actual.getCity());
        assertEquals(expected.getAddress().getPostalCode(), actual.getPostalCode());
        assertEquals(expected.getUser().getEmail(), actual.getEmail());
        assertEquals(expected.getUser().getUsername(), actual.getUsername());
        assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
    }

    @Test
    public void shouldFindListOfParents() {
        // given
        createBaseParent();
        Long idStudent = createBaseStudent();
        ParentRequestApiDto dto = parentUtil.prepareParentRequestApiDto(idStudent, "bubu", "bubub@o2.pl");
        parentService.register(dto);

        // when
        List<ParentResponseApiDto> allParents = parentService.findAll();

        // then
        assertEquals(2, allParents.size());
    }

    @Test
    public void shouldThrowsExceptionWhenStudentHasAlreadyParent() {
        // given
        Long idStudent = createBaseStudent();
        ParentRequestApiDto dto = parentUtil.prepareParentRequestApiDto(idStudent);
        parentService.register(dto);
        ParentRequestApiDto dto2 = parentUtil.prepareParentRequestApiDto(idStudent);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> parentService.register(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(ParentValidators.STUDENT_ALREADY_HAS_PARENT_VALIDATOR, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(ParentRequestApiDto.ID_STUDENT, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(ParentValidators.EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR, getStudentFullName(idStudent));
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsExceptionWhenParentPeselAlreadyExists() {
        // given
        Long idStudent = createBaseStudent();
        Long idStudent2 = createBaseStudent();
        ParentRequestApiDto dto = parentUtil.prepareParentRequestApiDto("000000000", idStudent);
        parentService.register(dto);
        ParentRequestApiDto dto2 = parentUtil.prepareParentRequestApiDto("000000000", idStudent2);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> parentService.register(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(ParentValidators.PARENT_PESEL_NOT_UNIQUE_VALIDATOR, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(ParentRequestApiDto.PESEL, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(ParentValidators.EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS, dto.getPesel());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

}
