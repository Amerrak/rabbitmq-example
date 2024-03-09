package usy.aibhub.amqp.examples.cli;

import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.help.Help;

@Cli(name = "amqp-client",
  description = "AMQP Client Cli Sample",
  defaultCommand = Help.class,
  commands = {SenderCommand.class, ReceiverCommand.class, Help.class})
public class SampleCli {

  public static void main(String[] args) {
    com.github.rvesse.airline.Cli<Runnable> cli = new com.github.rvesse.airline.Cli<>(SampleCli.class);
    Runnable cmd = cli.parse(args);
    cmd.run();
  }
}
