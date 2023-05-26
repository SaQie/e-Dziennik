package pl.edziennik.domain.schoolclass;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;
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

    public static SchoolClassConfiguration defaultConfig() {
        SchoolClassConfiguration classConfiguration = new SchoolClassConfiguration();
        classConfiguration.autoAssignSubjects = Boolean.TRUE;
        classConfiguration.maxStudentsSize = 30;
        // TODO CQRS -> przemyslec sposob jak uniknac hardkodowania tutaj
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
