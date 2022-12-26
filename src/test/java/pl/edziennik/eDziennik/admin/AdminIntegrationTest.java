package pl.edziennik.eDziennik.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.admin.domain.Admin;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.services.AdminService;
import pl.edziennik.eDziennik.server.admin.services.validator.AdminValidators;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.services.validator.SchoolValidators;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class AdminIntegrationTest extends BaseTest {

    @Autowired
    private AdminService service;

    private AdminIntegrationTestUtil util;

    @BeforeEach
    public void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    public AdminIntegrationTest() {
        this.util = new AdminIntegrationTestUtil();
    }

    @Test
    public void shouldAddNewAdministratorAccount(){
        // given
        AdminRequestApiDto expected = util.prepareAdminRequest();

        // when
        Long id = service.createNewAdminAccount(expected).getId();

        // then
        assertNotNull(id);
        Admin actual = find(Admin.class, id);

        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getEmail(), actual.getEmail());

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToAddMoreThanOneAdministratorAccount(){
        // given
        String expectedValidatorName = "AdminAlreadyExistValidator";
        AdminRequestApiDto firstAdminAccount = util.prepareAdminRequest();
        Long id = service.createNewAdminAccount(firstAdminAccount).getId();
        assertNotNull(id);

        AdminRequestApiDto secondAdminAccount = util.prepareAdminRequest();
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createNewAdminAccount(secondAdminAccount));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        String expectedExceptionMessage = resourceCreator.of(AdminValidators.EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
