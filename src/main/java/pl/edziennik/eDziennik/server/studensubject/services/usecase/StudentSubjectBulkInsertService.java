package pl.edziennik.eDziennik.server.studensubject.services.usecase;

import java.util.List;

public interface StudentSubjectBulkInsertService {

    void insertGroupStudentSubject(List<Long> idsSubject, Long idStudent);

}
