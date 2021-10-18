package live.sh0ck.paperex.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor {
  
  protected final String name;
  protected final List<String> aliases;
  protected final JavaPlugin plugin;
  
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
  
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
  
  protected abstract boolean execute(CommandSender sender, Command command, String[] args);
}
