package ok.api.config;

public record HttpClientConfig(String host, int port) {
    public static final String CONFIGFILE = "httpconfig.yaml";
}
