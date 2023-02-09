package pl.edziennik.eDziennik.server.user.domain;

import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.user.domain.dto.UserRequestDto;

public class UserMapper {

    public static User toEntity(UserRequestDto dto){
        return new User(dto.getUsername(), dto.getPassword(), dto.getEmail());
    }

    public static User toEntity(StudentRequestApiDto dto){
        return new User(dto.getUsername(), dto.getPassword(), dto.getEmail());
    }

    public static User toEntity(AdminRequestApiDto dto){
        return new User(dto.getUsername(), dto.getPassword(), dto.getEmail());
    }

    public static User toEntity(TeacherRequestApiDto dto){
        return new User(dto.getUsername(), dto.getPassword(), dto.getEmail());
    }

    public static UserRequestDto toDto(StudentRequestApiDto dto){
        return UserRequestDto.builder()
                .city(dto.getCity())
                .pesel(dto.getPesel())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .postalCode(dto.getPostalCode())
                .address(dto.getAddress())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(Role.RoleConst.ROLE_STUDENT.name())
                .build();
    }

    public static UserRequestDto toDto(TeacherRequestApiDto dto){
        return UserRequestDto.builder()
                .city(dto.getCity())
                .pesel(dto.getPesel())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .postalCode(dto.getPostalCode())
                .address(dto.getAddress())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(Role.RoleConst.ROLE_TEACHER.name())
                .build();
    }

    public static UserRequestDto toDto(AdminRequestApiDto dto){
        return UserRequestDto.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(Role.RoleConst.ROLE_ADMIN.name())
                .build();
    }

}
