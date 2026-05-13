package ch.funproject.mealplanner.config;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * Configuration class to manage the lifecycle of the H2 web console server. It starts the server when the application context is refreshed and stops it when the context is closed.
 */
@Configuration
@Slf4j
public class H2ConsoleConfig {
    // H2 web console server instance
    private Server webServer;

    @Value("${meal-planner.h2.port}")
    private String h2ConsolePort;

    // ContextRefreshedEvent fires after Spring has fully started
    @EventListener(ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        log.info("Starting h2 console at port {}", h2ConsolePort);
        this.webServer = Server.createWebServer("-webPort", h2ConsolePort).start();
    }


    // ContextClosedEvent is triggered when the application shuts down.
    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("Stopping h2 console at port {}", h2ConsolePort);
        this.webServer.stop();
    }
}
