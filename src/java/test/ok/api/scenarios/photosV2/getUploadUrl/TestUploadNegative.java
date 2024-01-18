package ok.api.scenarios.photosV2.getUploadUrl;

import java.util.List;
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

// https://apiok.ru/dev/methods/rest/photosv2/getUploadUrl
public class TestUploadNegative extends AbstractTest {
  @ParameterizedTest
  @MethodSource("provideHttpClients")
  public void testApi(AbstractHttpClient client) throws IOException {
    client.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
    client.setCredentials("non valid token", applicationSecrets.sessionSecret());

    var requestBuilder = client.buildRequest("GET", "photosV2", "getUploadUrl", Map.of("aid", "1", "sizes",
        List.of("133389", "103100").toString(), "count", "2"));
    client.addRequestApplication(requestBuilder);
    client.addRequestAccessToken(requestBuilder);
    client.signRequest(requestBuilder);
    var response = client.execute(requestBuilder.build());
    assertEquals(200, response.getStatusLine().getStatusCode());

    var contentStr = TestUtil.readContentToString(response);
    var error = tryExtractError(contentStr);
    assertNotNull(error);
    assertEquals(160, error.errorCode);
  }
}

