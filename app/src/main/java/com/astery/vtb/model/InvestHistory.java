package com.astery.vtb.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.astery.vtb.ui.adapters.Presentable;

import org.jetbrains.annotations.NotNull;

@Entity
public class InvestHistory {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int actionId;
    private String actionName;
    private boolean isSell;
    private int count;
    private float price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public InvestHistory(){}

    @Ignore
    public InvestHistory(int actionId, String actionName, boolean isSell, int count, float price, int iteration) {
        this.actionId = actionId;
        this.isSell = isSell;
        this.count = count;
        this.price = price;
        this.actionName = actionName;
        this.iteration = iteration;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Override
    public String toString() {
        return "InvestHistory{" +
                "id=" + id +
                ", actionId=" + actionId +
                ", isSell=" + isSell +
                ", count=" + count +
                ", price=" + price +
                ", iteration=" + iteration +
                '}';
    }
}
