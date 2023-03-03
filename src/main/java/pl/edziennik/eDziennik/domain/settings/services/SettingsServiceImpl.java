package pl.edziennik.eDziennik.domain.settings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.domain.settings.dao.SettingsDao;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
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

    // Cache for settings
    private List<SettingsDto> cacheSettingsList;

    @Override
    public SettingsDto getSettingsDataByName(String name) {
        return getSettingsDataFromCacheByName(name);
    }

    @Override
    public List<SettingsDto> getAllSettings() {
        List<SettingsDto> translatedSettingsData = new ArrayList<>();
        for (SettingsDto settingsDto : cacheSettingsList) {
            translatedSettingsData.add(new SettingsDto(settingsDto.getId(), resourceCreator.of(settingsDto.getName()), settingsDto.isEnabled()));
        }
        return translatedSettingsData;
    }

    @Override
    public void updateSetting(String name, SettingsValue value) {
        Settings settings = settingsDao.findByName(name)
                .orElseThrow(() -> new BusinessException("Setting with name " + name + " not exists"));
        settings.setValue(value.getEnabled());
        settingsDao.saveOrUpdate(settings);
        refreshCache();
    }


    @Override
    public void updateSettings(Long id, SettingsValue value) {
        Settings settings = settingsDao.get(id);
        settings.setValue(value.getEnabled());
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
        if (settingsDto == null) {
            throw new BusinessException("Setting with name " + name + " Not found !");
        }
        return new SettingsDto(settingsDto.getId(), resourceCreator.of(settingsDto.getName()), settingsDto.isEnabled());
    }

}