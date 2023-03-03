package pl.edziennik.eDziennik.domain.grade.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.grade.dao.GradeDao;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.GradeResponseApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.mapper.GradeMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class GradeServiceImpl implements GradeService {

    private final GradeDao dao;

    @Override
    @Transactional
    public GradeResponseApiDto addNewGrade(GradeRequestApiDto dto) {
        Grade grade = GradeMapper.toEntity(dto);
        Grade savedGrade = dao.saveOrUpdate(grade);
        return GradeMapper.toDto(savedGrade);
    }

    @Override
    public GradeResponseApiDto findGradeById(Long id) {
        Grade grade = dao.get(id);
        return GradeMapper.toDto(grade);
    }

    @Override
    public void deleteGradeById(Long id) {
        Grade grade = dao.get(id);
        dao.remove(grade);
    }

    @Override
    @Transactional
    public GradeResponseApiDto updateGrade(Long id, GradeRequestApiDto dto) {
        // TODO -> Walidacja
        Optional<Grade> optionalGrade = dao.find(id);

        if (optionalGrade.isPresent()) {
            Grade grade = optionalGrade.get();
            grade.setGrade(Grade.GradeConst.getByRating(dto.getGrade()));
            grade.setWeight(dto.getWeight());
            grade.setDescription(dto.getDescription());
            return GradeMapper.toDto(grade);
        }

        Grade grade = dao.saveOrUpdate(GradeMapper.toEntity(dto));
        return GradeMapper.toDto(grade);

    }

    @Override
    public List<GradeResponseApiDto> findAllGrades() {
        return dao.findAll()
                .stream()
                .map(GradeMapper::toDto)
                .collect(Collectors.toList());
    }
}