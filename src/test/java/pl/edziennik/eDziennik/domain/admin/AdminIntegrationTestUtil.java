package pl.edziennik.eDziennik.domain.admin;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;

/**
 * Util class for admin integration tests {@link AdminIntegrationTest}
 */
public class AdminIntegrationTestUtil {

    public AdminRequestApiDto prepareAdminRequest(){
        return new AdminRequestApiDto("Admin", "Admin@admin.pl", "test123");
    }



}
