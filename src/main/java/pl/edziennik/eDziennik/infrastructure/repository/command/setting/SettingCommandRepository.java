package pl.edziennik.eDziennik.infrastructure.repository.command.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.domain.settings.domain.wrapper.SettingsId;

@Repository
public interface SettingCommandRepository extends JpaRepository<Settings, SettingsId> {
}
