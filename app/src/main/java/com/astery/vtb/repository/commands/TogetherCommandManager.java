package com.astery.vtb.repository.commands;

import com.astery.copying.forms_adapter.utils.JobListener;

import java.util.ArrayList;
import java.util.List;

/** manager for commands those can get objects (created for do several task together)*/
public class TogetherCommandManager{
    private final List<Command> commands;
    private final List<Boolean> done;
    private JobListener unionListener;

    public TogetherCommandManager() {
        commands = new ArrayList<>();
        done = new ArrayList<>();

    }
    public Command getCommand() {
        return new Command(listener -> {
            unionListener = listener;
            for (Command command: commands){
                command.work();
            }
            if (commands.size() == 0)
                listener.done(true);
        });
    }

    public void setCommand(Command.CommandTask task){
        setCommand(new Command(task));
    }

    public void setCommand(Command command){
        commands.add(command);
        done.add(false);

        commands.get(commands.size()-1).setParentListener(success -> {
            if (success){

                done.set(commands.indexOf(command), true);
                if (!done.contains(Boolean.FALSE))
                    unionListener.done(true);
            } else
                unionListener.done(false);
        });
    }
}