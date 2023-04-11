package pl.edziennik.eDziennik.domain.grade.service.managment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.repository.GradeRepository;
import pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper.StudentSubjectId;
import pl.edziennik.eDziennik.server.basics.service.BaseService;

@Service
@AllArgsConstructor
class GradeManagmentValidationService extends BaseService {

    public static final String EXCEPTION_MESSAGE_GRADE_NOT_BELONG_TO_STUDENT_SUBJECT = "grade.not.belong.to.student.subject";

    private final GradeRepository repository;

    protected void checkGradeExistInStudentSubject(GradeId gradeId, StudentSubjectId idStudentSubject) {
        Grade grade = repository.findById(gradeId.id()).orElseThrow(notFoundException(gradeId.id(), Grade.class));
        // I have to check, the grade exist in student subject
        if (!grade.getStudentSubject().getStudentSubjectId().equals(idStudentSubject)) {
//            ValidatorUtil.makeAndThrowBusinessException(this.getClass().getSimpleName(),
//                    getMessage(EXCEPTION_MESSAGE_GRADE_NOT_BELONG_TO_STUDENT_SUBJECT));
        }

    }
}
