package pl.edziennik.eDziennik.server.teacher.services;

import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;

import java.util.List;

public interface TeacherService {

    TeacherResponseApiDto register(final TeacherRequestApiDto dto);

    TeacherResponseApiDto findTeacherById(final Long id);

    void deleteTeacherById(final Long id);

    List<TeacherResponseApiDto> findAllTeachers();

    boolean updateTeacherLastLoginDate(final String username);
}