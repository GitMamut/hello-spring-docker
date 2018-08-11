package mintfrost.cloud.fetcher;

import mintfrost.cloud.SensorRestRequest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class AllMeasurementsFetcherExecutorService implements MeasurementsFetcher {
    private static final List<String> SENSOR_ENDPOINTS = Arrays.asList("currentOutdoor", "currentIndoor", "currentPressure");


    @Override
    public Map<String, Map<String, Object>> getMeasurements() {
        final Map<String, Future<Map<String, Object>>> futureMap = new LinkedHashMap<>();
        final ExecutorService executorService = Executors.newFixedThreadPool(SENSOR_ENDPOINTS.size());
        for (String sensorEndpoint : SENSOR_ENDPOINTS) {
            futureMap.put(sensorEndpoint, executorService.submit(new SensorRestRequest(sensorEndpoint)));
        }

        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Map<String, Map<String, Object>> returnMap = new LinkedHashMap<>();
        for (Map.Entry<String, Future<Map<String, Object>>> stringFutureEntry : futureMap.entrySet()) {
            try {
                Future<Map<String, Object>> requestResult = stringFutureEntry.getValue();
                if (requestResult.isDone()) {
                    returnMap.put(stringFutureEntry.getKey(), requestResult.get());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return returnMap;
    }
}
