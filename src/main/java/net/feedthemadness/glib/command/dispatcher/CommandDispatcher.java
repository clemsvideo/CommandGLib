package net.feedthemadness.glib.command.dispatcher;

import java.util.Arrays;

import net.feedthemadness.glib.command.Command;

public class CommandDispatcher implements ICommandDispatcher {
	
	private Command[] commands = new Command[0];
	
	public CommandDispatcher() {
	}
	
	public CommandDispatcher addCommand(Command command) {
		Command[] commands = Arrays.copyOf(this.commands, this.commands.length + 1);
		commands[commands.length - 1] = command;
		
		this.commands = commands;
		return this;
	}
	
	public void dispatch(String parsableCommand, ICommandDispatcher dispatcher) {
		CommandContext context = new CommandContext(parsableCommand);
		
		for (int i = 0; i < commands.length; i++) {
			Command command = commands[i];
			
			command.dispatch(dispatcher, context, command);
		}
	}
	
	@Override
	public CommandDispatcher getCommandDispatcher() {
		return this;
	}
	
}
