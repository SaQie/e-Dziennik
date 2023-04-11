package pl.edziennik.eDziennik.domain.teacher.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

public interface TeacherService {

    TeacherResponseApiDto register(final TeacherRequestApiDto dto);

    TeacherResponseApiDto findTeacherById(final TeacherId teacherId);

    void deleteTeacherById(final TeacherId teacherId);

    PageDto<TeacherResponseApiDto> findAllTeachers(final Pageable pageable);


    TeacherResponseApiDto updateTeacher(final TeacherId teacherId, final TeacherRequestApiDto requestApiDto);

    TeacherResponseApiDto getTeacherByUsername(final String username);
}
