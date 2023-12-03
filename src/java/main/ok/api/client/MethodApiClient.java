package ok.api.client;

import ok.api.config.HttpClientConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.util.Map;

public class MethodApiClient extends AbstractHttpClient {
    public MethodApiClient(HttpClientConfig config) {
        super(config, "/fb.do");
    }

    @Override
    public HttpUriRequest buildRequest(String restMethod, String methodGroup, String methodName, Map<String, String> parameters) {
        RequestBuilder builder = RequestBuilder.create(restMethod)
                .setUri(path);
        builder.addParameter("method", methodGroup + '.' + methodName);
        parameters.forEach(builder::addParameter);
        return builder.build();
    }
}
