package pl.edziennik.eDziennik.domain.admin.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.admin.AdminIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.admin.dao.AdminDao;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.studentsubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.services.validator.StudentSubjectValidators;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdminValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private AdminAlreadyExistValidator validator;

    @Mock
    private AdminDao dao;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenAdminAlreadyExists() {
        // given
        AdminRequestApiDto dto = adminUtil.prepareAdminRequest();

        when(dao.findAll()).thenReturn(List.of(new Admin()));
        lenient().when(resourceCreator.of(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST))
                .thenReturn(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = validator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), AdminValidators.ADMIN_ALREADY_EXISTS_VALIDATOR_NAME);
        assertEquals(error.getCause(), getErrorMessage(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST));
    }

    @Test
    public void shouldNotReturnApiErrorWhenAminNotExists() {
        // given
        AdminRequestApiDto dto = adminUtil.prepareAdminRequest();

        when(dao.findAll()).thenReturn(Collections.emptyList());
        lenient().when(resourceCreator.of(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST))
                .thenReturn(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = validator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }
}
