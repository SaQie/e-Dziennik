package pl.edziennik.application;

import liquibase.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.mock.ApplicationEventPublisherMock;
import pl.edziennik.application.mock.ResourceCreatorMock;
import pl.edziennik.application.mock.repositories.*;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.properties.SchoolClassConfigurationProperties;
import pl.edziennik.common.properties.SchoolConfigurationProperties;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.school.SchoolConfiguration;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassConfiguration;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.repository.director.DirectorCommandRepository;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repository.lessonplan.LessonPlanCommandRepository;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolConfigurationCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationCommandRepository;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
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
    protected DirectorCommandRepository directorCommandRepository;
    protected ResourceCreator resourceCreator;
    protected ValidationErrorBuilder validationErrorBuilder;
    protected PasswordEncoder passwordEncoder;
    protected ActivationTokenRepository activationTokenRepository;
    protected StudentSubjectCommandRepository studentSubjectCommandRepository;
    protected SchoolClassConfigurationCommandRepository schoolClassConfigurationCommandRepository;
    protected SchoolConfigurationCommandRepository schoolConfigurationCommandRepository;
    protected PersonInformation personInformation;
    protected Address address;
    protected SchoolConfiguration schoolConfiguration;
    protected SchoolClassConfiguration schoolClassConfiguration;
    protected SchoolConfigurationProperties schoolConfigurationProperties;
    protected SchoolClassConfigurationProperties schoolClassConfigurationProperties;
    protected TeacherScheduleCommandRepository teacherScheduleCommandRepository;
    protected ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;
    protected ClassRoomCommandRepository classRoomCommandRepository;
    protected LessonPlanCommandRepository lessonPlanCommandRepository;

    protected BaseUnitTest() {
        this.gradeCommandRepository = new GradeCommandMockRepo();
        this.parentCommandRepository = new ParentCommandMockRepo();
        this.schoolCommandRepository = new SchoolCommandMockRepo();
        this.schoolClassCommandRepository = new SchoolClassCommandMockRepo();
        this.schoolLevelCommandRepository = new SchoolLevelCommandMockRepo();
        this.studentCommandRepository = new StudentCommandMockRepo();
        this.subjectCommandRepository = new SubjectCommandMockRepo(schoolClassCommandRepository, studentCommandRepository, gradeCommandRepository);
        this.teacherCommandRepository = new TeacherCommandMockRepo();
        this.userCommandRepository = new UserCommandMockRepo();
        this.resourceCreator = new ResourceCreatorMock();
        this.activationTokenRepository = new ActivationTokenMockRepository();
        this.validationErrorBuilder = new ValidationErrorBuilder(resourceCreator);
        this.adminCommandRepository = new AdminCommandMockRepo();
        this.roleCommandRepository = new RoleCommandMockRepo();
        this.studentSubjectCommandRepository = new StudentSubjectMockRepo();
        this.directorCommandRepository = new DirectorCommandMockRepo();
        this.schoolClassConfigurationCommandRepository = new SchoolClassConfigurationCommandMockRepo();
        this.schoolConfigurationCommandRepository = new SchoolConfigurationCommandMockRepo();
        this.classRoomCommandRepository = new ClassRoomCommandMockRepo();
        this.classRoomScheduleCommandRepository = new ClassRoomScheduleCommandMockRepo();
        this.teacherScheduleCommandRepository = new TeacherScheduleCommandMockRepo();
        this.lessonPlanCommandRepository = new LessonPlanCommandMockRepo();

        this.address = Address.of(
                pl.edziennik.common.valueobject.vo.Address.of(StringUtil.randomIdentifer(5)),
                City.of(StringUtil.randomIdentifer(5)),
                PostalCode.of(StringUtil.randomIdentifer(5))
        );
        this.personInformation = PersonInformation.of(
                FirstName.of("Test"),
                LastName.of("Testowy"),
                PhoneNumber.of("123123123")
        );

        SchoolConfigurationProperties schoolConfigurationProperties = new SchoolConfigurationProperties();
        schoolConfigurationProperties.setAverageType(AverageType.ARITHMETIC);
        schoolConfigurationProperties.setMaxLessonTime(TimeFrameDuration.of(60));
        schoolConfigurationProperties.setMinScheduleTime(TimeFrameDuration.of(10));

        this.schoolConfigurationProperties = schoolConfigurationProperties;

        SchoolClassConfigurationProperties schoolClassConfigurationProperties = new SchoolClassConfigurationProperties();
        schoolClassConfigurationProperties.setAutoAssignSubjects(Boolean.TRUE);
        schoolClassConfigurationProperties.setMaxStudentsSize(30);

        this.schoolClassConfigurationProperties = schoolClassConfigurationProperties;

        this.schoolConfiguration = SchoolConfiguration.createConfigFromProperties(schoolConfigurationProperties);
        this.schoolClassConfiguration = SchoolClassConfiguration.createConfigFromProperties(schoolClassConfigurationProperties);

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
                createRole(Name.of(role))
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
                createRole(Name.of(role))
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
                pl.edziennik.common.valueobject.vo.Address.of(StringUtil.randomIdentifer(5)),
                City.of(StringUtil.randomIdentifer(5)),
                PostalCode.of(StringUtil.randomIdentifer(5))
        );
    }

    protected Admin createAdmin(User user) {
        return Admin.of(AdminId.create(), user);
    }


    protected Director createDirector(User user, School school, PersonInformation personInformation, Address address) {
        Director director = Director.of(DirectorId.create(), user, personInformation, address, school);
        directorCommandRepository.save(director);
        return director;
    }

    protected Teacher createTeacher(User user, School school, PersonInformation personInformation, Address address) {
        Teacher teacher = Teacher.of(
                TeacherId.create(),
                user,
                school,
                personInformation,
                address
        );
        school.teachers().add(teacher);
        return teacherCommandRepository.save(teacher);
    }

    protected Student createStudent(User user, School school, SchoolClass schoolClass, PersonInformation personInformation, Address address) {
        Student student = Student.of(
                StudentId.create(),
                user,
                school,
                schoolClass,
                personInformation,
                address
        );
        school.students().add(student);
        if (schoolClass != null) {
            schoolClass.students().add(student);
        }
        return student;
    }

    protected Student createStudent(User user, School school, SchoolClass schoolClass, PersonInformation personInformation, Address address, Parent parent) {
        Student student = createStudent(user, school, schoolClass, personInformation, address);
        student.assignParent(parent);
        return student;
    }

    protected Parent createParent(User user, PersonInformation personInformation, Address address) {
        return Parent.builder()
                .parentId(ParentId.create())
                .user(user)
                .personInformation(personInformation)
                .address(address)
                .build();
    }

    protected Parent createParent(User user, PersonInformation personInformation, Address address, Student student) {
        return Parent.of(
                ParentId.create(),
                user,
                personInformation,
                address,
                student);
    }

    protected SchoolClass createSchoolClass(String name, School school, Teacher teacher) {
        SchoolClass schoolClass = SchoolClass.of(
                SchoolClassId.create(),
                Name.of(name),
                school,
                teacher,
                schoolClassConfigurationProperties
        );
        schoolClassConfigurationCommandRepository.save(schoolClass.schoolClassConfiguration());
        school.schoolClasses().add(schoolClass);
        return schoolClassCommandRepository.save(schoolClass);
    }

    protected Subject createSubject(String name, Teacher teacher, SchoolClass schoolClass) {
        Subject subject = Subject.of(
                SubjectId.create(),
                Name.of(name),
                Description.of(StringUtil.randomIdentifer(5)),
                schoolClass,
                teacher
        );
        schoolClass.subjects().add(subject);
        subject = subjectCommandRepository.save(subject);
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
        StudentSubject studentSubject = StudentSubject.of(
                student,
                subject
        );
        studentSubject = studentSubjectCommandRepository.save(studentSubject);

        return studentSubject;
    }

    protected Grade assignGradeToStudentSubject(StudentSubject studentSubject, Teacher teacher, pl.edziennik.common.enums.Grade grade, Weight weight) {
        Grade gradeEnum = Grade.of(GradeId.create(), grade, weight, Description.of("TEST"), studentSubject, teacher);
        gradeEnum = gradeCommandRepository.save(gradeEnum);
        return gradeEnum;
    }

    protected ClassRoom createClassRoom(String name, School school) {
        ClassRoom classRoom = ClassRoom.of(ClassRoomId.create(), school, ClassRoomName.of(name));
        return classRoomCommandRepository.save(classRoom);
    }

    protected ClassRoomSchedule createClassRoomSchedule(ClassRoom classRoom, TimeFrame timeFrame) {
        ClassRoomSchedule classRoomSchedule = ClassRoomSchedule.builder()
                .timeFrame(timeFrame)
                .description(Description.of("TEST"))
                .classRoom(classRoom)
                .build();

        return classRoomScheduleCommandRepository.save(classRoomSchedule);
    }

    protected TeacherSchedule createTeacherSchedule(Teacher teacher, TimeFrame timeFrame) {
        TeacherSchedule teacherSchedule = TeacherSchedule.builder()
                .teacher(teacher)
                .description(Description.of("TEST"))
                .timeFrame(timeFrame)
                .build();

        return teacherScheduleCommandRepository.save(teacherSchedule);
    }

    protected School createSchool(String name, String nip, String regon, Address address) {
        return School.of(
                SchoolId.create(),
                Name.of(name),
                Nip.of(nip),
                Regon.of(regon),
                PhoneNumber.of(StringUtil.randomIdentifer(5)),
                address,
                schoolLevelCommandRepository.findById(SchoolLevelCommandMockRepo.HIGH_SCHOOL_LEVEL_ID).get(),
                schoolConfigurationProperties
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

    private Role createRole(Name name) {
        return Role.builder()
                .name(name)
                .build();
    }
}
