package pl.edziennik.eDziennik.domain.student.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;

public interface StudentService {


    StudentResponseApiDto register(final StudentRequestApiDto dto);

    StudentResponseApiDto findStudentById(final Long id);

    void deleteStudentById(final Long id);

    Page<StudentResponseApiDto> findAllStudents(Pageable pageable);


    StudentResponseApiDto updateStudent(final Long id, final StudentRequestApiDto requestApiDto);

    StudentResponseApiDto getStudentByUsername(final String username);
}
