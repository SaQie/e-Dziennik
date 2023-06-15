package pl.edziennik.infrastructure.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.token.ActivationTokenRepository;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class RemoveInactiveUserAccountsSchedulerTask {

    private final ActivationTokenRepository tokenRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final ParentCommandRepository parentCommandRepository;

    @Scheduled(cron = "0 0 0 1/2 * ?")
    @Transactional
    public void removeInactiveUserAccounts() {
        List<UserId> userIds = tokenRepository.getUserIdsWithExpiredActivationTokens();
        if (userIds.isEmpty()) {
            log.debug("There is no inactive accounts to delete using cron " + this.getClass().getSimpleName());
            return;
        }

        List<Student> students = studentCommandRepository.getStudentsByUserIds(userIds);
        if (!students.isEmpty()) {
            studentCommandRepository.deleteAll(students);
        }

        List<Teacher> teachers = teacherCommandRepository.getTeachersByUserIds(userIds);
        if (!teachers.isEmpty()) {
            teacherCommandRepository.deleteAll(teachers);
        }

        List<Parent> parents = parentCommandRepository.getParentsByUserIds(userIds);
        if (!parents.isEmpty()) {
            parentCommandRepository.deleteAll(parents);
        }
        tokenRepository.deleteActivationToken(userIds);

        log.debug("Deleted " + userIds.size() + " inactive accounts using cron " + this.getClass().getSimpleName());
    }

}
