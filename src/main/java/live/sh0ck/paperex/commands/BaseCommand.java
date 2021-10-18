package live.sh0ck.paperex.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Class representing a command.<br><br>
 *
 * Used for implementing logic for a {@link CommandExecutor} with permission checks included out-of-the-box.
 * Inheritors can use a marker interface such as {@link IPlayerCommand} or {@link IEntityCommand} to mark this command
 * as only runnable by the specified {@link CommandSender}.<br>
 * If tab completion is required, you can implement {@link TabCompleter} and {@link BaseCommand} will register your command
 * for tab completion.
 *
 * @author sh0ckR6
 * @since latest
 */
public abstract class BaseCommand implements CommandExecutor {
  
  /**
   * The name of the command
   *
   * @since latest
   */
  protected final String name;
  /**
   * An unmodifiable list of all aliases of the command.
   *
   * @since latest
   */
  protected final List<String> aliases;
  /**
   * Reference to the {@link JavaPlugin} this command will be registered to
   *
   * @since latest
   */
  protected final JavaPlugin plugin;
  
  /**
   * Constructor
   *
   * @param name The name of the command
   * @param aliases A list of all aliases of the command
   * @param plugin Reference to the {@link JavaPlugin} this command will be registered to
   * @throws MissingCommandException If the command name passed could not be found in the plugin.yml
   * @author sh0ckR6
   * @since latest
   */
  public BaseCommand(String name, List<String> aliases, JavaPlugin plugin) throws MissingCommandException {
    this.name = name;
    this.aliases = Collections.unmodifiableList(aliases);
    this.plugin = plugin;
  
    if (plugin.getCommand(name) == null) {
      throw new MissingCommandException(name);
    }
    plugin.getCommand(name).setExecutor(this);
    if (this instanceof TabCompleter tabCompleter) {
      plugin.getCommand(name).setTabCompleter(tabCompleter);
    }
  }
  
  /**
   * Run when a {@link CommandSender} attempts to execute this command.
   *
   * @param sender The sender that attempted to send this command
   * @param command The internal command registered under this command's name
   * @param label The name or alias used to run this command
   * @param args A list of arguments passed to this command by the sender
   * @return True if the command successfully ran.
   * @author sh0ckR6
   * @since latest
   */
  @Override
  public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (this instanceof IPlayerCommand && !(sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "You do not have permission to send this command!");
      return true;
    }
    if (this instanceof IEntityCommand && !(sender instanceof Entity)) {
      sender.sendMessage(ChatColor.RED + "You do not have permission to send this command!");
      return true;
    }
    if (this instanceof IConsoleCommand && !(sender instanceof ConsoleCommandSender)) {
      sender.sendMessage(ChatColor.RED + "You do not have permission to send this command!");
      return true;
    }
    
    return execute(sender, command, args);
  }
  
  /**
   * Run when all checks have passed in {@link #onCommand}. Inheritors should implement command execution logic here.
   *
   * @param sender The sender that sent this command
   * @param command The internal command registered under this command's name
   * @param args A list of arguments passed to this command by the sender
   * @return True if the command successfully ran
   * @author sh0ckR6
   * @since latest
   */
  protected abstract boolean execute(CommandSender sender, Command command, String[] args);
}
