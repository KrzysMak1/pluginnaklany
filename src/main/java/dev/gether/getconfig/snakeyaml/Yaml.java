package dev.gether.getconfig.snakeyaml;

import java.util.Map;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Yaml {
    public Map<String, Object> load(String input) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.loadFromString(input);
        } catch (InvalidConfigurationException e) {
            throw new IllegalArgumentException("Unable to parse YAML", e);
        }
        return configuration.getValues(false);
    }
}
