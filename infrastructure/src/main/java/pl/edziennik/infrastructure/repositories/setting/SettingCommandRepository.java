package pl.edziennik.infrastructure.repositories.setting;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.setting.Settings;
import pl.edziennik.domain.setting.SettingsId;

@RepositoryDefinition(domainClass = Settings.class, idClass = SettingsId.class)
public interface SettingCommandRepository {
}
