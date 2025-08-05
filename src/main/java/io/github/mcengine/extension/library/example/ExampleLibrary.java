package io.github.mcengine.extension.library.example;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.extension.logger.MCEngineExtensionLogger;
import io.github.mcengine.api.artificialintelligence.extension.addon.IMCEngineArtificialIntelligenceAddOn;

import io.github.mcengine.extension.library.example.LibraryCommand;
import io.github.mcengine.extension.library.example.LibraryListener;
import io.github.mcengine.extension.library.example.LibraryTabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Main class for the ExampleLibrary.
 * <p>
 * Registers the /ailibraryexample command and event listeners.
 */
public class ExampleLibrary implements IMCEngineArtificialIntelligenceAddOn {

    /**
     * Initializes the ExampleLibrary.
     * Called automatically by the MCEngine core plugin.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onLoad(Plugin plugin) {
        MCEngineExtensionLogger logger = new MCEngineExtensionLogger(plugin, "Library", "ArtificialIntelligenceExampleLibrary");

        try {
            // Register event listener
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(new LibraryListener(plugin), plugin);

            // Reflectively access Bukkit's CommandMap
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Define the /ailibraryexample command
            Command aiLibraryExampleCommand = new Command("ailibraryexample") {

                /**
                 * Handles logic for /ailibraryexample command.
                 */
                private final LibraryCommand handler = new LibraryCommand(); // Handles command execution.

                /**
                 * Provides tab-completion for /ailibraryexample.
                 */
                private final LibraryTabCompleter completer = new LibraryTabCompleter(); // Handles tab-completion.

                /**
                 * Executes the /ailibraryexample command.
                 *
                 * @param sender The command sender.
                 * @param label  The command label.
                 * @param args   The command arguments.
                 * @return true if successful.
                 */
                @Override
                public boolean execute(CommandSender sender, String label, String[] args) {
                    return handler.onCommand(sender, this, label, args);
                }

                /**
                 * Handles tab-completion for the /ailibraryexample command.
                 *
                 * @param sender The command sender.
                 * @param alias  The alias used.
                 * @param args   The current arguments.
                 * @return A list of possible completions.
                 */
                @Override
                public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                    return completer.onTabComplete(sender, this, alias, args);
                }
            };

            aiLibraryExampleCommand.setDescription("AI Library example command.");
            aiLibraryExampleCommand.setUsage("/ailibraryexample");

            // Dynamically register the /ailibraryexample command
            commandMap.register(plugin.getName().toLowerCase(), aiLibraryExampleCommand);

            logger.info("Enabled successfully.");
        } catch (Exception e) {
            logger.warning("Failed to initialize ExampleLibrary: " + e.getMessage());
            e.printStackTrace();
        }

        // Check for updates
        MCEngineCoreApi.checkUpdate(plugin, logger.getLogger(),
            "github", "MCEngine-Extension", "artificialintelligence-library-example",
            plugin.getConfig().getString("github.token", "null"));
    }

    @Override
    public void onDisload(Plugin plugin) {
        // No specific unload logic
    }

    @Override
    public void setId(String id) {
        MCEngineCoreApi.setId("artificialintelligence-library-example");
    }
}
