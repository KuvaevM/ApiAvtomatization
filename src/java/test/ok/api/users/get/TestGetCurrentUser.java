package ok.api.users.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ok.api.client.MethodApiClient;
import ok.api.client.PathApiClient;
import ok.api.config.HttpClientConfig;
import ok.api.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static ok.api.users.get.ApplicationSecrets.*;
import static ok.api.util.TestUtil.MAPPER;
import static ok.api.util.TestUtil.tryExtractError;
import static org.junit.jupiter.api.Assertions.*;

// https://apiok.ru/dev/methods/rest/users/users.getCurrentUser
public class TestGetCurrentUser {

    private final HttpClientConfig config = HttpClientConfig.loadFromResources();
    private final PathApiClient pathClient = new PathApiClient(config);
    private final MethodApiClient methodClient = new MethodApiClient(config);

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

        var contentStr = TestUtil.readContentToString(response);
        var error = tryExtractError(contentStr);
        assertNull(error);

        var currentUser = MAPPER.readValue(contentStr, GetCurrentUserResponse.class);
        assertNotNull(currentUser);
        assertNotNull(currentUser.UID);
        assertEquals(UID, currentUser.UID);
    }

    @Test
    public void testMethodApi() throws IOException {
        methodClient.setApplication(APPLICATION_ID, APPLICATION_KEY, APPLICATION_SECRET_KEY);
        methodClient.setCredentials(ACCESS_TOKEN, SESSION_SECRET);

        var requestBuilder = methodClient.buildRequest("GET", "users", "getCurrentUser", Map.of());
        methodClient.addRequestApplication(requestBuilder);
        methodClient.addRequestAccessToken(requestBuilder);
        methodClient.signRequest(requestBuilder);
        var response = methodClient.execute(requestBuilder.build());
        assertEquals(200, response.getStatusLine().getStatusCode());

        var contentStr = TestUtil.readContentToString(response);
        var error = tryExtractError(contentStr);
        assertNull(error);

        var currentUser = MAPPER.readValue(contentStr, GetCurrentUserResponse.class);
        assertNotNull(currentUser);
        assertNotNull(currentUser.UID);
        assertEquals(UID, currentUser.UID);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GetCurrentUserResponse {
        @JsonProperty("uid")
        public String UID;
    }
}
