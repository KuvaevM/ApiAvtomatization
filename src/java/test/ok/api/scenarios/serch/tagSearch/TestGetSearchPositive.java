package ok.api.scenarios.serch.tagSearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import ok.api.AbstractTest;
import ok.api.client.AbstractHttpClient;
import ok.api.util.TestUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;

import static ok.api.util.TestUtil.MAPPER;
import static ok.api.util.TestUtil.tryExtractError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// https://apiok.ru/dev/methods/rest/search/search.tagSearch
public class TestGetSearchPositive extends AbstractTest {
  @ParameterizedTest
  @MethodSource("provideHttpClients")
  public void testApi(AbstractHttpClient client) throws IOException {
    client.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
    client.setCredentials(applicationSecrets.accessToken(), applicationSecrets.sessionSecret());

    var requestBuilder = client.buildRequest("GET", "photosV2", "getUploadUrl", Map.of("aid", "1", "sizes",
        List.of("133389", "103104").toString(), "count", "2"));
    client.addRequestApplication(requestBuilder);
    client.addRequestAccessToken(requestBuilder);
    client.signRequest(requestBuilder);
    var response = client.execute(requestBuilder.build());
    assertEquals(200, response.getStatusLine().getStatusCode());

    var contentStr = TestUtil.readContentToString(response);
    var error = tryExtractError(contentStr);
    assertNull(error);

    var url = MAPPER.readValue(contentStr, TestGetSearchPositive.GetUploadUrlResponse.class);
    assertNotNull(url);
    assertNotNull(url.totalCount);
    assertEquals(1, (int) url.totalCount);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class GetUploadUrlResponse {
    @JsonProperty("totalCount")
    public Integer totalCount;
  }
}

