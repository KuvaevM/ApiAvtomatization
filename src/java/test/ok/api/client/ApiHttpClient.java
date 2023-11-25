package ok.api.client;

import ok.api.config.ApiClientConfig;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiHttpClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String endpoint;

    private final String DEFAULT_PATH = "/fb.do";

    public ApiHttpClient(ApiClientConfig config) {
        this.endpoint = config.host() + ':' + config.port();
    }

    public HttpResponse<String> send(String method, Map<String, String> parameters)
            throws URISyntaxException, IOException, InterruptedException {
        return send(method, DEFAULT_PATH, parameters);
    }
    public HttpResponse<String> send(String method, String path, Map<String, String> parameters)
            throws URISyntaxException, IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(buildUri(path, parameters))
                .method(method, HttpRequest.BodyPublishers.noBody())
                .build();
        return client.send(req, HttpResponse.BodyHandlers.ofString());
    }

    public String signature(Map<String, String> params) {
        // TODO: рассчет подписи https://apiok.ru/dev/methods/
        return null;
    }

    private URI buildUri(String path, Map<String, String> parameters) throws URISyntaxException {
        String paramsStr = parameters.entrySet().stream()
                .map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining("&"));
        return new URI(endpoint + path + '?' + paramsStr);
    }
}
