package pl.edziennik.eDziennik.domain.student.services;

import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.basics.page.PageDto;

public interface StudentService {


    StudentResponseApiDto register(final StudentRequestApiDto dto);

    StudentResponseApiDto findStudentById(final StudentId studentId);

    void deleteStudentById(final StudentId studentId);

    PageDto<StudentResponseApiDto> findAllStudents(final Pageable pageable);


    StudentResponseApiDto updateStudent(final StudentId studentId, final StudentRequestApiDto requestApiDto);

    StudentResponseApiDto getStudentByUsername(final String username);
}
