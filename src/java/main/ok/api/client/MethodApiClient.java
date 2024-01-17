package ok.api.client;

import ok.api.config.HttpClientConfig;
import org.apache.http.client.methods.RequestBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MethodApiClient extends AbstractHttpClient {

    public MethodApiClient(HttpClientConfig config) {
        super(config, "/fb.do");
    }

    @Override
    public RequestBuilder buildRequest(@NotNull String restMethod, @NotNull String methodGroup, @NotNull String methodName, @NotNull Map<String, String> parameters) {
        RequestBuilder builder = RequestBuilder.create(restMethod).setUri(path);
        builder.addParameter("method", methodGroup + '.' + methodName);
        parameters.forEach(builder::addParameter);
        return builder;
    }
}
