package pl.edziennik.common.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pl.edziennik.common.enums.AverageType;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "school.config")
public class SchoolConfigurationProperties {

    @NotNull
    private AverageType averageType;

}
