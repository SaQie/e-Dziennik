package pl.edziennik.common.dto.schoolclass;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.util.ArrayList;
import java.util.List;

public record DetailedSchoolClassDto(
        SchoolClassId schoolClassId,
        Name className,
        TeacherId supervisingTeacherId,
        FullName supervisingTeacherName,
        List<StudentSummaryForSchoolClassDto> students
) {

    public DetailedSchoolClassDto(SchoolClassId schoolClassId, Name className, TeacherId supervisingTeacherId, FullName supervisingTeacherName) {
        this(schoolClassId, className, supervisingTeacherId, supervisingTeacherName, new ArrayList<>());
    }

    public DetailedSchoolClassDto(DetailedSchoolClassDto dto, List<StudentSummaryForSchoolClassDto> students) {
        this(dto.schoolClassId, dto.className, dto.supervisingTeacherId, dto.supervisingTeacherName, students);
    }

}
