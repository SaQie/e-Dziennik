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
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

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
    public void shouldUpdateBooleanSettingValueById() {
        // given
        Long id = 1L;

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(id, new SettingsValue(true));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertTrue(settingsDto.getBooleanValue());
    }

    @Test
    public void shouldUpdateStringSettingValueById() {
        // given
        String expectedSettingValue = "Test";
        Long id = 1L;

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(id, new SettingsValue(expectedSettingValue));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertEquals(expectedSettingValue, settingsDto.getStringValue());
    }

    @Test
    public void shouldUpdateLongSettingValueById() {
        // given
        Long expectedLongValue = 999L;
        Long id = 1L;

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(id, new SettingsValue(expectedLongValue));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertEquals(expectedLongValue, settingsDto.getLongValue());
    }

    @Test
    public void shouldNullAllOtherSettingValuesWhenUpdate() {
        // given
        Long expectedLongValue = 999L;
        Long id = 1L;

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(id, new SettingsValue(expectedLongValue));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertEquals(expectedLongValue, settingsDto.getLongValue());
        assertNull(settingsDto.getBooleanValue());
        assertNull(settingsDto.getStringValue());
    }

    @Test
    public void shouldThrowsExceptionWhenAllSettingsValuesAreNull() {
        // given
        Long id = 1L;

        settingsService.refreshCache();
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> settingsService.updateSettings(id, new SettingsValue((Long) null)));


        // then
        assertEquals("One of settings value must be not null", exception.getMessage());

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
        assertEquals(settingsDto.getBooleanValue(), false);
    }

}
