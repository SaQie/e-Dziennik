package pl.edziennik.infrastructure.command.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.setting.Settings;
import pl.edziennik.domain.setting.SettingsId;

@Repository
public interface SettingCommandRepository extends JpaRepository<Settings, SettingsId> {
}
