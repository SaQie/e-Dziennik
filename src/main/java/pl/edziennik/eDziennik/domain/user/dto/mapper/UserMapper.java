package pl.edziennik.eDziennik.domain.user.dto.mapper;

import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;

public class UserMapper {

    public static User toEntity(UserRequestDto dto) {
        return new User(dto.username(), dto.password(), dto.email());
    }

    public static User toEntity(StudentRequestApiDto dto) {
        return new User(dto.username(), dto.password(), dto.email());
    }

    public static User toEntity(AdminRequestApiDto dto) {
        return new User(dto.username(), dto.password(), dto.email());
    }

    public static User toEntity(TeacherRequestApiDto dto) {
        return new User(dto.username(), dto.password(), dto.email());
    }

    public static User toEntity(ParentRequestApiDto dto) {
        return new User(dto.username(), dto.password(), dto.email());
    }

    public static UserRequestDto toDto(StudentRequestApiDto dto) {
        return UserRequestDto.builder()
                .email(dto.email())
                .username(dto.username())
                .password(dto.password())
                .role(Role.RoleConst.ROLE_STUDENT.name())
                .build();
    }

    public static UserRequestDto toDto(ParentRequestApiDto dto) {
        return UserRequestDto.builder()
                .email(dto.email())
                .username(dto.username())
                .password(dto.password())
                .role(Role.RoleConst.ROLE_PARENT.name())
                .build();
    }

    public static UserRequestDto toDto(TeacherRequestApiDto dto) {
        return UserRequestDto.builder()
                .email(dto.email())
                .username(dto.username())
                .password(dto.password())
                .role(Role.RoleConst.ROLE_TEACHER.name())
                .build();
    }

    public static UserRequestDto toDto(AdminRequestApiDto dto) {
        return UserRequestDto.builder()
                .email(dto.email())
                .username(dto.username())
                .password(dto.password())
                .role(Role.RoleConst.ROLE_ADMIN.name())
                .build();
    }

}
