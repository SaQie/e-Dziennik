package pl.edziennik.eDziennik.domain.grade.service.managment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.domain.grade.dao.GradeDao;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.server.utils.ValidatorUtil;

@Service
@AllArgsConstructor
class GradeManagmentValidationService extends BaseService {

    public static final String EXCEPTION_MESSAGE_GRADE_NOT_BELONG_TO_STUDENT_SUBJECT = "grade.not.belong.to.student.subject";

    private final GradeDao dao;

    protected void checkGradeExistInStudentSubject(Long idGrade, Long idStudentSubject) {
        Grade grade = dao.get(idGrade);
        // I have to check, the grade exist in student subject
        if (!grade.getStudentSubject().getId().equals(idStudentSubject)) {
            ValidatorUtil.makeAndThrowBusinessException(this.getClass().getSimpleName(),
                    getMessage(EXCEPTION_MESSAGE_GRADE_NOT_BELONG_TO_STUDENT_SUBJECT));
        }

    }
}
