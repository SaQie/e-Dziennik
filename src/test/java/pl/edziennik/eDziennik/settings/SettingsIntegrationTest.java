package pl.edziennik.eDziennik.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.settings.domain.SettingsDto;
import pl.edziennik.eDziennik.server.settings.domain.SettingsValue;
import pl.edziennik.eDziennik.server.settings.services.SettingsService;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SettingsIntegrationTest extends BaseTest {

    @Autowired
    private SettingsService service;

    private BigInteger expectedNumberOfSettings;

    @BeforeEach
    void prepareDb() {
        clearDb();
        fillDbWithData();
        expectedNumberOfSettings = (BigInteger) em.createNativeQuery("select count(*) from app_settings").getSingleResult();
    }

    @Test
    public void shouldFindAllSettings(){
        // when
        service.refreshCache();
        List<SettingsDto> allSettings = service.getAllSettings();

        // then
        assertEquals(expectedNumberOfSettings.intValue(), allSettings.size());
    }

    @Test
    public void shouldUpdateSettingById(){
        // given
        Long id = 1L;

        service.refreshCache();
        // when
        service.updateSettings(id, new SettingsValue(true));

        // then
        SettingsDto settingsDto = service.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertTrue(settingsDto.isEnabled());
    }


    @Test
    public void shouldReturnSettingByName(){
        // given
        String settingName = SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD;

        service.refreshCache();
        // when
        SettingsDto settingsDto = service.getSettingsDataByName(settingName);

        // then
        assertEquals(settingsDto.getId(), 1L);
        assertEquals(settingsDto.isEnabled(), false);
    }

}
