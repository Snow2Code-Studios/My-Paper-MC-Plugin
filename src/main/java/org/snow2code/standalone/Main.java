package org.snow2code.standalone;

import javax.swing.*;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
//        if (System.console() == null && !isHeadless()) {
            String msg = "You shouldn't run this standalone! " +
                    "copy or move the plugin into the servers 'plugins' directory, " +
                    "then:" +
                    "\n\n" +
                    "If the server is online:" +
                    "\n" +
                    " * Send a message to every user online that the server is going to restart, then restart the server after 5 or so seconds" +
                    "\n\n" +
                    "If the server is offline:" +
                    "\n" +
                    " * Start the server\n\n\nReport any issues with the plugin to the github repo\n\nhttps://github.com/Snow2Code-Studios/My-Paper-MC-Plugin";
            JOptionPane.showMessageDialog(null, msg, "snow2code_plugin", 0);
//        }
    }

    public static boolean isHeadless() {
        try {
            Class<?> graphicsEnvironment = Class.forName("java.awt.GraphicsEnvironment");
            Method isHeadless = graphicsEnvironment.getDeclaredMethod("isHeadless", new Class[0]);
            return ((Boolean)isHeadless.invoke(null, new Object[0])).booleanValue();
        } catch (Exception exception) {
            return true;
        }
    }
}
