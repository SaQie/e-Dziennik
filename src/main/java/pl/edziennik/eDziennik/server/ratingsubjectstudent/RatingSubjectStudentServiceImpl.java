package pl.edziennik.eDziennik.server.ratingsubjectstudent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentRequestApiDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentResponseApiDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.mapper.RatingSubjectStudentMapper;

@Service
@AllArgsConstructor
class RatingSubjectStudentServiceImpl implements RatingSubjectStudentService {

    private final RatingSubjectStudentRepository repository;
    private final RatingSubjectStudentPrivService privService;

    @Override
    public RatingSubjectStudentResponseApiDto assignRatingToStudentSubject(RatingSubjectStudentRequestApiDto dto) {
        RatingSubjectStudentLink entity = new RatingSubjectStudentLink();
        privService.checkStudentExist(dto.getStudent(), entity);
        privService.checkSubjectExist(dto.getSubject(), entity);
        privService.checkRatingExist(dto.getRating(), entity);
        RatingSubjectStudentLink savedEntity = repository.save(entity);
        return RatingSubjectStudentMapper.toDto(savedEntity);
    }

    @Override
    public RatingSubjectStudentResponseApiDto findStudenSubjectRating(Long id) {
        return null;
    }

    @Override
    public void deleteStudentSubjectRating(Long id) {

    }
}
