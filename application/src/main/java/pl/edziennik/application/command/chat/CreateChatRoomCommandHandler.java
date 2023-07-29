package pl.edziennik.application.command.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.ChatId;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.domain.chat.ChatRoom;
import pl.edziennik.infrastructure.repository.chat.chatroom.ChatRoomCommandRepository;

@Component
@AllArgsConstructor
class CreateChatRoomCommandHandler implements ICommandHandler<CreateChatRoomCommand, OperationResult> {

    private final ChatRoomCommandRepository commandRepository;

    @Override
    public OperationResult handle(CreateChatRoomCommand command) {
        ChatId chatId = ChatId.create();

        // Todo nie wiem czy to ma sens, do przemyslenia pozniej czy trzeba zpisywac recipienta i sendera na odwrot tez
        ChatRoom recipientChatRoom = ChatRoom.builder()
                .recipientId(command.recipientId())
                .senderId(command.senderId())
                .chatId(chatId)
                .build();

        ChatRoom senderChatRoom = ChatRoom.builder()
                .recipientId(RecipientId.convert(command.senderId()))
                .senderId(SenderId.convert(command.recipientId()))
                .chatId(chatId)
                .build();


        commandRepository.save(recipientChatRoom);
        commandRepository.save(senderChatRoom);


        return OperationResult.success(chatId);
    }
}
