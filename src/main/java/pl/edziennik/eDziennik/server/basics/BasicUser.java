package pl.edziennik.eDziennik.server.basics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class BasicUser{

    private String username;
    private String password;
    private LocalDate createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime lastLoginDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedDate = LocalDateTime.now();
    }

}
