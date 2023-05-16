package pl.edziennik.application.command.school.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.SchoolLevelCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSchoolCommandValidatorTest extends BaseUnitTest {

    private final CreateSchoolCommandValidator validator;

    public CreateSchoolCommandValidatorTest() {
        this.validator = new CreateSchoolCommandValidator(schoolCommandRepository);
    }

    @Test
    public void shouldAddErrorWhenSchoolAlreadyExistsByName() {
        // given
        School school = createSchool("School", "123123", "123123", address);

        schoolCommandRepository.save(school);

        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("School"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("123123123"),
                Regon.of("123123123"),
                PhoneNumber.of("qweqew"),
                SchoolLevelCommandMockRepo.HIGH_SCHOOL_LEVEL_ID);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), CreateSchoolCommand.NAME);
    }

    @Test
    public void shouldAddErrorWhenSchoolAlreadyExistsByNip() {
        // given
        School school = createSchool("School1", "123123", "123123", address);

        schoolCommandRepository.save(school);

        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("School"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("123123"),
                Regon.of("123123123"),
                PhoneNumber.of("qweqew"),
                SchoolLevelCommandMockRepo.HIGH_SCHOOL_LEVEL_ID);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), CreateSchoolCommand.NIP);
    }

    @Test
    public void shouldAddErrorWhenSchoolAlreadyExistsByRegon() {
        // given
        School school = createSchool("School1", "123123", "00000", address);

        schoolCommandRepository.save(school);

        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("School"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("1222"),
                Regon.of("00000"),
                PhoneNumber.of("qweqew"),
                SchoolLevelCommandMockRepo.HIGH_SCHOOL_LEVEL_ID);

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(1, errors.size());
        assertEquals(errors.get(0).field(), CreateSchoolCommand.REGON);
    }

}
