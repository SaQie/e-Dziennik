package pl.edziennik.eDziennik.domain.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SettingsIntegrationTest extends BaseTest {


    @Test
    public void shouldFindAllSettings() {
        // when
        settingsService.refreshCache();
        List<SettingsDto> allSettings = settingsService.getAllSettings();

        // then
        assertEquals(expectedNumberOfSettings.intValue(), allSettings.size());
    }

    @Test
    public void shouldUpdateSettingById() {
        // given
        Long id = 1L;

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(id, new SettingsValue(true));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertTrue(settingsDto.isEnabled());
    }


    @Test
    public void shouldReturnSettingByName() {
        // given
        String settingName = SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD;

        settingsService.refreshCache();
        // when
        SettingsDto settingsDto = settingsService.getSettingsDataByName(settingName);

        // then
        assertEquals(settingsDto.getId(), 1L);
        assertEquals(settingsDto.isEnabled(), false);
    }

}
