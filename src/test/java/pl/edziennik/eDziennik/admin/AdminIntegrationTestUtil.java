package pl.edziennik.eDziennik.admin;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;

/**
 * Util class for admin integration tests {@link AdminIntegrationTest}
 */
class AdminIntegrationTestUtil {

    protected AdminRequestApiDto prepareAdminRequest(){
        return new AdminRequestApiDto("Admin", "Admin@admin.pl", "test123");
    }



}
