package pl.edziennik.application;

import liquibase.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.mock.ResourceCreatorMock;
import pl.edziennik.application.mock.repositories.*;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repositories.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.repositories.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repositories.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repositories.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repositories.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repositories.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repositories.user.UserCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

public class BaseUnitTest {

    protected GradeCommandRepository gradeCommandRepository;
    protected ParentCommandRepository parentCommandRepository;
    protected SchoolCommandRepository schoolCommandRepository;
    protected SchoolClassCommandRepository schoolClassCommandRepository;
    protected SchoolLevelCommandRepository schoolLevelCommandRepository;
    protected StudentCommandRepository studentCommandRepository;
    protected SubjectCommandRepository subjectCommandRepository;
    protected TeacherCommandRepository teacherCommandRepository;
    protected UserCommandRepository userCommandRepository;
    protected RoleCommandRepository roleCommandRepository;
    protected AdminCommandRepository adminCommandRepository;
    protected ResourceCreator resourceCreator;
    protected ValidationErrorBuilder validationErrorBuilder;
    protected PasswordEncoder passwordEncoder;


    protected Address address;

    protected BaseUnitTest() {
        this.gradeCommandRepository = new GradeCommandMockRepo();
        this.parentCommandRepository = new ParentCommandMockRepo();
        this.schoolCommandRepository = new SchoolCommandMockRepo();
        this.schoolClassCommandRepository = new SchoolClassCommandMockRepo();
        this.schoolLevelCommandRepository = new SchoolLevelCommandMockRepo();
        this.studentCommandRepository = new StudentCommandMockRepo();
        this.subjectCommandRepository = new SubjectCommandMockRepo(schoolClassCommandRepository, studentCommandRepository);
        this.teacherCommandRepository = new TeacherCommandMockRepo();
        this.userCommandRepository = new UserCommandMockRepo();
        this.resourceCreator = new ResourceCreatorMock();
        this.validationErrorBuilder = new ValidationErrorBuilder(resourceCreator);
        this.adminCommandRepository = new AdminCommandMockRepo();
        this.roleCommandRepository = new RoleCommandMockRepo();
        this.address = Address.of(
                pl.edziennik.common.valueobject.Address.of(StringUtil.randomIdentifer(5)),
                City.of(StringUtil.randomIdentifer(5)),
                PostalCode.of(StringUtil.randomIdentifer(5))
        );
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }


    @BeforeEach
    public void clearValidator() {
        this.validationErrorBuilder.clear();

    }

    protected User createUser(String username, String email, String role) {
        return User.of(
                Username.of(username),
                Password.of(StringUtil.randomIdentifer(5)),
                Email.of(email),
                Role.of(Name.of(role))
        );
    }

    protected PersonInformation createPersonInformation(String pesel) {
        return PersonInformation.of(
                FirstName.of(StringUtil.randomIdentifer(5)),
                LastName.of(StringUtil.randomIdentifer(5)),
                PhoneNumber.of(StringUtil.randomIdentifer(5)),
                Pesel.of(pesel)
        );
    }

    protected Address createAddress() {
        return Address.of(
                pl.edziennik.common.valueobject.Address.of(StringUtil.randomIdentifer(5)),
                City.of(StringUtil.randomIdentifer(5)),
                PostalCode.of(StringUtil.randomIdentifer(5))
        );
    }

    protected Admin createAdmin(User user) {
        return Admin.of(user);
    }

    protected Teacher createTeacher(User user, School school, PersonInformation personInformation, Address address) {
        Teacher teacher = Teacher.of(
                user,
                school,
                personInformation,
                address
        );
        school.getTeachers().add(teacher);
        return teacher;
    }

    protected Student createStudent(User user, School school, SchoolClass schoolClass, PersonInformation personInformation, Address address) {
        Student student = Student.of(
                user,
                school,
                schoolClass,
                personInformation,
                address
        );
        school.getStudents().add(student);
        if (schoolClass != null) {
            schoolClass.getStudents().add(student);
        }
        return student;
    }

    protected Student createStudent(User user, School school, SchoolClass schoolClass, PersonInformation personInformation, Address address, Parent parent) {
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student.assignParent(parent);
        return student;
    }

    protected Parent createParent(User user, PersonInformation personInformation, Address address) {
        return Parent.of(
                user,
                personInformation,
                address
        );
    }

    protected SchoolClass createSchoolClass(String name, School school, Teacher teacher) {
        SchoolClass schoolClass = SchoolClass.of(
                Name.of(name),
                school,
                teacher
        );
        school.getSchoolClasses().add(schoolClass);
        return schoolClass;
    }

    protected Subject createSubject(String name, Teacher teacher, SchoolClass schoolClass) {
        Subject subject = Subject.of(
                Name.of(name),
                Description.of(StringUtil.randomIdentifer(5)),
                schoolClass,
                teacher
        );
        schoolClass.getSubjects().add(subject);
        return subject;
    }

    protected School createSchool(String name, String nip, String regon, Address address) {
        return School.of(
                Name.of(name),
                Nip.of(nip),
                Regon.of(regon),
                PhoneNumber.of(StringUtil.randomIdentifer(5)),
                address,
                schoolLevelCommandRepository.findById(SchoolLevelCommandMockRepo.HIGH_SCHOOL_LEVEL_ID).get()
        );
    }

    protected String assertBusinessExceptionMessage(String field, String errorMessage, ErrorCode errorCode) {
        return "Business exception occurred, list of errors: [Field: '" + field + "' ]-[ErrorMessage: '" + errorMessage + "' ]-[ErrorCode: '" + errorCode.errorCode() + "' ]\n";
    }


    protected String assertBusinessExceptionMessage(String field) {
        return "Business exception occurred, list of errors: [Field: '" + field + "' ]-[ErrorMessage: 'not.found.message' ]-[ErrorCode: '1000' ]\n";
    }

    protected String assertNotFoundMessage(){
        return "not.found.message";
    }



}
