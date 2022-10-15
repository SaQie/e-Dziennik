package pl.edziennik.eDziennik.server.ratingsubjectstudent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.rating.RatingRepository;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.student.StudentRepository;
import pl.edziennik.eDziennik.server.subject.SubjectRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
class RatingSubjectStudentPrivService {

    private final StudentRepository studentRepository;
    private final RatingRepository ratingRepository;
    private final SubjectRepository subjectRepository;


    protected void checkStudentExist(Long studentId, RatingSubjectStudentLink entity) {
        studentRepository.findById(studentId).ifPresentOrElse(entity::setStudent, () -> {
            throw new EntityNotFoundException("Student with given id " + studentId + " not exist");
        });
    }


    protected void checkSubjectExist(Long subjectId, RatingSubjectStudentLink entity) {
//        subjectRepository.findById(subjectId).ifPresentOrElse(entity::, () -> {
//            throw new EntityNotFoundException("Subject with given id " + subjectId + " not exist");
//        });
    }


    protected void checkRatingExist(Long ratingId, RatingSubjectStudentLink entity) {
        ratingRepository.findById(ratingId).ifPresentOrElse(rating -> entity.getRatings().add(rating), () -> {
            throw new EntityNotFoundException("Rating with given id " + ratingId + " not exist");
        });
    }
}
