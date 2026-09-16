package simulationfeuforet;

public class ConfigurationInvalideException extends RuntimeException {

    public ConfigurationInvalideException(String message) {
        super(message);
    }

    public ConfigurationInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
}
