package mintfrost.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@SpringBootApplication
@RestController
public class Application {
    private static final List<String> SENSOR_ENDPOINTS = Arrays.asList("currentOutdoor", "currentIndoor", "currentPressure");

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    @RequestMapping("/sensors")
    @ResponseBody
    public Map<String, Map<String, Object>> sensors() {
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

    @RequestMapping("/sensors/{sensorEndpoint}")
    @ResponseBody
    public Map<String, Map<String, Object>> sensor(@PathVariable(value = "sensorEndpoint") String sensorEndpoint) {
        Map<String, Map<String, Object>> returnMap = new LinkedHashMap<>();
        returnMap.put(sensorEndpoint, new SensorRestRequest(sensorEndpoint).call());
        return returnMap;
    }
}
