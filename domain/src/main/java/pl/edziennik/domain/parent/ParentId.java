package pl.edziennik.domain.parent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.Identifier;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
public class ParentId implements Serializable, Identifier {

    @JsonProperty(value = "parentId", access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonCreator
    public ParentId(Long id) {
        this.id = id;
    }

    public ParentId() {
        this.id = null;
    }

    public static ParentId wrap(Long value) {
        return new ParentId(value);
    }


}