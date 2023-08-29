package pl.edziennik.application.command.classroom.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IBaseValidator;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateClassRoomCommandValidator implements IBaseValidator<CreateClassRoomCommand> {

    public static final String CLASS_ROOM_NAME_ALREADY_EXISTS_MESSAGE_KEY = "class.room.already.exists";

    private final SchoolCommandRepository schoolCommandRepository;
    private final ClassRoomCommandRepository classRoomCommandRepository;

    @Override
    public void validate(CreateClassRoomCommand command, ValidationErrorBuilder errorBuilder) {
        checkSchoolExists(command, errorBuilder);
        checkClassRoomAlreadyExists(command, errorBuilder);
    }

    /**
     * Check if classroom already exists in school
     */
    private void checkClassRoomAlreadyExists(CreateClassRoomCommand command, ValidationErrorBuilder errorBuilder) {
        if (classRoomCommandRepository.isClassRoomAlreadyExists(command.classRoomName(), command.schoolId())) {
            errorBuilder.addError(
                    CreateClassRoomCommand.CLASS_ROOM_NAME,
                    CLASS_ROOM_NAME_ALREADY_EXISTS_MESSAGE_KEY,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.classRoomName()
            );
        }
    }

    /**
     * Check if school with given id exists
     */
    private void checkSchoolExists(CreateClassRoomCommand command, ValidationErrorBuilder errorBuilder) {
        schoolCommandRepository.findById(command.schoolId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateClassRoomCommand.SCHOOL_ID);
                    return null;
                });
        errorBuilder.flush();
    }
}
