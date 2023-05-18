package pl.edziennik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.edziennik.infrastructure.TestClasssss;
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

//@SpringBootTest(classes = TestClasssss.class)
//@DataJpaTest
@SpringBootTest(classes = {TestClasssss.class})
@ContextConfiguration(classes = TestConfig.class)
public class BaseIntegrationTest extends ContainerEnvironment {

    @Autowired
    private ResourceCreator resourceCreator;

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


}
