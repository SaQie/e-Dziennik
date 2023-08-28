package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.classroom.classroomsforschool.ClassRoomForSchoolView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class ClassRoomQueryProjectionTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnClassRoomForSchoolView() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123", "12312312");
        createClassRoom(schoolId, "122A");
        createClassRoom(schoolId, "123A");

        // when
        Page<ClassRoomForSchoolView> view = classRoomQueryRepository.getClassRoomForSchoolView(Pageable.unpaged(), schoolId);

        // then
        assertNotNull(view);
        List<ClassRoomForSchoolView> content = view.getContent();
        assertEquals(2, content.size());
        assertEquals("122A", content.get(0).classRoomName().value());
        assertEquals("123A", content.get(1).classRoomName().value());
    }

}
