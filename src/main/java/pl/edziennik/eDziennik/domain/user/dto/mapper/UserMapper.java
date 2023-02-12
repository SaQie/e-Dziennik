package pl.edziennik.eDziennik.domain.user.dto.mapper;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;

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
