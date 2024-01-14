package ok.api.users.get;

import ok.api.AbstractTest;
import ok.api.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static ok.api.util.TestUtil.tryExtractError;
import static org.junit.jupiter.api.Assertions.*;

// https://apiok.ru/dev/methods/rest/users/users.getCurrentUser
public class TestGetCurrentUserNegative extends AbstractTest {

    @Test
    public void testPathApi() throws IOException {
        pathClient.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
        pathClient.setCredentials("invalid token", "invalid secret");

        var requestBuilder = pathClient.buildRequest("GET", "users", "getCurrentUser", Map.of());
        pathClient.addRequestApplication(requestBuilder);
        pathClient.addRequestAccessToken(requestBuilder);
        pathClient.signRequest(requestBuilder);
        var response = pathClient.execute(requestBuilder.build());
        assertEquals(200, response.getStatusLine().getStatusCode());

        var contentStr = TestUtil.readContentToString(response);
        var error = tryExtractError(contentStr);
        assertNotNull(error);
        assertEquals(103, error.errorCode);
    }

    @Test
    public void testMethodApi() throws IOException {
        methodClient.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
        methodClient.setCredentials("invalid token", "invalid secret");

        var requestBuilder = methodClient.buildRequest("GET", "users", "getCurrentUser", Map.of());
        methodClient.addRequestApplication(requestBuilder);
        methodClient.addRequestAccessToken(requestBuilder);
        methodClient.signRequest(requestBuilder);
        var response = methodClient.execute(requestBuilder.build());
        assertEquals(200, response.getStatusLine().getStatusCode());

        var contentStr = TestUtil.readContentToString(response);
        var error = tryExtractError(contentStr);
        assertNotNull(error);
        assertEquals(103, error.errorCode);
    }
}
