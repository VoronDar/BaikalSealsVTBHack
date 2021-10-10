package com.astery.vtb.repository.commands;

import androidx.annotation.NonNull;

import com.astery.copying.forms_adapter.utils.JobListener;


/** items that allow to do job with data one after one and in the end - notify parent*/
public class Command {
    private Command next;
    private JobListener listener;
    private JobListener parentListener;
    private final CommandTask task;

    public Command(CommandTask task) {
        this.task = task;
    }

    public void work(){
        task.work(listener);
    }

    public Command getNext() {
        return next;
    }

    public void setNext(@NonNull Command next) {
        if (this.next == null) {
            this.next = next;
            this.next.setParentListener(parentListener);
        } else{
            this.next.setNext(next);
        }
    }

    public void setParentListener(JobListener parentListener) {
        this.parentListener = parentListener;

        this.listener = success -> {
            if (success) {
                if (next == null) {

                    if (parentListener != null)
                        parentListener.done(true);
                }
                else {
                    next.work();
                }
            }
            else if (parentListener != null)
                parentListener.done(false);
        };
        if (next != null)
            this.next.setParentListener(parentListener);
    }


    public interface CommandTask{
        void work(JobListener listener);
    }

    public interface GetItemInCommand<T>{
        public void getItem(T item);
    }

}
