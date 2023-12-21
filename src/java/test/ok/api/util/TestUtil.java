package ok.api.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;

import java.nio.charset.Charset;

public class TestUtil {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String readContentToString(HttpResponse response) {
        return new String(response.getEntity().getContent().readAllBytes(), Charset.defaultCharset());
    }

    @SneakyThrows
    public static ErrorResponse tryExtractError(String content) {
        try {
            return MAPPER.readValue(content, ErrorResponse.class);
        } catch (JsonMappingException ignored) {
        }
        return null;
    }
}
