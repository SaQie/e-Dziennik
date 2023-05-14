package pl.edziennik.application.command.school.delete;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.mock.ResourceCreatorMock;
import pl.edziennik.application.mock.repositories.SchoolCommandMockRepo;
import pl.edziennik.application.mock.repositories.TeacherCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteSchoolCommandValidatorTest {

    private final DeleteSchoolCommandValidator validator;
    private final SchoolCommandRepository schoolCommandRepository;
    private final ValidationErrorBuilder validationErrorBuilder;
    private final TeacherCommandRepository teacherCommandRepository;
    private final ResourceCreator resourceCreator;

    public DeleteSchoolCommandValidatorTest() {
        this.schoolCommandRepository = new SchoolCommandMockRepo();
        this.validator = new DeleteSchoolCommandValidator(schoolCommandRepository);
        this.resourceCreator = new ResourceCreatorMock();
        this.validationErrorBuilder = new ValidationErrorBuilder(resourceCreator);
        this.teacherCommandRepository = new TeacherCommandMockRepo();
    }

    @BeforeEach
    public void clearValidator() {
        this.validationErrorBuilder.clear();
    }

    @Test
    public void shouldThrowErrorWhenSchoolNotExists() {
        // given
        DeleteSchoolCommand command = new DeleteSchoolCommand(SchoolId.create());

        // when
        // then
        assertThrows(BusinessException.class, () -> validator.validate(command, validationErrorBuilder));
    }

    @Test
    public void shouldAddErrorWhenTeachersStillExistsInSchool() {
        // given
        School school = School.of(Name.of("School"),
                Nip.of("12312"),
                Regon.of("123123"),
                PhoneNumber.of("123123"),
                null,
                SchoolLevel.of(SchoolLevelId.create(), Name.of("TEST")));

        schoolCommandRepository.save(school);


        User user = User.of(Username.of("asd"), Password.of("qwe"), Email.of("asd"), Role.of(Name.of("TEACHER")));
        PersonInformation personInformation = PersonInformation.of(FirstName.of("asd"), LastName.of("qwe"), PhoneNumber.of("123123"), Pesel.of("123123"));
        Address address = Address.of(pl.edziennik.common.valueobject.Address.of("qwe"), City.of("qweqe"), PostalCode.of("123"));
        school.getTeachers().add(Teacher.of(user, school, personInformation, address));

        DeleteSchoolCommand deleteSchoolCommand = new DeleteSchoolCommand(school.getSchoolId());
        // when
        validator.validate(deleteSchoolCommand, validationErrorBuilder);

        // then
    }

    @Test
    public void shouldAddErrorWhenStudentsStillExistsInSchool() {
        // given

        // when

        // then
    }

    @Test
    public void shouldAddErrorWhenSchoolClassesStillExistsInSchool() {
        // given

        // when

        // then
    }

}
