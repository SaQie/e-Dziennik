package pl.edziennik.eDziennik.domain.admin.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.repository.AdminRepository;
import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private AdminAlreadyExistValidator validator;

    @Mock
    private AdminRepository repository;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenAdminAlreadyExists() {
        // given
        AdminRequestApiDto dto = adminUtil.prepareAdminRequest();

        when(repository.findAll()).thenReturn(List.of(new Admin()));
        lenient().when(resourceCreator.of(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST))
                .thenReturn(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), AdminValidators.ADMIN_ALREADY_EXISTS_VALIDATOR_NAME);
        assertEquals(error.getCause(), getErrorMessage(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST));
    }

    @Test
    public void shouldNotReturnApiErrorWhenAminNotExists() {
        // given
        AdminRequestApiDto dto = adminUtil.prepareAdminRequest();

        when(repository.findAll()).thenReturn(Collections.emptyList());
        lenient().when(resourceCreator.of(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST))
                .thenReturn(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }
}
