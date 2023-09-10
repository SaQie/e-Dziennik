package pl.edziennik.application.command.chat.chatmessage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.common.valueobject.id.MessageStatusId;
import pl.edziennik.domain.chat.ChatMessage;
import pl.edziennik.domain.chat.MessageStatus;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageCommandRepository;
import pl.edziennik.infrastructure.repository.chat.messagestatus.MessageStatusQueryRepository;

@Component
@AllArgsConstructor
class CreateChatMessageCommandHandler implements CommandHandler<CreateChatMessageCommand> {

    private final ChatMessageCommandRepository commandRepository;
    private final MessageStatusQueryRepository messageStatusQueryRepository;

    @Override
    public void handle(CreateChatMessageCommand command) {
        MessageStatus messageStatus = messageStatusQueryRepository.getByMessageStatusId(MessageStatusId.PredefinedRow.DELIVERED);

        ChatMessage entity = ChatMessage.builder()
                .recipientId(command.recipientId())
                .senderId(command.senderId())
                .chatContent(command.chatContent())
                .senderName(command.senderName())
                .recipientName(command.recipientName())
                .chatId(command.chatId())
                .messageStatus(messageStatus)
                .build();

        commandRepository.save(entity);
    }
}
