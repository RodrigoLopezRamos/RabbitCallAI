package com.analia.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import io.quarkus.arc.DefaultBean;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ApplicationScoped
public class PlaywrightConfig {

    @ConfigProperty(name = "playwright.headless", defaultValue = "false")
    boolean headless;

    @ConfigProperty(name = "playwright.timeout", defaultValue = "30000")
    int timeout;

    private Playwright playwright;
    private Browser browser;

    @Produces
    @DefaultBean
    @ApplicationScoped
    public Playwright playwright() {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        return playwright;
    }

    @Produces
    @DefaultBean
    @ApplicationScoped
    public Browser browser() {
        if (browser == null) {
            browser = playwright().chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(headless)
                            .setTimeout(timeout)
                            .setSlowMo(50)
            );
        }
        return browser;
    }

    @PreDestroy
    void cleanup() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
