package pl.edziennik.eDziennik.server.settings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.settings.dao.SettingsDao;
import pl.edziennik.eDziennik.server.settings.domain.Settings;
import pl.edziennik.eDziennik.server.settings.domain.SettingsDto;
import pl.edziennik.eDziennik.server.settings.domain.SettingsValue;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
