package pl.edziennik.common.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "schoolclass.config")
public class SchoolClassConfigurationProperties {

    @Min(1)
    @NotNull
    private Integer maxStudentsSize;

    @NotNull
    private Boolean autoAssignSubjects;

}
