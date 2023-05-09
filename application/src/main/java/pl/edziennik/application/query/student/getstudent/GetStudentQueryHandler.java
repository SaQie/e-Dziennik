package pl.edziennik.application.query.student.getstudent;

import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.infrastructure.repositories.address.AddressCommandRepository;
import pl.edziennik.infrastructure.repositories.address.AddressQueryRepository;

class GetStudentQueryHandler implements IQueryHandler<GetStudentQuery,Void> {

    private AddressCommandRepository addressCommandRepository;
    private  AddressQueryRepository addressQueryRepository;

    @Override
    public Void handle(GetStudentQuery command) {

        return null;
    }
}
