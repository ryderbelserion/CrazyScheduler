package com.ryderbelserion.crazyscheduler.paper;

import com.ryderbelserion.fusion.paper.FusionPaper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public final class CrazyScheduler extends JavaPlugin {

    private FusionPaper fusion;

    @Override
    public void onEnable() {
        this.fusion = new FusionPaper(this);
        this.fusion.init();
    }

    @Override
    public void onDisable() {

    }

    public @NonNull FusionPaper getFusion() {
        return this.fusion;
    }
}