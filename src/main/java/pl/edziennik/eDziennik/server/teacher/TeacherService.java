package pl.edziennik.eDziennik.server.teacher;

import pl.edziennik.eDziennik.dto.teacher.TeacherRequestDto;
import pl.edziennik.eDziennik.dto.teacher.TeacherResponseApiDto;

import java.util.List;

public interface TeacherService {

    TeacherResponseApiDto register(final TeacherRequestDto dto);

    TeacherResponseApiDto findTeacherById(final Long id);

    void deleteTeacherById(final Long id);

    List<TeacherResponseApiDto> findAllTeachers();
}
