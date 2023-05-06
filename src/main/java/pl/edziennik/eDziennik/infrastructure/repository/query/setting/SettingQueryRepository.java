package pl.edziennik.eDziennik.infrastructure.repository.query.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.domain.settings.domain.wrapper.SettingsId;

@Repository
public interface SettingQueryRepository extends JpaRepository<Settings, SettingsId> {
}
