package com.analia;




import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.logging.Logger;

@QuarkusMain
public class AnaliaMain {
    private static final Logger LOG = Logger.getLogger(AnaliaMain.class.getName());

    public static void main(String[] args) {
        try {
            LOG.info("Starting Analia application...");
            Quarkus.run(args);
            LOG.info("Analia application started.");
        } catch (Throwable t) {
            LOG.severe("Failed to start Analia application: " + t.getMessage());
            t.printStackTrace();
            System.exit(1);
        }
    }
}
