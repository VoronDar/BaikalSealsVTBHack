package com.astery.vtb.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters({LiveActionConverter.class})
public class ChoresHistory {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int actionId;
    private LiveAction liveAction;
    private boolean isSell;
    private int count;
    private int price;
    private int iteration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public LiveAction getLiveAction() {
        return liveAction;
    }

    public void setLiveAction(LiveAction liveAction) {
        this.liveAction = liveAction;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }
}

