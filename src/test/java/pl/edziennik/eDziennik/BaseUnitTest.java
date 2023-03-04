package pl.edziennik.eDziennik;

import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import pl.edziennik.eDziennik.domain.admin.AdminIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.grade.GradeIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.parent.ParentIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.school.SchoolIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.schoolclass.SchoolClassIntergrationTestUtil;
import pl.edziennik.eDziennik.domain.student.StudentIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.studentsubject.StudentSubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.subject.SubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.teacher.TeacherIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.user.UserIntegrationTestUtil;
import pl.edziennik.eDziennik.server.config.messages.MessageConfig;

import java.util.Locale;

@Import(MessageConfig.class)
public class BaseUnitTest {


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

    public BaseUnitTest() {
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

    protected String getErrorMessage(String messageKey) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource.getMessage(messageKey, null, Locale.ROOT);
    }

}
