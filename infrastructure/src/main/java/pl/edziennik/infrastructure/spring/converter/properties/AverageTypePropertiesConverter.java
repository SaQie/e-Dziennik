package pl.edziennik.infrastructure.spring.converter.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.edziennik.common.enums.AverageType;

@Component
@ConfigurationPropertiesBinding
public class AverageTypePropertiesConverter implements Converter<String, AverageType> {

    @Override
    public AverageType convert(String source) {
        return AverageType.valueOf(source);
    }
}
