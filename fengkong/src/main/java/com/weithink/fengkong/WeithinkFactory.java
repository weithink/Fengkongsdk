package com.weithink.fengkong;

import com.weithink.fengkong.logger.ILogger;
import com.weithink.fengkong.logger.Logger;

public class WeithinkFactory {
    private static ILogger logger = null;

    public static ILogger getLogger() {
        if (logger == null) {
            // Logger needs to be "static" to retain the configuration throughout the app
            logger = new Logger();
        }
        return logger;
    }
}
