package live.sh0ck.paperex.commands;

/**
 * Exception representing a command that could not be found in the current {@link org.bukkit.plugin.java.JavaPlugin}'s
 * plugin.yml file.
 *
 * @author sh0ckR6
 * @since latest
 */
public class MissingCommandException extends Exception{
  public MissingCommandException() {
    super();
  }
  
  public MissingCommandException(String commandName) {
    super("Missing command " + commandName + ", maybe you forgot to register it in your plugin.yml?");
  }
  
  public MissingCommandException(String commandName, Throwable cause) {
    super("Missing command " + commandName + ", maybe you forgot to register it in your plugin.yml?", cause);
  }
  
  public MissingCommandException(Throwable cause) {
    super(cause);
  }
}
