package pl.edziennik.eDziennik.domain.teacher.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.server.basic.vo.Identifier;

import java.io.Serializable;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class TeacherId implements Serializable, Identifier {

    @JsonProperty(value = "teacherId", access = JsonProperty.Access.READ_ONLY)
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