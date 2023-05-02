package pl.edziennik.eDziennik.server.basic.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Basic class for User
 */
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class BasicUser implements Serializable {

    private String username;
    private String password;
    private LocalDate createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime lastLoginDate;
    private String email;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

}
