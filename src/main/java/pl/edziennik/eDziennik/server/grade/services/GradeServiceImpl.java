package pl.edziennik.eDziennik.server.grade.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.grade.dao.GradeDao;
import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeResponseApiDto;
import pl.edziennik.eDziennik.server.grade.domain.dto.mapper.GradeMapper;

import java.util.List;
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
    public void deleteRatingById(Long id) {
        Grade grade = dao.get(id);
        dao.remove(grade);
    }

    @Override
    public List<GradeResponseApiDto> findAllGrades() {
        return dao.findAll()
                .stream()
                .map(GradeMapper::toDto)
                .collect(Collectors.toList());
    }
}
