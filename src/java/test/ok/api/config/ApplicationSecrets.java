package ok.api.config;

public record ApplicationSecrets(String applicationId, String applicationKey, String applicationSecretKey,
                                 String accessToken, String sessionSecret) {
    public static final String CONFIGFILE = "applicationsecret.yaml";
}
