package pl.edziennik.eDziennik.server.grade.services.managment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.grade.dao.GradeDao;
import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.services.StudentSubjectValidatorService;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

@Service
@AllArgsConstructor
public class GradeManagmentValidationService{

    public static final String EXCEPTION_MESSAGE_GRADE_NOT_BELONG_TO_STUDENT_SUBJECT = "grade.not.belong.to.student.subject";

    private final StudentSubjectValidatorService studentSubjectValidatorService;
    private final ResourceCreator resourceCreator;
    private final GradeDao dao;

    protected StudentSubject checkStudentSubjectExist(Long idStudent, Long idSubject){
        return studentSubjectValidatorService.checkStudentSubjectExist(idStudent, idSubject);
    }

    protected void checkGradeExistInStudentSubject(Long idGrade, Long idStudentSubject){
        Grade grade = dao.get(idGrade);
        if (!grade.getStudentSubject().getId().equals(idStudentSubject)){
            String message = resourceCreator.of(EXCEPTION_MESSAGE_GRADE_NOT_BELONG_TO_STUDENT_SUBJECT);

            ApiErrorsDto error = ApiErrorsDto.builder()
                    .errorThrownedBy(this.getClass().getSimpleName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .thrownImmediately(false)
                    .cause(message)
                    .build();

            throw new BusinessException(error);
        }

    }

}
