package pl.edziennik.application.command.grademanagment.unassign;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.domain.grade.Grade;
import pl.edziennik.infrastructure.repository.grade.GradeCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class UnassignGradeFromStudentSubjectCommandHandler implements CommandHandler<UnassignGradeFromStudentSubjectCommand> {

    private final GradeCommandRepository gradeCommandRepository;
    private final ResourceCreator res;

    @Override
    public void handle(UnassignGradeFromStudentSubjectCommand command) {
        Grade grade = gradeCommandRepository.findById(command.gradeId())
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(UnassignGradeFromStudentSubjectCommand.GRADE_ID, command.gradeId())
                ));
        gradeCommandRepository.deleteById(grade.gradeId());

        // TODO pamietaj o evencie - musi sie przeliczyc jeszcze raz srednia !
    }
}
