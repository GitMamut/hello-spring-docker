package mintfrost.cloud;

import mintfrost.cloud.fetcher.AllMeasurementsFetcherExecutorService;
import mintfrost.cloud.fetcher.AllMeasurementsFetcherParallelStream;
import mintfrost.cloud.fetcher.MeasurementsFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ApplicationConfiguration {

    public static final List<String> SENSOR_ENDPOINTS = Arrays.asList("currentOutdoor", "currentIndoor", "currentPressure");

    @Value("${sensorUrl}")
    private String sensorUrl;

    @Bean
    MeasurementsFetcher allMeasurementsFetcherExecutorService() {
        return new AllMeasurementsFetcherExecutorService(sensorUrl);
    }

    @Bean
    MeasurementsFetcher allMeasurementsFetcherParallelStream() {
        return new AllMeasurementsFetcherParallelStream(sensorUrl);
    }

    @Bean
    public SensorsController sensorsController() {
        return new SensorsController(allMeasurementsFetcherParallelStream());
    }
}
