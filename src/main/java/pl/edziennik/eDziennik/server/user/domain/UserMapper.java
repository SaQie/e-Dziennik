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
        return new UserRequestDto(dto.getUsername(), dto.getPassword(), dto.getEmail(), Role.RoleConst.ROLE_STUDENT.name());
    }

    public static UserRequestDto toDto(TeacherRequestApiDto dto){
        return new UserRequestDto(dto.getUsername(), dto.getPassword(), dto.getEmail(), Role.RoleConst.ROLE_TEACHER.name());
    }

    public static UserRequestDto toDto(AdminRequestApiDto dto){
        return new UserRequestDto(dto.getUsername(), dto.getPassword(), dto.getEmail(), Role.RoleConst.ROLE_ADMIN.name());
    }

}
