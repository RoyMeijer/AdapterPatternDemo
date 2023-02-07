package roy.adapterpatterndemo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherReport(String temperature, String wind, String description, Forecast[] forecast) {
    @Override
    public String toString() {
        return """
                --------------------------------
                |   Temperature:    %s         \s
                |   Wind:           %s         \s
                |   Description:    %s         \s
                --------------------------------
                """.formatted(temperature, wind, description);
    }
}