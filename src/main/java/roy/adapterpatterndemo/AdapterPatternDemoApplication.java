package roy.adapterpatterndemo;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AdapterPatternDemoApplication {

    private static final Logger log = LoggerFactory.getLogger(AdapterPatternDemoApplication.class);
    public static final String HTTP_WEATHER_API_UTRECHT = "http://goweather.herokuapp.com/weather/Utrecht";

    public static void main(String[] args) {
        SpringApplication.run(AdapterPatternDemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            WeatherReport weatherReport = restTemplate.getForObject(
                    HTTP_WEATHER_API_UTRECHT, WeatherReport.class);
            Scanner scanner = new Scanner(System.in);
            MetricAdapter metricAdapter = new MetricAdapterImpl(weatherReport);
            while (true) {
                System.out.print("Imperial(I)/Metric(M)");
                String system = scanner.next();
                if (system.equals("I")) {
                    WeatherReport imperialWeatherReport = new WeatherReport(
                            metricAdapter.getTemprature(),
                            weatherReport.wind(),
                            weatherReport.description(),
                            weatherReport.forecast()
                    );
                    log.info(imperialWeatherReport.toString());
                }
                else if (system.equals("M")) {
                    log.info(weatherReport.toString());
                }
                scanner.next();
                System.out.print("No choice made, try again");
            }
        };
    }
}
