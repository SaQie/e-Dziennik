package pl.edziennik.common.attribute;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pl.edziennik.common.enums.Grade;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class GradeAttributeConverter implements AttributeConverter<Grade, String> {

    @Override
    public String convertToDatabaseColumn(Grade grade) {
        if (grade == null) {
            return null;
        }
        return grade.grade;
    }

    @Override
    public Grade convertToEntityAttribute(String input) {
        if (input == null) {
            return null;
        }
        return Stream.of(Grade.values())
                .filter(grade -> grade.grade.equals(input))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
