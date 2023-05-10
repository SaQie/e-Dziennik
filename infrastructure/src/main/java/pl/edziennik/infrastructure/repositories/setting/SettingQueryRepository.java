package pl.edziennik.infrastructure.repositories.setting;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SettingsId;
import pl.edziennik.domain.setting.Settings;

@RepositoryDefinition(domainClass = Settings.class, idClass = SettingsId.class)
public interface SettingQueryRepository{
}
