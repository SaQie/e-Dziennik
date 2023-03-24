package pl.edziennik.eDziennik.domain.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingsRepostory extends JpaRepository<Settings, Long> {

    Optional<Settings> findByName(String name);

    @Query("SELECT NEW pl.edziennik.eDziennik.domain.settings.dto.SettingsDto(s.id,s.name, s.booleanValue, s.stringValue, s.longValue) " +
            "FROM Settings s")
    List<SettingsDto> getAllSettings();

}
