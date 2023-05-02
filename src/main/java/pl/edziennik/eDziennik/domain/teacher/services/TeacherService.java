package pl.edziennik.eDziennik.domain.teacher.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.basic.page.PageDto;

import java.util.List;

public interface TeacherService {

    TeacherResponseApiDto register(final TeacherRequestApiDto dto);

    TeacherResponseApiDto findTeacherById(final TeacherId teacherId);

    void deleteTeacherById(final TeacherId teacherId);

    PageDto<TeacherResponseApiDto> findAllTeachers(final Pageable pageable);


    TeacherResponseApiDto updateTeacher(final TeacherId teacherId, final TeacherRequestApiDto requestApiDto);

    TeacherResponseApiDto getTeacherByUsername(final String username);

    List<SubjectResponseApiDto> getTeacherSubjects(TeacherId teacherId);
}
