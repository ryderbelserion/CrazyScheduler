package com.ryderbelserion.gradle.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GradleTemplate implements DedicatedServerModInitializer {

    private final Logger logger;

    public GradleTemplate() {
        this.logger = LoggerFactory.getLogger("GradleTemplate");
    }

    @Override
    public void onInitializeServer() {
        this.logger.info("Initializing GradleTemplate...");
    }
}