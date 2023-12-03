package ok.api.client;

import ok.api.config.HttpClientConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractHttpClient {
    private final HttpClient client = HttpClients.createDefault();
    protected final HttpHost host;
    protected final String path;
    private String applicationId;
    private String applicationKey;
    private String applicationSecretKey;
    private String accessToken;
    private String sessionSecretKey;

    public AbstractHttpClient(HttpClientConfig config, String path) {
        this.host = new HttpHost(config.host(), config.port());
        this.path = path;
    }

    public HttpResponse execute(HttpUriRequest req) throws IOException {
        return client.execute(host, req);
    }

    public abstract HttpUriRequest buildRequest(String restMethod, String apiMethodGroup, String apiMethodName, Map<String, String> parameters);

    public void withApplication(String applicationId, String applicationKey, String applicationSecretKey) {
        this.applicationId = applicationId;
        this.applicationKey = applicationKey;
        this.applicationSecretKey = applicationSecretKey;
    }

    public void withCredentials(String accessToken, String sessionSecretKey) {
        this.accessToken = accessToken;
        this.sessionSecretKey = sessionSecretKey;
    }

    public void signRequest(RequestBuilder builder) {
        String secretKey = sessionSecretKey;
        var params = builder.getParameters();
        if (secretKey == null) {
            secretKey = DigestUtils.md5Hex(accessToken + applicationSecretKey);
        }
        params.sort(Comparator.comparing(NameValuePair::getName));
        builder.addParameter("sig", DigestUtils.md5Hex(
                params.stream().map(p -> p.getName() + '=' + p.getValue())
                        .collect(Collectors.joining()) +
                        secretKey));
    }

    private void setApplication(RequestBuilder builder) {
        builder.addParameter("application_id", applicationId);
        builder.addParameter("application_key", applicationKey);
    }
}
