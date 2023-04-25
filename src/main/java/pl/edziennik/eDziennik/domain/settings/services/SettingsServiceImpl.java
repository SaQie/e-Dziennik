package pl.edziennik.eDziennik.domain.settings.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.settings.domain.Settings;
import pl.edziennik.eDziennik.domain.settings.domain.wrapper.SettingsId;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsDto;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
import pl.edziennik.eDziennik.domain.settings.repository.SettingsRepostory;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.ArrayList;
import java.util.List;

@Service
class SettingsServiceImpl extends BaseService implements SettingsService {

    @Autowired
    private SettingsRepostory settingsRepostory;

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

    private void addCorrectValue(final SettingsDto settingsDto, final List<SettingsDto> translatedSettingsData) {
        SettingsDto.SettingsDtoBuilder settingsDtoBuilder = SettingsDto.builder()
                .id(settingsDto.id())
                .name(resourceCreator.of(settingsDto.name()))
                .booleanValue(settingsDto.booleanValue())
                .stringValue(settingsDto.stringValue())
                .longValue(settingsDto.longValue());
        translatedSettingsData.add(settingsDtoBuilder.build());

    }

    @Override
    public void updateSetting(final String name, final SettingsValue value) {
        Settings settings = settingsRepostory.findByName(name)
                .orElseThrow(() -> new BusinessException("Setting with name " + name + " not exists"));
        setCorrectValue(settings, value);
        settingsRepostory.save(settings);
        refreshCache();
    }

    @Override
    public SettingsDto findSettingById(SettingsId settingsId) {
        refreshCache();
        return getSettingsDataFromCacheById(settingsId);
    }

    @Override
    public void updateSettings(final SettingsId settingsId, final SettingsValue value) {
        Settings settings = settingsRepostory.findById(settingsId)
                .orElseThrow(notFoundException(settingsId, Settings.class));
        setCorrectValue(settings, value);
        settingsRepostory.save(settings);
        refreshCache();
    }

    private void setCorrectValue(final Settings settings, final SettingsValue value) {
        settings.setLongValue(null);
        settings.setBooleanValue(null);
        settings.setStringValue(null);
        if (value.booleanValue() != null) {
            settings.setBooleanValue(value.booleanValue());
            return;
        }
        if (value.stringValue() != null) {
            settings.setStringValue(value.stringValue());
            return;
        }
        if (value.longValue() != null) {
            settings.setLongValue(value.longValue());
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
        return settingsRepostory.getAllSettings();
    }


    private SettingsDto getSettingsDataFromCacheByName(final String name) {
        List<SettingsDto> translatedSettingsData = new ArrayList<>(cacheSettingsList);
        SettingsDto settingsDto = translatedSettingsData.stream()
                .filter(data -> data.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        if (settingsDto == null) {
            throw new BusinessException("Setting with name " + name + " Not found !");
        }
        return returnCorrectSettingsDto(settingsDto);
    }

    private SettingsDto getSettingsDataFromCacheById(final SettingsId id) {
        List<SettingsDto> translatedSettingsData = new ArrayList<>(cacheSettingsList);
        SettingsDto settingsDto = translatedSettingsData.stream()
                .filter(data -> data.id().equals(id))
                .findFirst()
                .orElse(null);
        if (settingsDto == null) {
            throw new BusinessException("Setting with id " + id + " Not found !");
        }
        return returnCorrectSettingsDto(settingsDto);
    }

    private SettingsDto returnCorrectSettingsDto(final SettingsDto settingsDto) {
        SettingsId settingId = settingsDto.id();
        String translatedName = resourceCreator.of(settingsDto.name());
        if (settingsDto.booleanValue() == null && settingsDto.stringValue() == null && settingsDto.longValue() == null) {
            throw new BusinessException(resourceCreator.of("setting.value.not.set", settingsDto.name()));
        }
        return SettingsDto.builder()
                .id(settingId)
                .name(translatedName)
                .booleanValue(settingsDto.booleanValue())
                .stringValue(settingsDto.stringValue())
                .longValue(settingsDto.longValue())
                .build();

    }

}
