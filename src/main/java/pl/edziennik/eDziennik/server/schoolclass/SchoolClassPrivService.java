package pl.edziennik.eDziennik.server.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.dto.schoolclass.SchoolClassRequestDto;
import pl.edziennik.eDziennik.server.repositories.TeacherRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class SchoolClassPrivService {

    private final TeacherRepository teacherRepository;

    protected void checkSupervisingTeacherExist(SchoolClassRequestDto dto, SchoolClass schoolClass) {
        if (dto.getSupervisingTeacherId() != null){
            teacherRepository.findById(dto.getSupervisingTeacherId()).ifPresentOrElse(schoolClass::setTeacher, () -> {
                throw new EntityNotFoundException("Teacher with given id " + dto.getSupervisingTeacherId() + " not exist");
            });
        }
    }

}
