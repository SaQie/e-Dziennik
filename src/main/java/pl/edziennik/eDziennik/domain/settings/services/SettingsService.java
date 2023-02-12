package pl.edziennik.eDziennik.domain.settings.services;

import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;

import java.util.List;

public interface SettingsService {

    String AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD = "automatically.insert.student.subjects.when.add";
    String ALLOW_TO_CREATE_STUDENT_ACCOUNTS_INDEPENDENT = "allow.to.create.student.accounts.independent";

    SettingsDto getSettingsDataByName(String name);

    List<SettingsDto> getAllSettings();

    void updateSetting(String name, SettingsValue value);

    void updateSettings(Long id, SettingsValue value);

    void refreshCache();
}
