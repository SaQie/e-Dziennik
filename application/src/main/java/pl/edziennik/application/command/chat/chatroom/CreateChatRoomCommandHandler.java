package pl.edziennik.application.command.chat.chatroom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.common.valueobject.id.RecipientId;
import pl.edziennik.common.valueobject.id.SenderId;
import pl.edziennik.domain.chat.ChatRoom;
import pl.edziennik.infrastructure.repository.chat.chatroom.ChatRoomCommandRepository;

@Component
@AllArgsConstructor
class CreateChatRoomCommandHandler implements CommandHandler<CreateChatRoomCommand> {

    private final ChatRoomCommandRepository commandRepository;

    @Override
    public void handle(CreateChatRoomCommand command) {
        ChatRoom recipientChatRoom = ChatRoom.builder()
                .recipientId(command.recipientId())
                .senderId(command.senderId())
                .chatId(command.chatId())
                .build();

        ChatRoom senderChatRoom = ChatRoom.builder()
                .recipientId(RecipientId.convert(command.senderId()))
                .senderId(SenderId.convert(command.recipientId()))
                .chatId(command.chatId())
                .build();


        commandRepository.save(recipientChatRoom);
        commandRepository.save(senderChatRoom);
    }
}
