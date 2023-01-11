package pl.edziennik.eDziennik.server.studensubject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.ServiceValidator;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.studensubject.services.validator.StudentSubjectValidators;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentSubjectValidatorService extends ServiceValidator<StudentSubjectValidators, StudentSubjectRequestDto> {

    @Override
    protected void valid(StudentSubjectRequestDto dto) {
        runValidatorChain(dto);
        basicValidator.checkStudentExist(dto.getIdStudent());
        basicValidator.checkSubjectExist(dto.getIdSubject());
    }

}
