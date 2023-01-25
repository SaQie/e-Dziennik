package pl.edziennik.eDziennik.server.settings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.settings.dao.SettingsDao;
import pl.edziennik.eDziennik.server.settings.domain.Settings;
import pl.edziennik.eDziennik.server.settings.domain.SettingsDto;
import pl.edziennik.eDziennik.server.settings.domain.SettingsValue;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private ResourceCreator resourceCreator;

    private List<SettingsDto> cacheSettingsList;

    @Override
    public SettingsDto getSettingsDataByName(String name) {
        return getSettingsDataFromCacheByName(name);
    }

    @Override
    public List<SettingsDto> getAllSettings() {
        List<SettingsDto> translatedSettingsData = new ArrayList<>();
        for (SettingsDto settingsDto : cacheSettingsList) {
            translatedSettingsData.add(new SettingsDto(settingsDto.getId(), resourceCreator.of(settingsDto.getName()), settingsDto.isValue()));
        }
        return translatedSettingsData;
    }

    @Override
    public void updateSettings(Long id, SettingsValue value) {
        Settings settings = settingsDao.get(id);
        settings.setValue(value.isValue());
        settingsDao.saveOrUpdate(settings);
        refreshCache();
    }

    @Override
    public void refreshCache() {
        cacheSettingsList = getActualSettingsData();
    }

    @PostConstruct
    private void loadSettings() {
        cacheSettingsList = getActualSettingsData();
    }

    private List<SettingsDto> getActualSettingsData() {
        return settingsDao.getAllSettingsData();
    }



    private SettingsDto getSettingsDataFromCacheByName(String name) {
        List<SettingsDto> translatedSettingsData = new ArrayList<>(cacheSettingsList);
        SettingsDto settingsDto = translatedSettingsData.stream()
                .filter(data -> data.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        settingsDto.setName(resourceCreator.of(settingsDto.getName()));
        return settingsDto;
    }

}