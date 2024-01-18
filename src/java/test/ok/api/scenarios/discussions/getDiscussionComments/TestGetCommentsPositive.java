package ok.api.scenarios.discussions.getDiscussionComments;

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

public class TestGetCommentsPositive extends AbstractTest{
  @ParameterizedTest
  @MethodSource("provideHttpClients")
  public void testApi(AbstractHttpClient client) throws IOException {
    client.setApplication(applicationSecrets.applicationId(), applicationSecrets.applicationKey(), applicationSecrets.applicationSecretKey());
    client.setCredentials(applicationSecrets.accessToken(), applicationSecrets.sessionSecret());

    var requestBuilder = client.buildRequest("GET", "discussions", "getDiscussionComments", Map.of("entityId", "4398753", "entityType", "GROUP_PHOTO", "offset", "1", "count", "45"));
    client.addRequestApplication(requestBuilder);
    client.addRequestAccessToken(requestBuilder);
    client.signRequest(requestBuilder);
    var response = client.execute(requestBuilder.build());
    assertEquals(200, response.getStatusLine().getStatusCode());

    var contentStr = TestUtil.readContentToString(response);
    var error = tryExtractError(contentStr);
    assertNull(error);

    var comments = MAPPER.readValue(contentStr, TestGetCommentsPositive.GetDiscussionResponse.class);
    assertNotNull(comments);
    assertNotNull(comments.totalCount);
    assertTrue(comments.totalCount > 0);
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class GetDiscussionResponse {
    @JsonProperty("totalCount")
    public Integer totalCount;
  }
}
