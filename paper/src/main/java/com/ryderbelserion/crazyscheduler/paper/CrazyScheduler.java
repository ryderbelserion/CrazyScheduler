package com.ryderbelserion.crazyscheduler.paper;

import com.ryderbelserion.crazyscheduler.paper.enums.FileKeys;
import com.ryderbelserion.fusion.paper.FusionPaper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public final class CrazyScheduler extends JavaPlugin {

    private FusionPaper fusion;

    @Override
    public void onEnable() {
        this.fusion = new FusionPaper(this);
        this.fusion.init();

        for (final FileKeys key : FileKeys.values()) {
            key.load();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public @NonNull FusionPaper getFusion() {
        return this.fusion;
    }
}