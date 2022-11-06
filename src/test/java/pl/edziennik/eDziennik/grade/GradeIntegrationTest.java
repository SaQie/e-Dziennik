package pl.edziennik.eDziennik.grade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.grade.domain.Grade;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeResponseApiDto;
import pl.edziennik.eDziennik.server.grade.services.GradeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class GradeIntegrationTest extends BaseTest {

    @Autowired
    private GradeService service;

    private final GradeIntegrationTestUtil util;

    @BeforeEach
    public void prepareDb(){
        clearDb();
        fillDbWithData();
    }

    public GradeIntegrationTest() {
        this.util = new GradeIntegrationTestUtil();
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    public void shouldSaveNewGrade(int grade){
        // given
        GradeRequestApiDto expected = util.prepareRequestApi(grade, 1);

        // when
        Long id = service.addNewGrade(expected).getId();

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
        GradeRequestApiDto expected = util.prepareRequestApi(grade, 1);

        // when
        Throwable throwable = catchThrowable(() -> service.addNewGrade(expected));

        // then
        assertThat(throwable).hasMessage("Grade " + grade + " not found");
    }

    @Test
    public void shouldFindGradeWithGivenId(){
        // given
        GradeRequestApiDto expected = util.prepareRequestApi(1, 1);
        Long gradeId = service.addNewGrade(expected).getId();
        assertNotNull(gradeId);

        // when
        Long id = service.findGradeById(gradeId).getId();

        // then
        assertNotNull(id);
        Grade actual = find(Grade.class, id);
        assertEquals(expected.getGrade(), actual.getGrade().grade);
        assertEquals(expected.getWeight(), actual.getWeight());
        assertEquals(expected.getDescription(), actual.getDescription());

    }

}
