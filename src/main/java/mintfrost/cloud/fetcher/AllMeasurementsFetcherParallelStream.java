package mintfrost.cloud.fetcher;

import mintfrost.cloud.SensorResponse;
import mintfrost.cloud.SensorRestRequest;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static mintfrost.cloud.ApplicationConfiguration.SENSOR_ENDPOINTS;

public class AllMeasurementsFetcherParallelStream implements MeasurementsFetcher {

    @Override
    public Map<String, Map<String, Object>> getMeasurements() {
        return SENSOR_ENDPOINTS.stream()
                .parallel()
                .map(SensorRestRequest::new)
                .map(SensorRestRequest::call)
                .collect(toMap(SensorResponse::getSensorName, SensorResponse::getResponseMap));
    }
}
