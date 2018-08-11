package mintfrost.cloud;

import mintfrost.cloud.fetcher.AllMeasurementsFetcherExecutorService;
import mintfrost.cloud.fetcher.AllMeasurementsFetcherParallelStream;
import mintfrost.cloud.fetcher.MeasurementsFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ApplicationConfiguration {

    public static final List<String> SENSOR_ENDPOINTS = Arrays.asList("currentOutdoor", "currentIndoor", "currentPressure");

    @Bean
    MeasurementsFetcher allMeasurementsFetcherExecutorService() {
        return new AllMeasurementsFetcherExecutorService();
    }

    @Bean
    MeasurementsFetcher allMeasurementsFetcherParallelStream() {
        return new AllMeasurementsFetcherParallelStream();
    }

    @Bean
    public SensorsController sensorsController() {
        return new SensorsController(allMeasurementsFetcherParallelStream());
    }
}
