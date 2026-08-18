package zazerutus.java.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestVarConfig {

    private static final Properties PROPERTIES = loadProperties();

    private TestVarConfig() {
    }

    public static String baseUrl() {
        return getValue("baseUrl", "BASE_URL");
    }

    public static String browser() {
        return getValue("browser", "BROWSER");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                getValue("headless", "HEADLESS")
        );
    }

    public static int timeout() {
        return Integer.parseInt(
                getValue("timeout", "TIMEOUT")
        );
    }

    public static String screenSize() {
        return getValue("screenSize", "SCREEN_SIZE");
    }

    public static String getEmail() {
        return getValue("email", "TEST_EMAIL");
    }

    public static String getPassword() {
        return getValue("password", "TEST_PASSWORD");
    }

    private static String getValue(
            String propertyName,
            String environmentVariable
    ) {
        // 1. System property (-D...)
        String value = System.getProperty(propertyName);

        if (isNotBlank(value)) {
            return value;
        }

        // 2. Properties file
        value = PROPERTIES.getProperty(propertyName);

        if (isNotBlank(value)) {
            return value;
        }

        // 3. Environment variable
        value = System.getenv(environmentVariable);

        if (isNotBlank(value)) {
            return value;
        }

        throw new IllegalStateException(
                "Не задан обязательный параметр '" + propertyName +
                        "'. Источники: -D" + propertyName +
                        ", test.properties, " + environmentVariable
        );
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = TestVarConfig.class
                .getClassLoader()
                .getResourceAsStream("test.properties")) {

            if (inputStream != null) {
                properties.load(inputStream);
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Не удалось загрузить test.properties",
                    e
            );
        }

        return properties;
    }
}