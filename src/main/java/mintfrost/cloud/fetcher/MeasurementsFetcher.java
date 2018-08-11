package mintfrost.cloud.fetcher;

import java.util.Map;

public interface MeasurementsFetcher {
    Map<String, Map<String, Object>> getMeasurements();
}
