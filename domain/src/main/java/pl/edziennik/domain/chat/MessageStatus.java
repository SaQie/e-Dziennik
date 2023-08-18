package pl.edziennik.domain.chat;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.MessageStatusId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class MessageStatus {

    @EmbeddedId
    private MessageStatusId messageStatusId = MessageStatusId.create();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @Version
    private Long version;

}
