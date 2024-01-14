package ok.api;

import ok.api.client.MethodApiClient;
import ok.api.client.PathApiClient;
import ok.api.config.ApplicationSecrets;
import ok.api.config.ConfigFactory;
import ok.api.config.HttpClientConfig;

public class AbstractTest {
    protected final HttpClientConfig httpClientConfig = ConfigFactory.loadFromResources(HttpClientConfig.class, HttpClientConfig.CONFIGFILE);
    protected final PathApiClient pathClient = new PathApiClient(httpClientConfig);
    protected final MethodApiClient methodClient = new MethodApiClient(httpClientConfig);
    protected final ApplicationSecrets applicationSecrets = ConfigFactory.loadFromResources(ApplicationSecrets.class, ApplicationSecrets.CONFIGFILE);
}
