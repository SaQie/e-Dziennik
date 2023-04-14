package pl.edziennik.eDziennik.domain.studentsubject.domain.wrapper;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.server.basics.vo.ValueObject;

import java.io.Serializable;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class StudentSubjectId implements Serializable, ValueObject {


    private final Long id;


    public StudentSubjectId(Long id) {
        this.id = id;
    }

    public StudentSubjectId() {
        this.id = null;
    }

    public static StudentSubjectId wrap(Long value) {
        return new StudentSubjectId(value);
    }


}