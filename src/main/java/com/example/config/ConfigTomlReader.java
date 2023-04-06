package com.example.config;

import java.io.File;
import java.io.IOException;
import com.moandjiezana.toml.Toml;

public class ConfigTomlReader {
    public static String getConfigProperty(String propertyName) throws IOException {
        File configFile = new File("./config.toml");
        Toml toml = new Toml().read(configFile);
        return toml.getString(propertyName);
    }
}
