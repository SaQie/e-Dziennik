package pl.edziennik.eDziennik.domain.teacher.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
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
public class TeacherId implements Serializable, ValueObject {


    private Long id;


    @JsonCreator
    public TeacherId(Long id) {
        this.id = id;
    }

    public TeacherId() {
        this.id = null;
    }

    public static TeacherId wrap(Long value) {
        return new TeacherId(value);
    }


}