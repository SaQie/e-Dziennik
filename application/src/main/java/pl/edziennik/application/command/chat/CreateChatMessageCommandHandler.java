package pl.edziennik.application.command.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.ChatMessageId;
import pl.edziennik.common.valueobject.id.MessageStatusId;
import pl.edziennik.domain.chat.ChatMessage;
import pl.edziennik.domain.chat.MessageStatus;
import pl.edziennik.infrastructure.repository.chat.chatmessage.ChatMessageCommandRepository;
import pl.edziennik.infrastructure.repository.chat.messagestatus.MessageStatusQueryRepository;

@Component
@AllArgsConstructor
class CreateChatMessageCommandHandler implements ICommandHandler<CreateChatMessageCommand, OperationResult> {

    private final ChatMessageCommandRepository commandRepository;
    private final MessageStatusQueryRepository messageStatusQueryRepository;

    @Override
    public OperationResult handle(CreateChatMessageCommand command) {
        ChatMessage chatMessage = command.chatMessage();
        MessageStatus messageStatus = messageStatusQueryRepository.getByMessageStatusId(MessageStatusId.PredefinedRow.DELIVERED);

        ChatMessage entity = ChatMessage.builder()
                .recipientId(chatMessage.getRecipientId())
                .senderId(chatMessage.getSenderId())
                .chatContent(chatMessage.getChatContent())
                .senderName(chatMessage.getSenderName())
                .recipientName(chatMessage.getRecipientName())
                .chatId(chatMessage.getChatId())
                .messageStatus(messageStatus)
                .build();

        ChatMessageId chatMessageId = commandRepository.save(entity).getChatMessageId();

        return OperationResult.success(chatMessageId);
    }
}
