package pl.edziennik.eDziennik.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.basics.AbstractDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherRequestDto extends AbstractDto {

    private String username;
    private String firstName;
    private String lastName;
    private String adress;
    private String postalCode;
    private String city;
    private String pesel;
    private String role;
    private String phoneNumber;
    private String password;

    private Long school;

}
