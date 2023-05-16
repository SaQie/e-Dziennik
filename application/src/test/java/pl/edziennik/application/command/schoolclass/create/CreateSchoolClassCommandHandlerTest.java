package pl.edziennik.application.command.schoolclass.create;

import pl.edziennik.application.BaseUnitTest;

class CreateSchoolClassCommandHandlerTest extends BaseUnitTest {

    private final CreateSchoolClassCommandHandler handler;

    public CreateSchoolClassCommandHandlerTest() {
        this.handler = new CreateSchoolClassCommandHandler(schoolCommandRepository, teacherCommandRepository, schoolClassCommandRepository);
    }


}
