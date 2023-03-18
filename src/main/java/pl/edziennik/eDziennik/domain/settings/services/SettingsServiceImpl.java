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
            addCorrectValue(settingsDto, translatedSettingsData);
        }
        return translatedSettingsData;
    }

    private void addCorrectValue(SettingsDto settingsDto, List<SettingsDto> translatedSettingsData) {
        if (settingsDto.getBooleanValue() != null) {
            translatedSettingsData.add(new SettingsDto(settingsDto.getId(), resourceCreator.of(settingsDto.getName()), settingsDto.getBooleanValue()));
            return;
        }
        if (settingsDto.getStringValue() != null) {
            translatedSettingsData.add(new SettingsDto(settingsDto.getId(), resourceCreator.of(settingsDto.getName()), settingsDto.getStringValue()));
            return;
        }
        if (settingsDto.getLongValue() != null) {
            translatedSettingsData.add(new SettingsDto(settingsDto.getId(), resourceCreator.of(settingsDto.getName()), settingsDto.getLongValue()));
        }
    }

    @Override
    public void updateSetting(String name, SettingsValue value) {
        Settings settings = settingsDao.findByName(name)
                .orElseThrow(() -> new BusinessException("Setting with name " + name + " not exists"));
        setCorrectValue(settings, value);
        settingsDao.saveOrUpdate(settings);
        refreshCache();
    }


    @Override
    public void updateSettings(Long id, SettingsValue value) {
        Settings settings = settingsDao.get(id);
        setCorrectValue(settings, value);
        settingsDao.saveOrUpdate(settings);
        refreshCache();
    }

    private void setCorrectValue(Settings settings, SettingsValue value) {
        settings.setLongValue(null);
        settings.setBooleanValue(null);
        settings.setStringValue(null);
        if (value.getBooleanValue() != null) {
            settings.setBooleanValue(value.getBooleanValue());
            return;
        }
        if (value.getStringValue() != null) {
            settings.setStringValue(value.getStringValue());
            return;
        }
        if (value.getLongValue() != null) {
            settings.setLongValue(value.getLongValue());
        }
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
        return returnCorrectSettingsDto(settingsDto);
    }

    private SettingsDto returnCorrectSettingsDto(SettingsDto settingsDto) {
        Long settingId = settingsDto.getId();
        String translatedName = resourceCreator.of(settingsDto.getName());
        if (settingsDto.getBooleanValue() != null) {
            return new SettingsDto(settingId, translatedName, settingsDto.getBooleanValue());
        }
        if (settingsDto.getStringValue() != null) {
            return new SettingsDto(settingId, translatedName, settingsDto.getStringValue());
        }
        if (settingsDto.getLongValue() != null) {
            return new SettingsDto(settingId, translatedName, settingsDto.getLongValue());
        }
        throw new BusinessException(resourceCreator.of("setting.value.not.set", settingsDto.getName()));
    }

}
