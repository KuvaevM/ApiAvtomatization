package ok.api.client;

import lombok.SneakyThrows;
import ok.api.config.HttpClientConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractHttpClient {
    private final HttpClient client;
    private final HttpHost host;
    protected final String path;
    private String applicationId;
    private String applicationKey;
    private String applicationSecretKey;
    private String accessToken;
    private String sessionSecretKey;

    @SneakyThrows
    public AbstractHttpClient(HttpClientConfig config, String path) {
        this.client = HttpClients.custom()
                .setConnectionManager(new BasicHttpClientConnectionManager(RegistryBuilder.< ConnectionSocketFactory > create()
                        .register("https", new SSLConnectionSocketFactory(SSLContexts.custom()
                                .loadTrustMaterial(null, (cert, authType) -> true)
                                .build(), NoopHostnameVerifier.INSTANCE))
                        .register("http", new PlainConnectionSocketFactory())
                        .build()
                )).build();
        this.host = new HttpHost(config.host(), config.port(), "https");
        this.path = path;
    }

    public HttpResponse execute(HttpUriRequest req) throws IOException {
        return client.execute(host, req);
    }

    public abstract RequestBuilder buildRequest(String restMethod, String apiMethodGroup, String apiMethodName, Map<String, String> parameters);

    public void setApplication(@NotNull String applicationId, @NotNull String applicationKey, @NotNull String applicationSecretKey) {
        this.applicationId = applicationId;
        this.applicationKey = applicationKey;
        this.applicationSecretKey = applicationSecretKey;
    }

    public void setCredentials(@NotNull String accessToken, @NotNull String sessionSecretKey) {
        this.accessToken = accessToken;
        this.sessionSecretKey = sessionSecretKey;
    }

    public void signRequest(@NotNull RequestBuilder builder) {
        String secretKey = sessionSecretKey;
        var params = builder.getParameters();
        if (secretKey == null) {
            secretKey = DigestUtils.md5Hex(accessToken + applicationSecretKey);
        }
        params.sort(Comparator.comparing(NameValuePair::getName));
        builder.addParameter("sig", DigestUtils.md5Hex(params.stream().map(p -> p.getName() + '=' + p.getValue()).collect(Collectors.joining()) + secretKey));
    }

    public void addRequestAccessToken(@NotNull RequestBuilder builder)  {
        builder.addParameter("access_token", accessToken);
    }
    public void addRequestApplication(@NotNull RequestBuilder builder) {
        builder.addParameter("application_id", applicationId);
        builder.addParameter("application_key", applicationKey);
    }
}
