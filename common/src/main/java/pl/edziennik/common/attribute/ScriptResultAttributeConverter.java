package pl.edziennik.common.attribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ScriptResultAttributeConverter implements AttributeConverter<String, String> {

    private static final ObjectMapper objMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(String scriptResult) {
        try {
            return objMapper.writeValueAsString(scriptResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String jsonValue) {
        try {
            return objMapper.readValue(jsonValue, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
