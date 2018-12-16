package run.racoon.commons.storage.configuration;

import java.util.List;

public interface Configuration {
    <T> T getByKey(String key);
    <T> List<T> getListByKey(String key);
}
