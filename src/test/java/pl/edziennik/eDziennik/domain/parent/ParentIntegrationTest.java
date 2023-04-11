package pl.edziennik.eDziennik.domain.parent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.wrapper.ParentId;
import pl.edziennik.eDziennik.domain.parent.services.validator.ParentValidators;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class ParentIntegrationTest extends BaseTesting {


    @Test
    public void shouldSaveNewParent() {
        // given
        // i need to create student first because parent idStudent field is required
        Long idStudent = createBaseStudent();

        ParentRequestApiDto expected = parentUtil.prepareParentRequestApiDto(idStudent);

        // when
        Long id = parentService.register(expected).id();

        // then
        assertNotNull(id);
        Parent actual = find(Parent.class, id);
        assertEquals(expected.firstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.lastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.postalCode(), actual.getAddress().getPostalCode());
        assertEquals(expected.email(), actual.getUser().getEmail());
        assertEquals(expected.username(), actual.getUser().getUsername());
        assertEquals(ROLE_PARENT_TEXT, actual.getUser().getRole().getName());
        assertEquals(expected.idStudent(), actual.getStudent().getStudentId().id());
    }

    @Test
    public void shouldDeleteParent() {
        // given
        Long idParent = createBaseParent();
        Parent parent = find(Parent.class, idParent);
        // firstly i need to delete student
        studentService.deleteStudentById(parent.getStudent().getStudentId());

        // when
        parentService.deleteById(ParentId.wrap(idParent));

        // then
        Parent actual = find(Parent.class, idParent);
        assertNull(actual);
    }

    @Test
    public void shouldFindParentById() {
        // given
        Long idParent = createBaseParent();
        Parent expected = find(Parent.class, idParent);

        // when
        ParentResponseApiDto actual = parentService.findById(ParentId.wrap(idParent));

        // then
        assertNotNull(actual);
        assertEquals(expected.getPersonInformation().getFirstName(), actual.firstName());
        assertEquals(expected.getPersonInformation().getLastName(), actual.lastName());
        assertEquals(expected.getAddress().getCity(), actual.city());
        assertEquals(expected.getAddress().getPostalCode(), actual.postalCode());
        assertEquals(expected.getUser().getEmail(), actual.email());
        assertEquals(expected.getUser().getUsername(), actual.username());
        assertEquals(expected.getStudent().getStudentId().id(), actual.student().id());
    }

    @Test
    public void shouldFindListOfParents() {
        // given
        createBaseParent();
        Long idStudent = createBaseStudent();
        ParentRequestApiDto dto = parentUtil.prepareParentRequestApiDto(idStudent, "bubu", "bubub@o2.pl");
        parentService.register(dto);

        // when
        List<ParentResponseApiDto> allParents = parentService.findAll(PageRequest.of(0, 20)).getContent();

        // then
        assertEquals(2, allParents.size());
    }

    @Test
    public void shouldUpdateParent(){
        // TODO -> Przemyslec czy mozna zmieniac studenta rodzicowi ?

        // given
//        Long idStudent = createBaseStudent();
//        ParentRequestApiDto dto = parentUtil.prepareParentRequestApiDto();
//        Long id = parentService.register(dto).getId();
//        ParentRequestApiDto expected = parentUtil.prepareParentRequestApiDto("Tomasz", "Nowakowy", idStudent, "asdasd", "ifeife@o2.pl");

        // when
//        ParentResponseApiDto updated = parentService.update(id, expected);

        // then
//        assertNotNull(updated);
//        assertEquals(expected.getFirstName(), updated.getFirstName());
//        assertEquals(expected.getLastName(), updated.getLastName());
//        assertEquals(expected.getEmail(), updated.getEmail());
//        assertEquals(expected.getUsername(), updated.getUsername());

    }

    @Test
    public void shouldThrowExceptionWhenUpdateAndStudentNotExists(){
        Long idParent = createBaseParent();
        ParentRequestApiDto expected = parentUtil.prepareParentRequestApiDto("Tomasz", "Nowakowy", 999L, "asdasd", "ifeife@o2.pl");
        // when

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> parentService.update(ParentId.wrap(idParent), expected));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", expected.idStudent(), Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowExceptionWhenDeleteAndParentStillHasStudent(){
        // given
        Long idParent = createBaseParent();
        Student student = em.find(Parent.class, idParent).getStudent();

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> parentService.deleteById(ParentId.wrap(idParent)));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(ParentValidators.PARENT_STILL_HAS_STUDENT_VALIDATOR, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(ParentRequestApiDto.ID_STUDENT, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(ParentValidators.EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT, getStudentFullName(student.getStudentId().id()));
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());

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
        String expectedExceptionMessage = resourceCreator.of(ParentValidators.EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS, dto.pesel());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

}
