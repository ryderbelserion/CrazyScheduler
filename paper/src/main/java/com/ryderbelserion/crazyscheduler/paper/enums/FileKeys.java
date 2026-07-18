package com.ryderbelserion.crazyscheduler.paper.enums;

import com.ryderbelserion.crazyscheduler.paper.CrazyScheduler;
import com.ryderbelserion.fusion.core.api.enums.Level;
import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import com.ryderbelserion.fusion.files.FileManager;
import com.ryderbelserion.fusion.files.enums.FileAction;
import com.ryderbelserion.fusion.files.enums.FileType;
import com.ryderbelserion.fusion.files.interfaces.ICustomFile;
import com.ryderbelserion.fusion.files.types.configurate.JsonCustomFile;
import com.ryderbelserion.fusion.files.types.configurate.YamlCustomFile;
import com.ryderbelserion.fusion.paper.FusionPaper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NullMarked;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@NullMarked
public enum FileKeys {

    config("config.yml", FileType.YAML, List.of(
            FileAction.EXTRACT_FILE
    ));

    private final CrazyScheduler plugin = JavaPlugin.getPlugin(CrazyScheduler.class);

    private final FusionPaper fusion = this.plugin.getFusion();

    private final FileManager fileManager = this.fusion.getFileManager();

    private final Path dataPath = this.fusion.getDataPath();

    private final List<FileAction> actions;
    private final FileType fileType;
    private final Path path;

    FileKeys(final String fileName, final String folder, final FileType fileType, final List<FileAction> actions) {
        this.path = folder.isEmpty() ? this.dataPath.resolve(fileName) : this.dataPath.resolve(folder).resolve(fileName);
        this.fileType = fileType;
        this.actions = actions;
    }

    FileKeys(final String fileName, final FileType fileType, final List<FileAction> actions) {
        this(fileName, "", fileType, actions);
    }

    public final CommentedConfigurationNode getYamlConfiguration() {
        return getYamlCustomFile().getConfiguration();
    }

    public final YamlCustomFile getYamlCustomFile() {
        final Optional<YamlCustomFile> customFile = this.fileManager.getYamlFile(this.path);

        if (customFile.isEmpty()) {
            throw new FusionException("Could not find custom file for " + this.path);
        }

        return customFile.get();
    }

    public final BasicConfigurationNode getJsonConfiguration() {
        return getJsonCustomFile().getConfiguration();
    }

    public final JsonCustomFile getJsonCustomFile() {
        final Optional<JsonCustomFile> customFile = this.fileManager.getJsonFile(this.path);

        if (customFile.isEmpty()) {
            throw new FusionException("Could not find custom file for " + this.path);
        }

        return customFile.get();
    }

    public final ICustomFile<?, ?, ?> getCustomFile() {
        final Optional<ICustomFile<?, ?, ?>> customFile = this.fileManager.getFile(this.path);

        if (customFile.isEmpty()) {
            throw new FusionException("Could not find custom file for " + this.path);
        }

        return customFile.get();
    }

    public final boolean hasCustomFile() {
        return this.fileManager.hasFile(this.path);
    }

    public final void load() {
        if (this.actions.contains(FileAction.DELETE_FILE)) {
            try {
                Files.deleteIfExists(this.path);
            } catch (final IOException exception) {
                this.fusion.log(Level.ERROR, "Failed to delete: %s", exception, this.path);
            }
        }

        this.fileManager.addFile(this.path, this.fileType, action -> action.addActions(this.actions));
    }

    public final void reload() {
        if (this.actions.contains(FileAction.DELETE_FILE)) {
            try {
                Files.deleteIfExists(this.path);
            } catch (final IOException exception) {
                this.fusion.log(Level.ERROR, "Failed to delete: %s", exception, this.path);
            }
        }

        this.fileManager.addFile(this.path, this.fileType, action -> action.addActions(this.actions).addAction(FileAction.RELOAD_FILE));
    }

    public final void save() {
        this.fileManager.saveFile(this.path);
    }

    public final FileType getFileType() {
        return this.fileType;
    }

    public final Path getPath() {
        return this.path;
    }
}