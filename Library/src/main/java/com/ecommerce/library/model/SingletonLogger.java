package com.ecommerce.library.model;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SingletonLogger {
    private static final Logger LOGGER = Logger.getLogger(SingletonLogger.class.getName());
    private static volatile SingletonLogger instance;

    private SingletonLogger() {
        try {
            LOGGER.setLevel(Level.INFO);

            FileHandler fileHandler = new FileHandler("proxy.txt", 10 * 1024, 1);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error configuring logger", e);
        }
    }

    public static SingletonLogger getInstance() {
        if (instance == null) {
            synchronized (SingletonLogger.class) {
                if (instance == null) {
                    instance = new SingletonLogger();
                }
            }
        }
        return instance;
    }

    public void logInfo(String message) {
        LOGGER.info(message);
    }

    public void logWarning(String message) {
        LOGGER.warning(message);
    }
}

