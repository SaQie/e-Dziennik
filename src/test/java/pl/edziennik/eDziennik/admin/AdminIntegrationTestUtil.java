package pl.edziennik.eDziennik.admin;

import pl.edziennik.eDziennik.school.SchoolIntegrationTest;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;

/**
 * Util class for admin integration tests {@link AdminIntegrationTest}
 */
class AdminIntegrationTestUtil {

    protected AdminRequestApiDto prepareAdminRequest(){
        return new AdminRequestApiDto("Admin", "Admin@admin.pl", "test123");
    }



}
