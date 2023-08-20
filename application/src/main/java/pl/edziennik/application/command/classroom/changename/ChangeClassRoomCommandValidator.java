package pl.edziennik.application.command.classroom.changename;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class ChangeClassRoomCommandValidator implements IBaseValidator<ChangeClassRoomNameCommand> {

    public static final String CLASS_ROOM_NAME_EQUAL_TO_OLD_MESSAGE_KEY = "class.room.name.equal.to.old";
    public static final String CLASS_ROOM_NAME_ALREADY_EXISTS_MESSAGE_KEY = "class.room.already.exists";

    private final ClassRoomCommandRepository classRoomCommandRepository;

    @Override
    public void validate(ChangeClassRoomNameCommand command, ValidationErrorBuilder errorBuilder) {
        checkClassRoomExists(command, errorBuilder);
        checkClassRoomName(command, errorBuilder);
        checkClassRoomWithGivenNameAlreadyExists(command, errorBuilder);
    }

    /**
     * Check the classroom with given id exists
     */
    private void checkClassRoomExists(ChangeClassRoomNameCommand command, ValidationErrorBuilder errorBuilder) {
        classRoomCommandRepository.findById(command.classRoomId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(ChangeClassRoomNameCommand.CLASS_ROOM_ID);
                    return null;
                });
        errorBuilder.flush();
    }

    /**
     * Check the new classroom name isn't equal to old classroom's name
     */
    private void checkClassRoomName(ChangeClassRoomNameCommand command, ValidationErrorBuilder errorBuilder) {
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        ClassRoomName oldClassRoomName = classRoom.classRoomName();

        if (oldClassRoomName.equals(command.classRoomName())) {
            errorBuilder.addError(
                    ChangeClassRoomNameCommand.CLASS_ROOM_NAME,
                    CLASS_ROOM_NAME_EQUAL_TO_OLD_MESSAGE_KEY,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    oldClassRoomName
            );
        }
    }

    /**
     * Check if classroom with given name already exists in school
     */
    private void checkClassRoomWithGivenNameAlreadyExists(ChangeClassRoomNameCommand command, ValidationErrorBuilder errorBuilder) {
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        SchoolId schoolId = classRoom.school().schoolId();

        if (classRoomCommandRepository.isClassRoomAlreadyExists(command.classRoomName(), schoolId)) {
            errorBuilder.addError(
                    ChangeClassRoomNameCommand.CLASS_ROOM_NAME,
                    CLASS_ROOM_NAME_ALREADY_EXISTS_MESSAGE_KEY,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.classRoomName()
            );
        }
    }
}
