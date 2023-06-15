package pl.edziennik.application;

import liquibase.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.mock.ApplicationEventPublisherMock;
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
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationCommandRepository;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.token.ActivationTokenRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

public class BaseUnitTest {

    protected ApplicationEventPublisher publisher;

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
    protected ActivationTokenRepository activationTokenRepository;
    protected StudentSubjectCommandRepository studentSubjectCommandRepository;
    protected SchoolClassConfigurationCommandRepository schoolClassConfigurationCommandRepository;


    protected PersonInformation personInformation;
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
        this.activationTokenRepository = new ActivationTokenMockRepository();
        this.validationErrorBuilder = new ValidationErrorBuilder(resourceCreator);
        this.adminCommandRepository = new AdminCommandMockRepo();
        this.roleCommandRepository = new RoleCommandMockRepo();
        this.studentSubjectCommandRepository = new StudentSubjectMockRepo();
        this.schoolClassConfigurationCommandRepository = new SchoolClassConfigurationCommandMockRepo();
        this.address = Address.of(
                pl.edziennik.common.valueobject.Address.of(StringUtil.randomIdentifer(5)),
                City.of(StringUtil.randomIdentifer(5)),
                PostalCode.of(StringUtil.randomIdentifer(5))
        );
        this.personInformation = PersonInformation.of(
                FirstName.of("Test"),
                LastName.of("Testowy"),
                PhoneNumber.of("123123123")
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

        this.publisher = new ApplicationEventPublisherMock();


    }


    @BeforeEach
    public void clearValidator() {
        this.validationErrorBuilder.clear();
    }

    protected User createUser(String username, String email, String role) {
        User user = User.of(
                Username.of(username),
                Password.of(StringUtil.randomIdentifer(5)),
                Email.of(email),
                Pesel.of(StringUtil.randomIdentifer(11)),
                Role.of(Name.of(role))
        );

        userCommandRepository.save(user);
        return user;
    }

    protected User createUser(String username, String email, String role, String password) {
        User user = User.of(
                Username.of(username),
                Password.of(password),
                Email.of(email),
                Pesel.of(StringUtil.randomIdentifer(11)),
                Role.of(Name.of(role))
        );

        userCommandRepository.save(user);
        return user;
    }


    protected PersonInformation createPersonInformation() {
        return PersonInformation.of(
                FirstName.of(StringUtil.randomIdentifer(5)),
                LastName.of(StringUtil.randomIdentifer(5)),
                PhoneNumber.of(StringUtil.randomIdentifer(5))
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

    protected Parent createParent(User user, PersonInformation personInformation, Address address, Student student) {
        return Parent.of(
                user,
                personInformation,
                address,
                student);
    }

    protected SchoolClass createSchoolClass(String name, School school, Teacher teacher) {
        SchoolClass schoolClass = SchoolClass.of(
                Name.of(name),
                school,
                teacher
        );
        schoolClassConfigurationCommandRepository.save(schoolClass.getSchoolClassConfiguration());
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

    protected SchoolClass createSchoolWithSchoolClass() {
        School school = createSchool("Testowa", "123123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        user.activate();
        Teacher teacher = createTeacher(user, school, personInformation, address);
        teacher = teacherCommandRepository.save(teacher);

        SchoolClass schoolClass = createSchoolClass("1A", school, teacher);
        schoolClass = schoolClassCommandRepository.save(schoolClass);

        return schoolClass;
    }

    protected StudentSubject createStudentSubject(Student student, Subject subject) {
        return StudentSubject.of(
                student,
                subject
        );
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

    protected Student createStudentWithSchoolAndClass(User user, Parent parent) {
        School school = createSchool("Test", "123123", "123123", address);
        schoolCommandRepository.save(school);
        SchoolClass schoolClass = createSchoolClass("Test", school, null);
        schoolClassCommandRepository.save(schoolClass);

        Student student = createStudent(user, school, schoolClass, personInformation, address, parent);
        return studentCommandRepository.save(student);
    }

    protected String assertBusinessExceptionMessage(String field, String errorMessage, ErrorCode errorCode) {
        return "Business exception occurred, list of errors: [Field: '" + field + "' ]-[ErrorMessage: '" + errorMessage + "' ]-[ErrorCode: '" + errorCode.errorCode() + "' ]\n";
    }


    protected String assertBusinessExceptionMessage(String field) {
        return "Business exception occurred, list of errors: [Field: '" + field + "' ]-[ErrorMessage: 'not.found.message' ]-[ErrorCode: '1000' ]\n";
    }

    protected String assertNotFoundMessage() {
        return "not.found.message";
    }


}
