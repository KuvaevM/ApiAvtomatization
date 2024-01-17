package ok.api.client;

import ok.api.config.HttpClientConfig;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClient {
    private final HttpClientConfig config = new HttpClientConfig("localhost", 7777);

    @Test
    public void testPathApiHttpRequest() {
        PathApiClient client = new PathApiClient(config);
        var request = client.buildRequest("GET", "group", "name", Map.of("key", "value")).build();
        assertEquals("/api/group/name?key=value", request.getURI().toString());
    }

    @Test
    public void testMethodApiHttpRequest() {
        MethodApiClient client = new MethodApiClient(config);
        var request = client.buildRequest("GET", "group", "name", Map.of("key", "value")).build();
        assertEquals("/fb.do?method=group.name&key=value", request.getURI().toString());
    }
}
