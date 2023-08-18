package pl.edziennik.infrastructure.repository.subject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.view.subject.DetailedSubjectView;
import pl.edziennik.domain.subject.Subject;

@RepositoryDefinition(domainClass = Subject.class, idClass = SubjectId.class)
public interface SubjectQueryRepository {
    @Query("SELECT NEW " +
            "pl.edziennik.common.view.subject.DetailedSubjectView(s.subjectId, s.name, s.description, t.teacherId, t.personInformation.fullName, sc.schoolClassId, sc.className) " +
            "FROM Subject s " +
            "JOIN s.teacher t " +
            "JOIN s.schoolClass sc " +
            "WHERE s.subjectId = :subjectId")
    DetailedSubjectView getSubjectView(SubjectId subjectId);
}
