package pl.edziennik.application.command.school.create;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.mock.ResourceCreatorMock;
import pl.edziennik.application.mock.repositories.SchoolCommandMockRepo;
import pl.edziennik.application.mock.repositories.SchoolLevelCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateSchoolCommandValidatorTest {

    private final CreateSchoolCommandValidator validator;
    private final SchoolCommandRepository schoolCommandRepository;
    private final ResourceCreatorMock resourceCreator;
    private final ValidationErrorBuilder validationErrorBuilder;

    public CreateSchoolCommandValidatorTest() {
        this.schoolCommandRepository = new SchoolCommandMockRepo();
        this.validator = new CreateSchoolCommandValidator(schoolCommandRepository);
        this.resourceCreator = new ResourceCreatorMock();
        this.validationErrorBuilder = new ValidationErrorBuilder(resourceCreator);
    }

    @BeforeEach
    public void clearValidator() {
        this.validationErrorBuilder.clear();
    }

    @Test
    public void shouldAddErrorWhenSchoolAlreadyExistsByName() {
        // given
        schoolCommandRepository.save(School.of(Name.of("School"),
                Nip.of("12312"),
                Regon.of("123123"),
                PhoneNumber.of("123123"),
                null,
                SchoolLevel.of(SchoolLevelId.create(), Name.of("TEST"))));

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
        schoolCommandRepository.save(School.of(Name.of("School1"),
                Nip.of("000000000"),
                Regon.of("123123"),
                PhoneNumber.of("123123"),
                null,
                SchoolLevel.of(SchoolLevelId.create(), Name.of("TEST"))));

        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("School"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("000000000"),
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
        schoolCommandRepository.save(School.of(Name.of("School1"),
                Nip.of("000000000"),
                Regon.of("0000000000"),
                PhoneNumber.of("123123"),
                null,
                SchoolLevel.of(SchoolLevelId.create(), Name.of("TEST"))));

        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("School"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("1222"),
                Regon.of("0000000000"),
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
