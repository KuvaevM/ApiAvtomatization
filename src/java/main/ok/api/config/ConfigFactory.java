package ok.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;

public class ConfigFactory {
    public static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    @SneakyThrows
    public static <CONFIG> CONFIG loadFromResources(Class<CONFIG> cfgClass, String cfgFile) {
        return MAPPER.readValue(cfgClass.getClassLoader().getResource(cfgFile), cfgClass);
    }
}
