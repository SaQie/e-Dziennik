package pl.edziennik;

import liquibase.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repositories.address.AddressCommandRepository;
import pl.edziennik.infrastructure.repositories.address.AddressQueryRepository;
import pl.edziennik.infrastructure.repositories.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.repositories.admin.AdminQueryRepository;
import pl.edziennik.infrastructure.repositories.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repositories.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.repositories.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repositories.parent.ParentQueryRepository;
import pl.edziennik.infrastructure.repositories.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repositories.role.RoleQueryRepository;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.school.SchoolQueryRepository;
import pl.edziennik.infrastructure.repositories.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repositories.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.infrastructure.repositories.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.repositories.schoollevel.SchoolLevelQueryRepository;
import pl.edziennik.infrastructure.repositories.setting.SettingCommandRepository;
import pl.edziennik.infrastructure.repositories.setting.SettingQueryRepository;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repositories.student.StudentQueryRepository;
import pl.edziennik.infrastructure.repositories.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repositories.studentsubject.StudentSubjectQueryRepository;
import pl.edziennik.infrastructure.repositories.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repositories.subject.SubjectQueryRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherQueryRepository;
import pl.edziennik.infrastructure.repositories.user.UserCommandRepository;
import pl.edziennik.infrastructure.repositories.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@EnableJpaRepositories(basePackages = {"pl.edziennik.infrastructure.repositories"})
@EntityScan(basePackages = {"pl.edziennik.domain"})
@ComponentScan(basePackages = {"pl.edziennik.common", "pl.edziennik.infrastructure.spring", "pl.edziennik.application.*"})
@SpringBootTest(classes = BaseIntegrationTest.class)
@AutoConfigureDataJpa
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clearDb.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BaseIntegrationTest extends ContainerEnvironment {

    @MockBean
    protected ResourceCreator resourceCreator;

    @Autowired
    protected AdminCommandRepository adminCommandRepository;
    @Autowired
    protected AdminQueryRepository adminQueryRepository;
    @Autowired
    protected AddressQueryRepository addressQueryRepository;
    @Autowired
    protected AddressCommandRepository addressCommandRepository;
    @Autowired
    protected GradeCommandRepository gradeCommandRepository;
    @Autowired
    protected GradeQueryRepository gradeQueryRepository;
    @Autowired
    protected ParentQueryRepository parentQueryRepository;
    @Autowired
    protected ParentCommandRepository parentCommandRepository;
    @Autowired
    protected RoleCommandRepository roleCommandRepository;
    @Autowired
    protected RoleQueryRepository roleQueryRepository;
    @Autowired
    protected SchoolCommandRepository schoolCommandRepository;
    @Autowired
    protected SchoolQueryRepository schoolQueryRepository;
    @Autowired
    protected SchoolClassQueryRepository schoolClassQueryRepository;
    @Autowired
    protected SchoolClassCommandRepository schoolClassCommandRepository;
    @Autowired
    protected SchoolLevelCommandRepository schoolLevelCommandRepository;
    @Autowired
    protected SchoolLevelQueryRepository schoolLevelQueryRepository;
    @Autowired
    protected SettingQueryRepository settingQueryRepository;
    @Autowired
    protected SettingCommandRepository settingCommandRepository;
    @Autowired
    protected StudentCommandRepository studentCommandRepository;
    @Autowired
    protected StudentQueryRepository studentQueryRepository;
    @Autowired
    protected StudentSubjectQueryRepository studentSubjectQueryRepository;
    @Autowired
    protected StudentSubjectCommandRepository studentSubjectCommandRepository;
    @Autowired
    protected SubjectCommandRepository subjectCommandRepository;
    @Autowired
    protected SubjectQueryRepository subjectQueryRepository;
    @Autowired
    protected TeacherQueryRepository teacherQueryRepository;
    @Autowired
    protected TeacherCommandRepository teacherCommandRepository;
    @Autowired
    protected UserCommandRepository userCommandRepository;
    @Autowired
    protected UserQueryRepository userQueryRepository;

    protected SchoolLevelId primarySchoolLevelId;
    protected SchoolLevelId universitySchoolLevelId;
    protected SchoolLevelId highSchoolLevelId;

    protected final Address address = Address.of(
            pl.edziennik.common.valueobject.Address.of("Test"),
            City.of("Test"),
            PostalCode.of("12-123")
    );

    @BeforeEach
    public void assignValues() {
        this.primarySchoolLevelId = schoolLevelQueryRepository.getByName(Name.of("PRIMARY"));
        this.universitySchoolLevelId = schoolLevelQueryRepository.getByName(Name.of("UNIVERSITY"));
        this.highSchoolLevelId = schoolLevelQueryRepository.getByName(Name.of("HIGH"));

        Mockito.when(resourceCreator.of(Mockito.anyString(), Mockito.any())).thenAnswer(invocation -> invocation.<String>getArgument(0));
        Mockito.when(resourceCreator.of(Mockito.anyString())).thenAnswer(invocation -> invocation.<String>getArgument(0));

        Mockito.when(resourceCreator.notFoundError(Mockito.anyString(), Mockito.any(Identifier.class)))
                .thenAnswer(invocation -> invocation.<String>getArgument(0));
    }

    protected SchoolId createSchool(String name, String nip, String regon) {
        School school = School.of(
                Name.of(name),
                Nip.of(nip),
                Regon.of(regon),
                PhoneNumber.of(StringUtil.randomIdentifer(5)),
                address,
                schoolLevelCommandRepository.findById(primarySchoolLevelId).get()
        );

        return schoolCommandRepository.save(school).getSchoolId();
    }

    public TeacherId createTeacher(String username, String email, String pesel, SchoolId schoolId) {
        User user = User.of(
                Username.of(username),
                Password.of(StringUtil.randomIdentifer(5)),
                Email.of(email),
                Role.of(Name.of("ROLE_TEACHER"))
        );

        School school = schoolCommandRepository.getReferenceById(schoolId);

        PersonInformation personInformation = getPersonInformation(pesel);

        Teacher teacher = Teacher.of(
                user,
                school,
                personInformation,
                address
        );

        return teacherCommandRepository.save(teacher).getTeacherId();
    }

    private PersonInformation getPersonInformation(String pesel) {
        return PersonInformation.of(
                FirstName.of("Test"),
                LastName.of("Test"),
                PhoneNumber.of("123456789"),
                Pesel.of(pesel)
        );
    }

    protected void assignGradeToStudentSubject(StudentId studentId, SubjectId subjectId, TeacherId teacherId) {
        StudentSubject studentSubject = studentSubjectCommandRepository.getReferenceByStudentStudentIdAndSubjectSubjectId(studentId, subjectId);

        Teacher teacher = teacherCommandRepository.getReferenceById(teacherId);

        Grade grade = Grade.of(
                pl.edziennik.common.enums.Grade.SIX,
                Weight.of(1),
                Description.of("asdasd"),
                studentSubject,
                teacher
        );

        gradeCommandRepository.save(grade);

    }

    protected StudentId createStudent(String username, String email, String pesel, SchoolId schoolId, SchoolClassId schoolClassId) {
        User user = User.of(
                Username.of(username),
                Password.of(StringUtil.randomIdentifer(5)),
                Email.of(email),
                Role.of(Name.of("ROLE_STUDENT"))
        );

        PersonInformation personInformation = getPersonInformation(pesel);

        School school = schoolCommandRepository.getReferenceById(schoolId);
        SchoolClass schoolClass = schoolClassCommandRepository.getReferenceById(schoolClassId);

        Student student = Student.of(
                user,
                school,
                schoolClass,
                personInformation,
                address);

        return studentCommandRepository.save(student).getStudentId();
    }

    protected SubjectId createSubject(String name, SchoolClassId schoolClassId, TeacherId teacherId) {

        SchoolClass schoolClass = schoolClassCommandRepository.getReferenceById(schoolClassId);

        Teacher teacher = teacherCommandRepository.getReferenceById(teacherId);

        Subject subject = Subject.of(
                Name.of(name),
                Description.of("asd"),
                schoolClass,
                teacher
        );

        return subjectCommandRepository.save(subject).getSubjectId();
    }

    protected AdminId createAdmin(String username, String email) {
        User user = User.of(
                Username.of(username),
                Password.of(StringUtil.randomIdentifer(5)),
                Email.of(email),
                Role.of(Name.of("ROLE_ADMIN"))
        );

        Admin admin = Admin.of(user);

        return adminCommandRepository.save(admin).getAdminId();
    }

    protected SchoolClassId createSchoolClass(SchoolId schoolId, TeacherId teacherId, String name) {
        School school = schoolCommandRepository.getReferenceById(schoolId);
        Teacher teacher = teacherCommandRepository.getReferenceById(teacherId);

        SchoolClass schoolClass = SchoolClass.of(
                Name.of(name),
                school,
                teacher
        );

        return schoolClassCommandRepository.save(schoolClass).getSchoolClassId();
    }

    protected StudentSubjectId assignStudentToSubject(StudentId studentId, SubjectId subjectId) {

        Student student = studentCommandRepository.getReferenceById(studentId);

        Subject subject = subjectCommandRepository.getReferenceById(subjectId);

        StudentSubject studentSubject = StudentSubject.of(
                student,
                subject
        );

        return studentSubjectCommandRepository.save(studentSubject).getStudentSubjectId();
    }

}
