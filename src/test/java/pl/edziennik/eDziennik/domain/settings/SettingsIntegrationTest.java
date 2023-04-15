package pl.edziennik.eDziennik.domain.settings;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.settings.domain.wrapper.SettingsId;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SettingsIntegrationTest extends BaseTesting {


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
        SettingsId settingsId = SettingsId.wrap(1L);

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(settingsId, new SettingsValue(true, null, null));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertTrue(settingsDto.booleanValue());
    }

    @Test
    public void shouldUpdateStringSettingValueById() {
        // given
        String expectedSettingValue = "Test";
        SettingsId settingsId = SettingsId.wrap(1L);

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(settingsId, new SettingsValue(null,expectedSettingValue, null));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertEquals(expectedSettingValue, settingsDto.stringValue());
    }

    @Test
    public void shouldUpdateLongSettingValueById() {
        // given
        Long expectedLongValue = 999L;
        SettingsId settingsId = SettingsId.wrap(1L);

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(settingsId, new SettingsValue(null,null,expectedLongValue));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertEquals(expectedLongValue, settingsDto.longValue());
    }

    @Test
    public void shouldNullAllOtherSettingValuesWhenUpdate() {
        // given
        Long expectedLongValue = 999L;
        SettingsId settingsId = SettingsId.wrap(1L);

        settingsService.refreshCache();
        // when
        settingsService.updateSettings(settingsId, new SettingsValue(null,null,expectedLongValue));

        // then
        SettingsDto settingsDto = settingsService.getSettingsDataByName(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD);
        assertEquals(expectedLongValue, settingsDto.longValue());
        assertNull(settingsDto.booleanValue());
        assertNull(settingsDto.stringValue());
    }

    @Test
    public void shouldThrowsExceptionWhenAllSettingsValuesAreNull() {
        // given
        SettingsId settingsId = SettingsId.wrap(1L);

        settingsService.refreshCache();
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> settingsService.updateSettings(settingsId, new SettingsValue(null,null,(Long) null)));


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
        assertEquals(settingsDto.id(), SettingsId.wrap(1L));
        assertEquals(settingsDto.booleanValue(), false);
    }

}
