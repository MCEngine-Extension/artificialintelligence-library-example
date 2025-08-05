package io.github.mcengine.extension.example.library;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Handles /ailibraryexample command logic.
 */
public class LibraryCommand implements CommandExecutor {

    /**
     * Executes the /ailibraryexample command.
     *
     * @param sender  The source of the command.
     * @param command The command that was executed.
     * @param label   The alias used.
     * @param args    The command arguments.
     * @return true if the command was handled successfully.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§aLibrary command executed!");
        return true;
    }
}
