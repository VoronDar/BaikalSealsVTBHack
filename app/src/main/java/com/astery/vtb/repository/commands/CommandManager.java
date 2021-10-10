package com.astery.vtb.repository.commands;

import android.util.Log;

import com.astery.copying.forms_adapter.utils.JobListener;
import com.astery.vtb.ui.utils.GetItemListener;

/** manager for commands those can get objects*/
public class CommandManager<T>{
    private final GetItemListener<T> innerListener;
    protected JobListener commandListener;
    protected Command.CommandTask afterTask;

    public CommandManager(Command.GetItemInCommand<T> clientListener) {
        innerListener = new GetItemListener<T>() {
            @Override
            public void getItem(T item) {
                clientListener.getItem(item);
                if (afterTask !=null)
                    afterTask.work(null);
                commandListener.done(true);
            }

            @Override
            public void error() {
                Log.i("load", "error");
                commandListener.done(false);
            }
        };
    }
    public Command getCommand(Command.CommandTask task){
        return new Command(listener -> {
            commandListener = listener;
            task.work(commandListener);
        });
    }
    public void setNextCommands(Command.CommandTask afterTask){
        this.afterTask = afterTask;
    }

    public GetItemListener<T> getInnerListener() {
        return innerListener;
    }
}