package ok.api.users.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ok.api.AbstractTest;
import ok.api.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static ok.api.util.TestUtil.MAPPER;
import static ok.api.util.TestUtil.tryExtractError;
import static org.junit.jupiter.api.Assertions.*;

// https://apiok.ru/dev/methods/rest/users/users.getCurrentUser
public class TestGetCurrentUserPositive extends AbstractTest {

    private static final String UID = "585037496439";

    @Test
    public void testPathApi() throws IOException {
        pathClient.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
        pathClient.setCredentials(applicationSecrets.accessToken(), applicationSecrets.sessionSecret());

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
        methodClient.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
        methodClient.setCredentials(applicationSecrets.accessToken(), applicationSecrets.sessionSecret());

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
