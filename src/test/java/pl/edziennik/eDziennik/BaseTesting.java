package pl.edziennik.eDziennik;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.admin.AdminIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.admin.services.AdminService;
import pl.edziennik.eDziennik.domain.grade.GradeIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.grade.service.GradeService;
import pl.edziennik.eDziennik.domain.grade.service.managment.GradeManagmentService;
import pl.edziennik.eDziennik.domain.parent.ParentIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentResponseApiDto;
import pl.edziennik.eDziennik.domain.parent.services.ParentService;
import pl.edziennik.eDziennik.domain.school.SchoolIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.school.services.SchoolService;
import pl.edziennik.eDziennik.domain.schoolclass.SchoolClassIntergrationTestUtil;
import pl.edziennik.eDziennik.domain.schoolclass.services.SchoolClassService;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.domain.student.StudentIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.services.StudentService;
import pl.edziennik.eDziennik.domain.studentsubject.StudentSubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.studentsubject.services.StudentSubjectService;
import pl.edziennik.eDziennik.domain.subject.SubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.subject.services.SubjectService;
import pl.edziennik.eDziennik.domain.teacher.TeacherIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.teacher.services.TeacherService;
import pl.edziennik.eDziennik.domain.user.UserIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.math.BigInteger;


/**
 * Basics class for test
 */
@SpringBootTest
@Transactional
public class BaseTesting {

    @Autowired
    private DataSource dataSource;

    @Autowired
    protected ResourceCreator resourceCreator;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    protected SettingsService settingsService;

    @Autowired
    protected StudentService studentService;

    @Autowired
    protected SchoolService schoolService;

    @Autowired
    protected TeacherService teacherService;

    @Autowired
    protected GradeService gradeService;

    @Autowired
    protected SubjectService subjectService;

    @Autowired
    protected SchoolClassService schoolClassService;

    @Autowired
    protected AdminService adminService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected ParentService parentService;

    @Autowired
    protected StudentSubjectService studentSubjectService;

    @Autowired
    protected GradeManagmentService gradeManagmentService;

    protected BigInteger expectedNumberOfSettings;

    @BeforeEach
    public void prepareDb() {
        clearDb();
        fillDbWithData();
        expectedNumberOfSettings = (BigInteger) em.createNativeQuery("select count(*) from app_settings").getSingleResult();
    }

    protected ParentIntegrationTestUtil parentUtil;
    protected StudentIntegrationTestUtil studentUtil;
    protected TeacherIntegrationTestUtil teacherUtil;
    protected GradeIntegrationTestUtil gradeUtil;
    protected SubjectIntegrationTestUtil subjectUtil;
    protected SchoolIntegrationTestUtil schoolUtil;
    protected SchoolClassIntergrationTestUtil schoolClassUtil;
    protected AdminIntegrationTestUtil adminUtil;
    protected UserIntegrationTestUtil userUtil;
    protected StudentSubjectIntegrationTestUtil studentSubjectUtil;

    public BaseTesting() {
        this.parentUtil = new ParentIntegrationTestUtil();
        this.studentUtil = new StudentIntegrationTestUtil();
        this.teacherUtil = new TeacherIntegrationTestUtil();
        this.gradeUtil = new GradeIntegrationTestUtil();
        this.subjectUtil = new SubjectIntegrationTestUtil();
        this.schoolUtil = new SchoolIntegrationTestUtil();
        this.schoolClassUtil = new SchoolClassIntergrationTestUtil();
        this.adminUtil = new AdminIntegrationTestUtil();
        this.userUtil = new UserIntegrationTestUtil();
        this.studentSubjectUtil = new StudentSubjectIntegrationTestUtil();
    }

    /**
     * This method returns object or null in managed state in test persistance context
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    public <T> T find(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }


    protected void fillDbWithData() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("/db/data.sql"));
        populator.execute(dataSource);
        settingsService.refreshCache();
    }

    protected void clearDb() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("/db/clearData.sql"));
        populator.execute(dataSource);
    }

    protected Long createBaseStudent() {
        StudentRequestApiDto studentRequestApiDto = studentUtil.prepareStudentRequestDto();
        StudentResponseApiDto response = studentService.register(studentRequestApiDto);
        return response.id();
    }

    protected Long createBaseParent() {
        Long idStudent = createBaseStudent();
        ParentRequestApiDto parentRequestApiDto = parentUtil.prepareParentRequestApiDto(idStudent);
        ParentResponseApiDto response = parentService.register(parentRequestApiDto);
        return response.id();
    }

    protected String getStudentFullName(Long idStudent) {
        Student student = find(Student.class, idStudent);
        return student.getPersonInformation().getFullName();
    }

    protected final Long ROLE_ADMIN = 1L;
    protected final Long ROLE_MODERATOR = 2L;
    protected final Long ROLE_TEACHER = 3L;

    protected final String ROLE_ADMIN_TEXT = "ROLE_ADMIN";
    protected final String ROLE_MODERATOR_TEXT = "ROLE_MODERATOR";
    protected final String ROLE_TEACHER_TEXT = "ROLE_TEACHER";
    protected final String ROLE_PARENT_TEXT = "ROLE_PARENT";

}
