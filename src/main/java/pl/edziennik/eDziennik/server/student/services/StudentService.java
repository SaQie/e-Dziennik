package pl.edziennik.eDziennik.server.student.services;

import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;

import java.util.List;

public interface StudentService {


    StudentResponseApiDto register(final StudentRequestApiDto dto);

    StudentResponseApiDto findStudentById(final Long id);

    void deleteStudentById(final Long id);

    List<StudentResponseApiDto> findAllStudents();


    StudentResponseApiDto updateStudent(final Long id, final StudentRequestApiDto requestApiDto);

    StudentResponseApiDto getStudentByUsername(final String username);
}
