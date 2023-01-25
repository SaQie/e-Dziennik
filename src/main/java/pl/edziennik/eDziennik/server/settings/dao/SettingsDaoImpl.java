package pl.edziennik.eDziennik.server.settings.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.settings.domain.Settings;
import pl.edziennik.eDziennik.server.settings.domain.SettingsDto;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class SettingsDaoImpl extends BaseDao<Settings> implements SettingsDao {

    @Override
    public List<SettingsDto> getAllSettingsData() {
        Query query = em.createNamedQuery(Queries.GET_ALL_SETTINGS_DATA);
        return query.getResultList();
    }

    @Override
    public Optional<Settings> findByName(String name) {
        Query query = em.createNamedQuery(Queries.FIND_SETTINGS_DATA_BY_NAME);
        return PersistanceHelper.getSingleResultAsOptional(query);
    }

    private static final class Parameters {

    }

    private static final class Queries {

        public static final String GET_ALL_SETTINGS_DATA = "Settings.getAllSettingsData";
        public static final String FIND_SETTINGS_DATA_BY_NAME = "Settings.findSettingsDataByName";

    }
}
