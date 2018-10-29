package mintfrost.cloud.fetcher;

import mintfrost.cloud.ApplicationConfiguration;
import mintfrost.cloud.SensorResponse;
import mintfrost.cloud.SensorRestRequest;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class AllMeasurementsFetcherExecutorService implements MeasurementsFetcher {


    private final String sensorUrl;

    public AllMeasurementsFetcherExecutorService(String sensorUrl) {

        this.sensorUrl = sensorUrl;
    }

    @Override
    public Map<String, Map<String, Object>> getMeasurements() {
        final Map<String, Future<SensorResponse>> futureMap = new LinkedHashMap<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(ApplicationConfiguration.SENSOR_ENDPOINTS.size());
        for (String sensorEndpoint : ApplicationConfiguration.SENSOR_ENDPOINTS) {
            futureMap.put(sensorEndpoint, executorService.submit(new SensorRestRequest(sensorUrl, sensorEndpoint)));
        }

        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Map<String, Map<String, Object>> returnMap = new LinkedHashMap<>();
        for (Map.Entry<String, Future<SensorResponse>> stringFutureEntry : futureMap.entrySet()) {
            try {
                Future<SensorResponse> requestResult = stringFutureEntry.getValue();
                if (requestResult.isDone()) {
                    returnMap.put(stringFutureEntry.getKey(), requestResult.get().getResponseMap());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return returnMap;
    }
}
