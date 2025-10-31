package org.snow2code.util;

import java.util.logging.Logger;

//
import static org.snow2code.plugin.Snow2Code_Plugin.*;

public class SemiLogger {

    private static Logger logger = Logger.getLogger("");

    /* ANSI Colors */

    private static void SemiLog(String type, String customPrefix, String message) {
        boolean hasAThing = false;
        String color = "";

        switch (type.toLowerCase()) {
            case "info":
                hasAThing = true;
                color = SemiColors.GREEN;
                break;
            case "warn":
                hasAThing = true;
                color = SemiColors.YELLOW;
                break;
            case "error":
                hasAThing = true;
                color = SemiColors.RED;
                break;
            case "debug":
                hasAThing = true;
                color = SemiColors.CYAN;
                break;
            case "custom":
                hasAThing = true;
                break;
            case "blank":
                hasAThing = false;
                break;
        }

        if ( hasAThing ) {
            if ( type.equals("CUSTOM") ) {
                logger.info("[snow2code - " + customPrefix + "] " + message);
            } else if ( type.equals("ERROR") ) {
                logger.info("[snow2code - " + customPrefix + "] " + message);
            } else if ( type.equals("WARN") ) {
                logger.info("[snow2code - " + customPrefix + "] " + message);
            } else if ( type.equals("DEBUG") ) {
                logger.info("[snow2code - " + customPrefix + "] " + message);
            }
        } else {
            if ( type.equals("BLANK") ) {
                logger.info(message);
            } else {

                if ( type.contains("TEMP") ) {
                    logger.info("[snow2code - TEMP] " + message);
                } else {
                    logger.info("[snow2code] " + message);
                }

            }
        }
    }

    public static void Log(String message) {
        SemiLog("LOG", "LOG", message);
    }

    public static void Info(String message) {
        SemiLog("INFO", "INFO", message);
    }

    public static void Warn(String message) {
        SemiLog("WARN", "WARN", message);
    }

    public static void Error(String message) {
        SemiLog("ERROR", "ERROR", message);
    }

    public static void Debug(String message) {
        SemiLog("DEBUG", "DEBUG", message);
    }

    public static void Custom(String prefix, String color, String message) {
        SemiLog("CUSTOM", color + prefix + SemiColors.RESET, message);
    }

    public static void Custom2(String message) {
        SemiLog("BLANK", "idk", message);
    }

    public static void LogTemp(String message) {
        SemiLog("LOGTEMP", "LOG", message);
    }
}
