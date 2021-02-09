package net.feedthemadness.glib.command;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.feedthemadness.glib.command.dispatcher.CommandContext;
import net.feedthemadness.glib.command.executor.CommandExecutor;
import net.feedthemadness.glib.command.executor.CommandListener;
import net.feedthemadness.glib.command.executor.CommandUsageListener;
import net.feedthemadness.glib.command.executor.ExecutorReference;
import net.feedthemadness.glib.command.executor.ICommandExecutor;

public abstract class ACommandElement {
	
	protected ACommandElement[] subElements = new ACommandElement[0];
	
	protected CommandExecutor[] usageExecutors = new CommandExecutor[0];
	
	protected CommandExecutor[] commandExecutors = new CommandExecutor[0];
	
	public ACommandElement addSubElement(ACommandElement subElement) {
		ACommandElement[] subCommands = Arrays.copyOf(this.subElements, this.subElements.length + 1);
		subCommands[subCommands.length - 1] = subElement;

		this.subElements = subCommands;
		return this;
	}
	
	public ACommandElement addExecutor(ICommandExecutor executor, String id) {
		Method[] methods = executor.getClass().getDeclaredMethods();
		CommandExecutor commandExecutor = new CommandExecutor(id);
		
		boolean noListenerMethod = true;
		
		for(int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			
			if(!method.isAnnotationPresent(CommandListener.class)) {
				continue;
			}
			
			CommandListener annotation = method.getAnnotationsByType(CommandListener.class)[0];
			
			if(!id.equals(annotation.value())) {
				continue;
			}
			
			if(noListenerMethod) noListenerMethod = false;
			
			commandExecutor.addExecutor(new ExecutorReference(executor, method, annotation.value()));
		}

		CommandExecutor[] commandExecutors = Arrays.copyOf(this.commandExecutors, this.commandExecutors.length + 1);
		commandExecutors[commandExecutors.length - 1] = commandExecutor;
		
		this.commandExecutors = commandExecutors;
		return this;
	}
	
	public ACommandElement addUsageExecutor(ICommandExecutor executor, String id) {
		Method[] methods = executor.getClass().getDeclaredMethods();
		CommandExecutor commandExecutor = new CommandExecutor(id);
		
		boolean noListenerMethod = true;
		
		for(int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			
			if(!method.isAnnotationPresent(CommandUsageListener.class)) {
				continue;
			}
			
			CommandUsageListener annotation = method.getAnnotationsByType(CommandUsageListener.class)[0];
			
			if(!id.equals(annotation.value())) {
				continue;
			}
			
			if(noListenerMethod) noListenerMethod = false;
			
			commandExecutor.addExecutor(new ExecutorReference(executor, method, annotation.value()));
		}
		
		if(noListenerMethod) {
			Main.getTerminal().warning("No listener method");
			//TODO proper error
		}

		CommandExecutor[] usageExecutors = Arrays.copyOf(this.commandExecutors, this.commandExecutors.length + 1);
		usageExecutors[usageExecutors.length - 1] = commandExecutor;
		
		this.usageExecutors = usageExecutors;
		return this;
	}
	
	protected void dispatch(CommandContext context, int depth) {
		
		for(int i = 0 ; i < commandExecutors.length ; i++) {
			CommandExecutor commandExecutor = commandExecutors[i];
			
			commandExecutor.dispatch(context, depth);
		}
		
		depth++;
		if(depth >= context.parsableArgsSize()) return;
		
		boolean noDispatch = subElements.length > 0;
		
		for(int i = 0 ; i < subElements.length ; i++) {
			ACommandElement subElement = subElements[i];
			
			if(!subElement.checkDispatch(context, depth)) {
				continue;
			}
			
			if(noDispatch) noDispatch = false;
			
			subElement.dispatch(context, depth);
		}
		
		if(noDispatch) {
			for(int i = 0 ; i < usageExecutors.length ; i++) {
				CommandExecutor commandExecutor = usageExecutors[i];
				
				commandExecutor.dispatch(context, depth);
			}
		}
	}
	
	public abstract boolean checkDispatch(CommandContext context, int depth);
	
	protected void usageDispatch() {
		
	}
	
}
