package pl.edziennik.eDziennik.domain.admin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.services.validator.AdminValidators;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class AdminIntegrationTest extends BaseTesting {

    @Test
    public void shouldAddNewAdministratorAccount() {
        // given
        AdminRequestApiDto expected = adminUtil.prepareAdminRequest();

        // when
        AdminId id = adminService.createNewAdminAccount(expected).adminId();

        // then
        assertNotNull(id);
        Admin actual = find(Admin.class, id);

        assertEquals(expected.username(), actual.getUser().getUsername());
        assertEquals(expected.email(), actual.getUser().getEmail());

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToAddMoreThanOneAdministratorAccount() {
        // given
        String expectedValidatorName = "AdminAlreadyExistValidator";
        AdminRequestApiDto firstAdminAccount = adminUtil.prepareAdminRequest();
        Long id = adminService.createNewAdminAccount(firstAdminAccount).adminId().id();
        assertNotNull(id);

        AdminRequestApiDto secondAdminAccount = adminUtil.prepareAdminRequest();
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> adminService.createNewAdminAccount(secondAdminAccount));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        String expectedExceptionMessage = resourceCreator.of(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
