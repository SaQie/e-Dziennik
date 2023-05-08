package pl.edziennik.application.common.mapper;


import pl.edziennik.application.command.student.create.CreateStudentCommand;
import pl.edziennik.domain.student.Student;

public class StudentMapper {


    private StudentMapper() {
    }

    public static Student toEntity(CreateStudentCommand command){
        return new Student(
                PersonInformationMapper.mapToPersonInformation(command),
                AddressMapper.mapToAddress(command)
        );
    }
}
