package ok.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

public record HttpClientConfig(String host, int port) {

    public static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());
    private static final String CONFIGFILE = "httpconfig.yaml";

    public static HttpClientConfig loadFromResources() throws IOException {
        return MAPPER.readValue(HttpClientConfig.class.getClassLoader().getResource(CONFIGFILE), HttpClientConfig.class);
    }
}
