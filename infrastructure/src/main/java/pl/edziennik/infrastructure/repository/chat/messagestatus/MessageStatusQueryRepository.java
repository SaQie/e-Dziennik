package pl.edziennik.infrastructure.repository.chat.messagestatus;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.MessageStatusId;
import pl.edziennik.domain.chat.MessageStatus;

@RepositoryDefinition(domainClass = MessageStatus.class, idClass = MessageStatusId.class)
public interface MessageStatusQueryRepository {

    MessageStatus getByMessageStatusId(MessageStatusId messageStatusId);

}
