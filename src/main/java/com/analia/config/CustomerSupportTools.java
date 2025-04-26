package com.analia.config;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CustomerSupportTools {

    @ConfigProperty(name = "mcp.api.key", defaultValue = "")
    String mcpApiKey;



}