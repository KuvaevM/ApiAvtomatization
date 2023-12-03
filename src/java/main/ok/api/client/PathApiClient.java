package ok.api.client;

import ok.api.config.HttpClientConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.util.Map;

public class PathApiClient extends AbstractHttpClient {
    public PathApiClient(HttpClientConfig config) {
        super(config, "/api/ok");
    }

    @Override
    public HttpUriRequest buildRequest(String restMethod, String methodGroup, String methodName, Map<String, String> parameters) {
        RequestBuilder builder = RequestBuilder.create(restMethod)
                .setUri(path + '/' + methodGroup + '/' + methodName);
        parameters.forEach(builder::addParameter);
        return builder.build();
    }
}
