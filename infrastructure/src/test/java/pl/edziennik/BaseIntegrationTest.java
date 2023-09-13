package pl.edziennik;

import jakarta.persistence.EntityManager;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import pl.edziennik.common.properties.SchoolClassConfigurationProperties;
import pl.edziennik.common.properties.SchoolConfigurationProperties;
import pl.edziennik.common.valueobject.base.Identifier;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.chat.ChatMessage;
import pl.edziennik.domain.chat.ChatRoom;
import pl.edziennik.domain.chat.MessageStatus;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.domain.groovy.GroovyScript;
import pl.edziennik.domain.groovy.GroovyScriptResult;
import pl.edziennik.domain.groovy.GroovyScriptStatus;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.studentsubject.StudentSubject;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.address.AddressCommandRepository;
import pl.edziennik.infrastructure.repository.address.AddressQueryRepository;
import pl.edziennik.infrastructure.repository.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.repository.admin.AdminQueryRepository;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageCommandRepository;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageQueryRepository;
import pl.edziennik.infrastructure.repository.chat.chatroom.ChatRoomCommandRepository;
import pl.edziennik.infrastructure.repository.chat.messagestatus.MessageStatusQueryRepository;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomQueryRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleQueryRepository;
import pl.edziennik.infrastructure.repository.director.DirectorCommandRepository;
import pl.edziennik.infrastructure.repository.director.DirectorQueryRepository;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.repository.grade.GradeQueryRepository;
import pl.edziennik.infrastructure.repository.groovy.GroovyScriptCommandRepository;
import pl.edziennik.infrastructure.repository.groovy.GroovyScriptQueryRepository;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultCommandRepository;
import pl.edziennik.infrastructure.repository.groovy.result.GroovyScriptResultQueryRepository;
import pl.edziennik.infrastructure.repository.groovy.status.GroovyScriptStatusQueryRepository;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.parent.ParentQueryRepository;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repository.role.RoleQueryRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolConfigurationCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolConfigurationQueryRepository;
import pl.edziennik.infrastructure.repository.school.SchoolQueryRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassConfigurationQueryRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelCommandRepository;
import pl.edziennik.infrastructure.repository.schoollevel.SchoolLevelQueryRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentQueryRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectCommandRepository;
import pl.edziennik.infrastructure.repository.studentsubject.StudentSubjectQueryRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectQueryRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherQueryRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleQueryRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.repository.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableJpaRepositories(basePackages = {"pl.edziennik.infrastructure.repository"})
@EntityScan(basePackages = {"pl.edziennik.domain"})
@ComponentScan(basePackages = {"pl.edziennik.common", "pl.edziennik.infrastructure.spring", "pl.edziennik.infrastructure.spring.cache.*", "pl.edziennik.application.*"})
@SpringBootTest(classes = {BaseIntegrationTest.class, RedisCacheTestConfig.class})
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
    protected DirectorCommandRepository directorCommandRepository;
    @Autowired
    protected DirectorQueryRepository directorQueryRepository;
    @Autowired
    protected UserCommandRepository userCommandRepository;
    @Autowired
    protected UserQueryRepository userQueryRepository;
    @Autowired
    protected SchoolClassConfigurationCommandRepository schoolClassConfigurationCommandRepository;
    @Autowired
    protected SchoolClassConfigurationQueryRepository schoolClassConfigurationQueryRepository;
    @Autowired
    protected SchoolConfigurationQueryRepository schoolConfigurationQueryRepository;
    @Autowired
    protected SchoolConfigurationCommandRepository schoolConfigurationCommandRepository;
    @Autowired
    protected PlatformTransactionManager transactionManager;
    @Autowired
    protected SchoolClassConfigurationProperties schoolClassConfigurationProperties;
    @Autowired
    protected SchoolConfigurationProperties schoolConfigurationProperties;
    @Autowired
    protected GroovyScriptCommandRepository groovyScriptCommandRepository;
    @Autowired
    protected GroovyScriptResultCommandRepository groovyScriptResultCommandRepository;
    @Autowired
    protected GroovyScriptStatusQueryRepository groovyScriptStatusQueryRepository;
    @Autowired
    protected GroovyScriptResultQueryRepository groovyScriptResultQueryRepository;
    @Autowired
    protected GroovyScriptQueryRepository groovyScriptQueryRepository;
    @Autowired
    protected ChatMessageQueryRepository chatMessageQueryRepository;
    @Autowired
    protected ChatMessageCommandRepository chatMessageCommandRepository;
    @Autowired
    protected ChatRoomCommandRepository chatRoomCommandRepository;
    @Autowired
    protected MessageStatusQueryRepository messageStatusQueryRepository;
    @Autowired
    protected TeacherScheduleQueryRepository teacherScheduleQueryRepository;
    @Autowired
    protected TeacherScheduleCommandRepository teacherScheduleCommandRepository;
    @Autowired
    protected ClassRoomScheduleQueryRepository classRoomScheduleQueryRepository;
    @Autowired
    protected ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;
    @Autowired
    protected ClassRoomCommandRepository classRoomCommandRepository;
    @Autowired
    protected ClassRoomQueryRepository classRoomQueryRepository;

    @Autowired
    protected EntityManager entityManager;

    protected TransactionTemplate transactionTemplate;

    protected Role teacherRole;
    protected Role studentRole;
    protected Role parentRole;
    protected Role adminRole;
    protected Role directorRole;

    protected GroovyScriptStatus executingGroovyScriptStatus;
    protected GroovyScriptStatus errorGroovyScriptStatus;
    protected GroovyScriptStatus successGroovyScriptStatus;

    protected MessageStatus deliveredMessageStatus;
    protected MessageStatus receivedMessageStatus;

    protected final Address address = Address.of(
            pl.edziennik.common.valueobject.vo.Address.of("Test"),
            City.of("Test"),
            PostalCode.of("12-123")
    );

    @BeforeEach
    public void assignValues() {
        this.teacherRole = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_TEACHER);
        this.studentRole = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_STUDENT);
        this.parentRole = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_PARENT);
        this.adminRole = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_ADMIN);
        this.directorRole = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_DIRECTOR);

        this.executingGroovyScriptStatus = groovyScriptStatusQueryRepository.getByGroovyScriptStatusId(GroovyScriptStatusId.PredefinedRow.EXECUTING);
        this.errorGroovyScriptStatus = groovyScriptStatusQueryRepository.getByGroovyScriptStatusId(GroovyScriptStatusId.PredefinedRow.ERROR);
        this.successGroovyScriptStatus = groovyScriptStatusQueryRepository.getByGroovyScriptStatusId(GroovyScriptStatusId.PredefinedRow.SUCCESS);

        this.deliveredMessageStatus = messageStatusQueryRepository.getByMessageStatusId(MessageStatusId.PredefinedRow.DELIVERED);
        this.receivedMessageStatus = messageStatusQueryRepository.getByMessageStatusId(MessageStatusId.PredefinedRow.RECEIVED);

        this.transactionTemplate = new TransactionTemplate(transactionManager);

        Mockito.when(resourceCreator.of(Mockito.anyString(), Mockito.any())).thenAnswer(invocation -> invocation.<String>getArgument(0));
        Mockito.when(resourceCreator.of(Mockito.anyString())).thenAnswer(invocation -> invocation.<String>getArgument(0));

        Mockito.when(resourceCreator.notFoundError(Mockito.anyString(), Mockito.any(Identifier.class)))
                .thenAnswer(invocation -> invocation.<String>getArgument(0));
    }

    protected SchoolId createSchool(String name, String nip, String regon) {
        SchoolId schoolId = SchoolId.create();
        School school = School.of(
                schoolId,
                Name.of(name),
                Nip.of(nip),
                Regon.of(regon),
                PhoneNumber.of(StringUtil.randomIdentifer(5)),
                createAddress(),
                schoolLevelCommandRepository.findById(SchoolLevelId.PredefinedRow.PRIMARY_SCHOOL).get(),
                schoolConfigurationProperties
        );

        return schoolCommandRepository.save(school).schoolId();
    }

    public DirectorId createDirector(String username, String email, String pesel, SchoolId schoolId) {
        return transactionTemplate.execute((result) -> {
            User user = User.of(
                    Username.of(username),
                    Password.of(StringUtil.randomIdentifer(5)),
                    Email.of(email),
                    Pesel.of(pesel),
                    teacherRole
            );

            School school = schoolCommandRepository.getReferenceById(schoolId);

            PersonInformation personInformation = getPersonInformation(pesel);
            DirectorId directorId = DirectorId.create();

            Director director = Director.of(
                    directorId,
                    user,
                    personInformation,
                    createAddress(),
                    school
            );

            return directorCommandRepository.save(director).directorId();
        });
    }

    public TeacherId createTeacher(String username, String email, String pesel, SchoolId schoolId) {
        return transactionTemplate.execute((result) -> {
            User user = User.of(
                    Username.of(username),
                    Password.of(StringUtil.randomIdentifer(5)),
                    Email.of(email),
                    Pesel.of(pesel),
                    teacherRole
            );

            School school = schoolCommandRepository.getReferenceById(schoolId);

            PersonInformation personInformation = getPersonInformation(pesel);

            TeacherId teacherId = TeacherId.create();
            Teacher teacher = Teacher.of(
                    teacherId,
                    user,
                    school,
                    personInformation,
                    createAddress()
            );

            return teacherCommandRepository.save(teacher).teacherId();
        });
    }

    private PersonInformation getPersonInformation(String pesel) {
        return PersonInformation.of(
                FirstName.of("Test"),
                LastName.of("Test"),
                PhoneNumber.of("123456789")
        );
    }

    protected void assignGradeToStudentSubject(pl.edziennik.common.enums.Grade grade, StudentId studentId, SubjectId subjectId, TeacherId teacherId) {
        StudentSubject studentSubject = studentSubjectCommandRepository.getReferenceByStudentStudentIdAndSubjectSubjectId(studentId, subjectId);

        Teacher teacher = teacherCommandRepository.getReferenceById(teacherId);

        GradeId gradeId = GradeId.create();
        Grade gradeEntity = Grade.of(
                gradeId,
                grade,
                Weight.of(1),
                Description.of("StudentAllSubjectGradesToPdfDocumentGeneratorStrategy"),
                studentSubject,
                teacher
        );

        gradeCommandRepository.save(gradeEntity);

    }

    protected StudentId createStudent(String username, String email, String pesel, SchoolId schoolId, SchoolClassId schoolClassId) {
        User user = User.of(
                Username.of(username),
                Password.of(StringUtil.randomIdentifer(5)),
                Email.of(email),
                Pesel.of(pesel),
                studentRole
        );

        PersonInformation personInformation = getPersonInformation(pesel);

        School school = schoolCommandRepository.getBySchoolId(schoolId);
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(schoolClassId);

        Student student = Student.of(
                StudentId.create(),
                user,
                school,
                schoolClass,
                personInformation,
                createAddress());

        return studentCommandRepository.save(student).studentId();
    }

    protected SubjectId createSubject(String name, SchoolClassId schoolClassId, TeacherId teacherId) {

        SchoolClass schoolClass = schoolClassCommandRepository.getReferenceById(schoolClassId);

        Teacher teacher = teacherCommandRepository.getReferenceById(teacherId);

        Subject subject = Subject.of(
                SubjectId.create(),
                Name.of(name),
                Description.of("asd"),
                schoolClass,
                teacher
        );

        return subjectCommandRepository.save(subject).subjectId();
    }

    protected AdminId createAdmin(String username, String email) {
        User user = User.of(
                Username.of(username),
                Password.of(StringUtil.randomIdentifer(5)),
                Email.of(email),
                Pesel.of(StringUtil.randomIdentifer(11)),
                adminRole
        );

        Admin admin = Admin.of(AdminId.create(), user);

        return adminCommandRepository.save(admin).adminId();
    }

    protected SchoolClassId createSchoolClass(SchoolId schoolId, TeacherId teacherId, String name) {
        School school = schoolCommandRepository.getReferenceById(schoolId);
        Teacher teacher = teacherCommandRepository.getReferenceById(teacherId);

        SchoolClass schoolClass = SchoolClass.of(
                SchoolClassId.create(),
                Name.of(name),
                school,
                teacher,
                schoolClassConfigurationProperties
        );

        return schoolClassCommandRepository.save(schoolClass).schoolClassId();
    }

    protected StudentSubjectId assignStudentToSubject(StudentId studentId, SubjectId subjectId) {

        Student student = studentCommandRepository.getReferenceById(studentId);

        Subject subject = subjectCommandRepository.getReferenceById(subjectId);

        StudentSubject studentSubject = StudentSubject.of(
                student,
                subject
        );

        return studentSubjectCommandRepository.save(studentSubject).studentSubjectId();
    }

    protected GroovyScriptId execSimpleGroovyScript(User user) {

        GroovyScript groovyScript = GroovyScript.builder()
                .groovyScriptId(GroovyScriptId.create())
                .scriptContent(ScriptContent.of("return new ScriptResult(\"Test\")"))
                .scriptStatus(successGroovyScriptStatus)
                .user(user)
                .build();

        GroovyScriptResult scriptResult = GroovyScriptResult.builder()
                .groovyScript(groovyScript)
                .groovyScriptResultId(GroovyScriptResultId.create())
                .scriptResult(ScriptResult.of("Test"))
                .execTime(GroovyScriptExecTime.of(Duration.ZERO))
                .build();

        groovyScriptResultCommandRepository.save(scriptResult);

        return groovyScript.groovyScriptId();
    }

    protected ChatId createChatMessage(RecipientId recipientId, SenderId senderId, ChatContent chatContent) {
        ChatId chatId = ChatId.create();

        ChatRoom recipientChatRoom = ChatRoom.builder()
                .recipientId(recipientId)
                .senderId(senderId)
                .chatId(chatId)
                .build();

        ChatRoom senderChatRoom = ChatRoom.builder()
                .recipientId(RecipientId.convert(senderId))
                .senderId(SenderId.convert(recipientId))
                .chatId(chatId)
                .build();

        chatRoomCommandRepository.save(senderChatRoom);
        chatRoomCommandRepository.save(recipientChatRoom);

        ChatMessage chatMessage = ChatMessage.builder()
                .chatMessageId(ChatMessageId.create())
                .senderId(senderId)
                .recipientId(recipientId)
                .chatId(chatId)
                .messageStatus(receivedMessageStatus)
                .chatContent(chatContent)
                .senderName(FullName.of("Test"))
                .recipientName(FullName.of("Test"))
                .build();

        chatMessageCommandRepository.save(chatMessage);

        return chatId;
    }

    private Address createAddress() {
        return Address.of(
                pl.edziennik.common.valueobject.vo.Address.of("Test"),
                City.of("Test"),
                PostalCode.of("12-123"));
    }

    protected TeacherScheduleId createTeacherSchedule(TeacherId teacherId, TimeFrame timeFrame) {
        Teacher teacher = teacherCommandRepository.getReferenceById(teacherId);

        TeacherSchedule teacherSchedule = TeacherSchedule.builder()
                .teacherScheduleId(TeacherScheduleId.create())
                .timeFrame(timeFrame)
                .description(Description.of("Something special"))
                .teacher(teacher)
                .build();

        return teacherScheduleCommandRepository.save(teacherSchedule).teacherScheduleId();
    }

    protected ClassRoomScheduleId createClassRoomSchedule(ClassRoomId classRoomId, TimeFrame timeFrame) {
        ClassRoom classRoom = classRoomCommandRepository.getById(classRoomId);

        ClassRoomSchedule classRoomSchedule = ClassRoomSchedule.builder()
                .classRoomScheduleId(ClassRoomScheduleId.create())
                .description(Description.of("Something special"))
                .classRoom(classRoom)
                .timeFrame(timeFrame)
                .build();

        return classRoomScheduleCommandRepository.save(classRoomSchedule).classRoomScheduleId();
    }

    protected ClassRoomId createClassRoom(SchoolId schoolId, String name) {
        School school = schoolCommandRepository.getReferenceById(schoolId);

        ClassRoom classRoom = ClassRoom.builder()
                .classRoomId(ClassRoomId.create())
                .school(school)
                .classRoomName(ClassRoomName.of(name))
                .build();

        return classRoomCommandRepository.save(classRoom).classRoomId();
    }


    protected void checkTimeFrame(LocalDateTime from, LocalDateTime to, TimeFrame timeFrame) {
        Duration duration = Duration.between(from, to);
        int durationInMinutes = (int) duration.toMinutes();

        assertEquals(durationInMinutes, timeFrame.duration().value());
        assertEquals(from, timeFrame.startDate());
        assertEquals(to, timeFrame.endDate());
    }


}
