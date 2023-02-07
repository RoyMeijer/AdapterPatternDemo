package roy.adapterpatterndemo;

public class MetricAdapterImpl implements MetricAdapter {
    private final WeatherReport weatherReport;

    public MetricAdapterImpl(WeatherReport weatherReport) {
        this.weatherReport = weatherReport;
    }

    @Override
    public String getTemprature() {
        return celsiusToFahrenheit(weatherReport.temperature());
    }

    private String celsiusToFahrenheit(String celsius) {
        char pre = celsius.charAt(0);
        int trimmedTemp = Integer.parseInt(celsius.replace(" °C", "").substring(1));
        int fahrenheit = trimmedTemp * 9 / 5 + 32;
        return pre + fahrenheit + " °F";
    }

    private String fahrenheitToCelsius(String fahrenheit) {
        char pre = fahrenheit.charAt(0);
        int trimmedTemp = Integer.parseInt(fahrenheit.replace(" °F", "").substring(1));
        int celsius = (trimmedTemp - 32) * 5 / 9;
        return pre + celsius + " °C";
    }
}
