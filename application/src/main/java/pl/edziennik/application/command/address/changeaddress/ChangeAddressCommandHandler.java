package pl.edziennik.application.command.address.changeaddress;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.*;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.director.DirectorCommandRepository;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

@Component
@AllArgsConstructor
class ChangeAddressCommandHandler implements ICommandHandler<ChangeAddressCommand, OperationResult> {

    private final SchoolCommandRepository schoolCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final ParentCommandRepository parentCommandRepository;
    private final DirectorCommandRepository directorCommandRepository;
    private final ResourceCreator res;

    @Override
    public OperationResult handle(ChangeAddressCommand command) {
        switch (command.commandFor()) {
            case PARENT -> updateParentAddress(command);
            case SCHOOL -> updateSchoolAddress(command);
            case TEACHER -> updateTeacherAddress(command);
            case STUDENT -> updateStudentAddress(command);
            case DIRECTOR -> updateDirectorAddress(command);
        }
        return OperationResult.success();
    }

    private void updateDirectorAddress(ChangeAddressCommand command) {
        DirectorId directorId = DirectorId.of(command.id());

        Director director = directorCommandRepository.findById(directorId)
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeAddressCommand.ID, directorId)
                ));

        fillAddressData(command, director.address());

        directorCommandRepository.save(director);
    }


    private void updateStudentAddress(ChangeAddressCommand command) {
        StudentId studentId = StudentId.of(command.id());

        Student student = studentCommandRepository.findById(studentId)
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeAddressCommand.ID, studentId)
                ));

        fillAddressData(command, student.address());

        studentCommandRepository.save(student);

    }


    private void updateTeacherAddress(ChangeAddressCommand command) {
        TeacherId teacherId = TeacherId.of(command.id());

        Teacher teacher = teacherCommandRepository.findById(teacherId)
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeAddressCommand.ID, teacherId)
                ));

        fillAddressData(command, teacher.address());

        teacherCommandRepository.save(teacher);
    }

    private void updateSchoolAddress(ChangeAddressCommand command) {
        SchoolId schoolId = SchoolId.of(command.id());

        School student = schoolCommandRepository.findById(schoolId)
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeAddressCommand.ID, schoolId)
                ));

        fillAddressData(command, student.address());

        schoolCommandRepository.save(student);
    }

    private void updateParentAddress(ChangeAddressCommand command) {
        ParentId parentId = ParentId.of(command.id());

        Parent parent = parentCommandRepository.findById(parentId)
                .orElseThrow(() -> new BusinessException(
                        res.notFoundError(ChangeAddressCommand.ID, parentId)
                ));

        fillAddressData(command, parent.address());

        parentCommandRepository.save(parent);
    }

    private void fillAddressData(ChangeAddressCommand command, Address address) {
        address.changeCity(command.city());
        address.changePostalCode(command.postalCode());
        address.changeAddress(command.address());
    }
}
