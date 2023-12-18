package ok.api.users.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import ok.api.client.MethodApiClient;
import ok.api.client.PathApiClient;
import ok.api.config.HttpClientConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static ok.api.users.get.ApplicationSecrets.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// https://apiok.ru/dev/methods/rest/users/users.getCurrentUser
public class TestGetCurrentUser {

    private final HttpClientConfig config = HttpClientConfig.loadFromResources();
    private final PathApiClient pathClient = new PathApiClient(config);
    private final MethodApiClient methodClient = new MethodApiClient(config);
    private final ObjectMapper MAPPER = new ObjectMapper();

    public TestGetCurrentUser() throws IOException {
    }

    private static final String UID = "585037496439";

    @Test
    public void testPathApi() throws IOException {
        pathClient.setApplication(APPLICATION_ID, APPLICATION_KEY, APPLICATION_SECRET_KEY);
        pathClient.setCredentials(ACCESS_TOKEN, SESSION_SECRET);
        var requestBuilder = pathClient.buildRequest("GET", "users", "getCurrentUser", Map.of());
        pathClient.addRequestApplication(requestBuilder);
        pathClient.addRequestAccessToken(requestBuilder);
        pathClient.signRequest(requestBuilder);
        var response = pathClient.execute(requestBuilder.build());
        assertEquals(200, response.getStatusLine().getStatusCode());
//        TODO: deserialize response
//        assertEquals(UID, MAPPER.convertValue(response.getEntity(), GetCurrentUserResponse.class).UID);
    }



    @Test
    public void testMethodApi() throws IOException {
        pathClient.setApplication(APPLICATION_ID, APPLICATION_KEY, APPLICATION_SECRET_KEY);
        pathClient.setCredentials(ACCESS_TOKEN, SESSION_SECRET);
        var request = pathClient.buildRequest("GET", "users", "getCurrentUser", Map.of()).build();
        pathClient.execute(request);
    }

    private static class GetCurrentUserResponse {
        public String UID;
    }
}
