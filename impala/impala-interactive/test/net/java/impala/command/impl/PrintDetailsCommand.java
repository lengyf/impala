package net.java.impala.command.impl;

import java.util.Map;

import net.java.impala.command.Command;
import net.java.impala.command.CommandInfo;
import net.java.impala.command.CommandPropertyValue;
import net.java.impala.command.CommandSpec;
import net.java.impala.command.CommandState;

public class PrintDetailsCommand implements Command {

	private CommandSpec commandSpec;

	public PrintDetailsCommand() {
		super();
		CommandSpec cspec = newCommandSpec();
		this.commandSpec = cspec;
	}

	protected CommandSpec newCommandSpec() {

		// name is shared
		CommandInfo ci1 = new CommandInfo("n", "name", "Name", "Please give your name", null, null, true, false, false);

		// date of birth is optional here
		CommandInfo ci2 = new CommandInfo("dob", "dateOfBirth", "Date of birth", "Please give your date of birth",
				null, null, false, true, false);

		// residence is supplied via global
		CommandInfo ci3 = new CommandInfo("r", "residence", "Residence", "Where do you live", null, null, false, true,
				false);

		// house name is supplied via default
		CommandInfo ci4 = new CommandInfo("r", "housename", "House name", "Name of your house?", "IvyD", null, false,
				true, false);

		CommandSpec cspec = new CommandSpec();
		cspec.add(ci1);
		cspec.add(ci2);
		cspec.add(ci3);
		cspec.add(ci4);
		return cspec;
	}

	public CommandSpec getCommandSpec() {
		return commandSpec;
	}

	public boolean execute(CommandState commandState) {
		Map<String, CommandPropertyValue> properties = commandState.getProperties();
		System.out.println();
		CommandPropertyValue dob = properties.get("dateOfBirth");

		System.out.println("Name " + properties.get("name").getValue() + ", dob " + dob != null ? "unknown" : dob
				.getValue());
		return true;
	}

}
