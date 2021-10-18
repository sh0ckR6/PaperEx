package live.sh0ck.paperex.commands;

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
