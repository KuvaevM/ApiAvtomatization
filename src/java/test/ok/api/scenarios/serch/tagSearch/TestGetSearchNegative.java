package ok.api.scenarios.serch.tagSearch;

import ok.api.AbstractTest;
import ok.api.client.AbstractHttpClient;
import ok.api.util.TestUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;

import static ok.api.util.TestUtil.tryExtractError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// https://apiok.ru/dev/methods/rest/search/search.tagSearch
public class TestGetSearchNegative extends AbstractTest {
  @ParameterizedTest
  @MethodSource("provideHttpClients")
  public void testApi(AbstractHttpClient client) throws IOException {
    client.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
    client.setCredentials("non valid token", applicationSecrets.sessionSecret());

    var requestBuilder = client.buildRequest("GET", "search", "tagSearch", Map.of("query", "hello", "filter",
        Map.of("since", "2020", "until", "2022").toString(), "count", "999"));
    client.addRequestApplication(requestBuilder);
    client.addRequestAccessToken(requestBuilder);
    client.signRequest(requestBuilder);
    var response = client.execute(requestBuilder.build());
    assertEquals(200, response.getStatusLine().getStatusCode());

    var contentStr = TestUtil.readContentToString(response);
    var error = tryExtractError(contentStr);
    assertNotNull(error);
    assertEquals(103, error.errorCode);
  }
}

