package ok.api.scenarios.bookmark.add;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ok.api.AbstractTest;
import ok.api.client.AbstractHttpClient;
import ok.api.util.TestUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;

import static ok.api.util.TestUtil.MAPPER;
import static ok.api.util.TestUtil.tryExtractError;
import static org.junit.jupiter.api.Assertions.*;

// https://apiok.ru/dev/methods/rest/bookmark/bookmark.add
public class TestAddBookmarkPositive extends AbstractTest {
    @ParameterizedTest
    @MethodSource("provideHttpClients")
    public void testApi(AbstractHttpClient client) throws IOException {
        client.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
        client.setCredentials(applicationSecrets.accessToken(), applicationSecrets.sessionSecret());

        var requestBuilder = client.buildRequest("GET", "bookmark", "add", Map.of("ref_id", "53038939046008", "bookmark_type", "GROUP"));
        client.addRequestApplication(requestBuilder);
        client.addRequestAccessToken(requestBuilder);
        client.signRequest(requestBuilder);
        var response = client.execute(requestBuilder.build());
        assertEquals(200, response.getStatusLine().getStatusCode());

        var contentStr = TestUtil.readContentToString(response);
        var error = tryExtractError(contentStr);
        assertNull(error);

        var addResponse = MAPPER.readValue(contentStr, AddResponse.class);
        assertNotNull(addResponse);
        assertTrue(addResponse.success);
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AddResponse {
        @JsonProperty("success")
        public Boolean success;
    }
}
