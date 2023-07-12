package pl.edziennik.domain.schoolclass;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.*;
import pl.edziennik.common.properties.SchoolClassConfigurationProperties;
import pl.edziennik.common.valueobject.id.SchoolClassConfigurationId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SchoolClassConfiguration {

    @EmbeddedId
    private SchoolClassConfigurationId schoolClassConfigurationId = SchoolClassConfigurationId.create();

    private Integer maxStudentsSize;

    private Boolean autoAssignSubjects;

    @Version
    private Long version;

    public static SchoolClassConfiguration createConfigFromProperties(SchoolClassConfigurationProperties properties) {
        SchoolClassConfiguration classConfiguration = new SchoolClassConfiguration();

        classConfiguration.autoAssignSubjects = properties.getAutoAssignSubjects();
        classConfiguration.maxStudentsSize = properties.getMaxStudentsSize();

        return classConfiguration;
    }

    public void changeMaxStudentsSize(Integer maxStudentsSize) {
        this.maxStudentsSize = maxStudentsSize;
    }

    public void changeAutoAssignSubject(Boolean value) {
        if (!this.autoAssignSubjects.equals(value)) {
            this.autoAssignSubjects = value;
        }
    }


}
