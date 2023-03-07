package pl.edziennik.eDziennik.domain.student.services;

import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.List;

public interface StudentService {


    StudentResponseApiDto register(final StudentRequestApiDto dto);

    StudentResponseApiDto findStudentById(final Long id);

    void deleteStudentById(final Long id);

    Page<List<StudentResponseApiDto>> findAllStudents(PageRequest pageRequest);


    StudentResponseApiDto updateStudent(final Long id, final StudentRequestApiDto requestApiDto);

    StudentResponseApiDto getStudentByUsername(final String username);
}
