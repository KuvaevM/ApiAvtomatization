package ok.api.client;

import ok.api.config.HttpClientConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PathApiClient extends AbstractHttpClient {
    public PathApiClient(HttpClientConfig config) {
        super(config, "/api");
    }

    @Override
    public RequestBuilder buildRequest(@NotNull String restMethod, @NotNull String methodGroup, @NotNull String methodName, @NotNull Map<String, String> parameters) {
        RequestBuilder builder = RequestBuilder.create(restMethod).setUri(path + '/' + methodGroup + '/' + methodName);
        parameters.forEach(builder::addParameter);
        return builder;
    }
}
