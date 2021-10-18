package live.sh0ck.paperex.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
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
    return execute(sender, command, args);
  }
  
  protected abstract boolean execute(CommandSender sender, Command command, String[] args);
}
