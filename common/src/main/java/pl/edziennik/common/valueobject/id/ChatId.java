package pl.edziennik.common.valueobject.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ChatId implements Serializable {

    @JsonValue
    private final String id;

    @JsonCreator
    protected ChatId(String value) {
        this.id = value;
    }

    protected ChatId(RecipientId recipientId, SenderId senderId) {
        this.id = String.format("%s_%s", recipientId.id(), senderId.id());
    }


    public static ChatId of(RecipientId recipientId, SenderId senderId) {
        return new ChatId(recipientId, senderId);
    }

}
