package mintfrost.cloud.fetcher;

import mintfrost.cloud.SensorResponse;
import mintfrost.cloud.SensorRestRequest;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static mintfrost.cloud.ApplicationConfiguration.SENSOR_ENDPOINTS;

public class AllMeasurementsFetcherParallelStream implements MeasurementsFetcher {

    private final String sensorUrl;

    public AllMeasurementsFetcherParallelStream(String sensorUrl) {

        this.sensorUrl = sensorUrl;
    }

    @Override
    public Map<String, Map<String, Object>> getMeasurements() {
        return SENSOR_ENDPOINTS.stream()
                .parallel()
                .map(sensorName -> new SensorRestRequest(sensorUrl, sensorName))
                .map(SensorRestRequest::call)
                .collect(toMap(SensorResponse::getSensorName, SensorResponse::getResponseMap));
    }
}
