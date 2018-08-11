package mintfrost.cloud;

import mintfrost.cloud.fetcher.MeasurementsFetcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SensorsController {
    private final MeasurementsFetcher allMeasurementsFetcher;

    SensorsController(MeasurementsFetcher allMeasurementsFetcher) {
        this.allMeasurementsFetcher = allMeasurementsFetcher;
    }

    @RequestMapping("/sensors")
    @ResponseBody
    public Map<String, Map<String, Object>> sensors() {
        return allMeasurementsFetcher.getMeasurements();
    }
}
