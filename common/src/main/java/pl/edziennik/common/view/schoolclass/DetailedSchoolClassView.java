package pl.edziennik.common.view.schoolclass;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.util.ArrayList;
import java.util.List;

public record DetailedSchoolClassView(
        SchoolClassId schoolClassId,
        Name className,
        TeacherId supervisingTeacherId,
        FullName supervisingTeacherName,
        List<StudentSummaryForSchoolClassView> students
) {

    public DetailedSchoolClassView(SchoolClassId schoolClassId, Name className, TeacherId supervisingTeacherId, FullName supervisingTeacherName) {
        this(schoolClassId, className, supervisingTeacherId, supervisingTeacherName, new ArrayList<>());
    }

    public DetailedSchoolClassView(DetailedSchoolClassView dto, List<StudentSummaryForSchoolClassView> students) {
        this(dto.schoolClassId, dto.className, dto.supervisingTeacherId, dto.supervisingTeacherName, students);
    }

}
