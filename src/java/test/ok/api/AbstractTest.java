package ok.api;

import ok.api.client.MethodApiClient;
import ok.api.client.PathApiClient;
import ok.api.config.ApplicationSecrets;
import ok.api.config.ConfigFactory;
import ok.api.config.HttpClientConfig;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;

public class AbstractTest {
    protected static final HttpClientConfig httpClientConfig = ConfigFactory.loadFromResources(HttpClientConfig.class, HttpClientConfig.CONFIGFILE);
    protected static final PathApiClient pathClient = new PathApiClient(httpClientConfig);
    protected static final MethodApiClient methodClient = new MethodApiClient(httpClientConfig);
    protected static final ApplicationSecrets applicationSecrets = ConfigFactory.loadFromResources(ApplicationSecrets.class, ApplicationSecrets.CONFIGFILE);

    protected static List<Arguments> provideHttpClients() {
        return List.of(Arguments.of(pathClient), Arguments.of(methodClient));
    }
}
