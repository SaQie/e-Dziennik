package pl.edziennik.eDziennik.domain.settings.dao;

import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.server.utils.PersistanceHelper;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
class SettingsDaoImpl extends BaseDao<Settings> implements SettingsDao {

    @Override
    public List<SettingsDto> getAllSettingsData() {
        Query query = em.createNamedQuery(Queries.GET_ALL_SETTINGS_DATA);
        return query.getResultList();
    }

    @Override
    public Optional<Settings> findByName(String name) {
        Query query = em.createNamedQuery(Queries.FIND_SETTINGS_DATA_BY_NAME);
        query.setParameter(Parameters.NAME, name);
        return PersistanceHelper.getSingleResultAsOptional(query);
    }

    private static final class Parameters {

        public static final String NAME = "name";

    }

    private static final class Queries {

        public static final String GET_ALL_SETTINGS_DATA = "Settings.getAllSettingsData";
        public static final String FIND_SETTINGS_DATA_BY_NAME = "Settings.findSettingsDataByName";

    }
}