package pl.edziennik.eDziennik.server.rating;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.rating.domain.Rating;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingRequestApiDto;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;
import pl.edziennik.eDziennik.server.rating.domain.dto.mapper.RatingMapper;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class RatingServiceImpl implements RatingService{

    private final RatingRepository repository;

    @Override
    public RatingResponseApiDto addNewRating(RatingRequestApiDto dto) {
        Rating rating = RatingMapper.toEntity(dto);
        Rating savedRating = repository.save(rating);
        return RatingMapper.toDto(savedRating);
    }

    @Override
    public RatingResponseApiDto findRatingById(Long id) {
        Rating rating = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rating with given id " + id + "not exist"));
        return RatingMapper.toDto(rating);
    }

    @Override
    public void deleteRatingById(Long id) {
        Rating rating = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rating with given id " + id + "not exist"));
        repository.delete(rating);
    }

    @Override
    public List<RatingResponseApiDto> findAllRatings() {
        return repository.findAll()
                .stream()
                .map(RatingMapper::toDto)
                .collect(Collectors.toList());
    }
}
