package pl.edziennik.eDziennik.domain.grade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class GradeIntegrationTest extends BaseTesting {

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    public void shouldSaveNewGrade(int grade){
        // given
        GradeRequestApiDto expected = gradeUtil.prepareRequestApi(grade, 1);

        // when
        Long id = gradeService.addNewGrade(expected).id();

        // then
        Grade actual = find(Grade.class, id);
        assertNotNull(actual);
        assertEquals(expected.getGrade(), actual.getGrade().grade);
        assertEquals(expected.getWeight(), actual.getWeight());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @ParameterizedTest
    @ValueSource(ints = {7,8,9,-1,-2})
    public void shouldThrowsExceptionWhenTryingToSaveGradeThatNotExist(int grade){
        // given
        GradeRequestApiDto expected = gradeUtil.prepareRequestApi(grade, 1);
        String expectedExceptionMessage = "Grade " + grade + " not found";

        // when
        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> gradeService.addNewGrade(expected));
        assertEquals(exception.getMessage(), expectedExceptionMessage);
    }

    @Test
    public void shouldFindGradeWithGivenId(){
        // given
        GradeRequestApiDto expected = gradeUtil.prepareRequestApi(1, 1);
        Long gradeId = gradeService.addNewGrade(expected).id();
        assertNotNull(gradeId);

        // when
        Long id = gradeService.findGradeById(GradeId.wrap(gradeId)).id();

        // then
        assertNotNull(id);
        Grade actual = find(Grade.class, id);
        assertEquals(expected.getGrade(), actual.getGrade().grade);
        assertEquals(expected.getWeight(), actual.getWeight());
        assertEquals(expected.getDescription(), actual.getDescription());

    }

    @Test
    public void shouldUpdateGrade(){
        // given
        GradeRequestApiDto dto = gradeUtil.prepareRequestApi(1, 1);
        Long gradeId = gradeService.addNewGrade(dto).id();
        GradeRequestApiDto expected = gradeUtil.prepareRequestApi(2, 2);

        // when
        Long updated = gradeService.updateGrade(GradeId.wrap(gradeId), expected).id();

        // then
        assertNotNull(updated);
        assertEquals(updated,gradeId);
        Grade actual = find(Grade.class, updated);
        assertNotNull(actual);
        assertEquals(expected.getGrade(), actual.getGrade().grade);
        assertEquals(expected.getWeight(), actual.getWeight());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToDeleteGradeFromDifferentStudentSubject(){
        // given


        // when


        // then
    }

}
