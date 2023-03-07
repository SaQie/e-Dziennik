package pl.edziennik.eDziennik.domain.teacher.services;

import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.List;

public interface TeacherService {

    TeacherResponseApiDto register(final TeacherRequestApiDto dto);

    TeacherResponseApiDto findTeacherById(final Long id);

    void deleteTeacherById(final Long id);

    Page<List<TeacherResponseApiDto>> findAllTeachers(PageRequest pageRequest);


    TeacherResponseApiDto updateTeacher(final Long id, final TeacherRequestApiDto requestApiDto);

    TeacherResponseApiDto getTeacherByUsername(final String username);
}
