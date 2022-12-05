package pl.edziennik.eDziennik.server.studensubject.services.usecase;

import java.util.List;

public interface StudentSubjectInsertGroupService {

    void insertGroupStudentSubject(List<Long> idsSubject, Long idStudent);

}
