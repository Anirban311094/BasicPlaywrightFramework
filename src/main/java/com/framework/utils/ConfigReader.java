package com.framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    public static String getProperty(String key) {
        try {
            if (prop == null) {
                prop = new Properties();
                FileInputStream ip = new FileInputStream("./src/test/resources/config.properties");
                prop.load(ip);
            }
        } catch (IOException e) {
            System.err.println("Could not read config.properties file!");
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
