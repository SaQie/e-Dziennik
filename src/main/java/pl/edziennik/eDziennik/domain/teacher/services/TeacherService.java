package pl.edziennik.eDziennik.domain.teacher.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;

public interface TeacherService {

    TeacherResponseApiDto register(final TeacherRequestApiDto dto);

    TeacherResponseApiDto findTeacherById(final Long id);

    void deleteTeacherById(final Long id);

    Page<TeacherResponseApiDto> findAllTeachers(Pageable pageable);


    TeacherResponseApiDto updateTeacher(final Long id, final TeacherRequestApiDto requestApiDto);

    TeacherResponseApiDto getTeacherByUsername(final String username);
}
