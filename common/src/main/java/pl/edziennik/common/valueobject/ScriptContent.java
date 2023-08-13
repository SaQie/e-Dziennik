package pl.edziennik.common.valueobject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Getter
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = "value")
public class ScriptContent implements ValueObject, Serializable {

    @JsonValue
    private final String value;

    private ScriptContent(@NotEmpty String value) {
        this.value = value;
    }

    @JsonCreator
    public static ScriptContent of(@NotEmpty String value) {
        return new ScriptContent(value);
    }

    public static ScriptContent of(@NotNull MultipartFile file) {
        StringBuilder content = new StringBuilder();

        try (InputStream inputStream = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ScriptContent(content.toString());
    }

    @Override
    public String toString() {
        return value;
    }

}
