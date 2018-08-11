package mintfrost.cloud;

import mintfrost.cloud.fetcher.AllMeasurementsFetcherExecutorService;
import mintfrost.cloud.fetcher.MeasurementsFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    private MeasurementsFetcher allMeasurementsFetcherExecutorService(){
        return new AllMeasurementsFetcherExecutorService();
    }

    @Bean
    public SensorsController sensorsController() {
        return new SensorsController(allMeasurementsFetcherExecutorService());
    }
}
