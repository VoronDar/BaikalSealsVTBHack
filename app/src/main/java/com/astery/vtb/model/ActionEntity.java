package com.astery.vtb.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.vtb.ui.adapters.Presentable;

import org.jetbrains.annotations.NotNull;

@Entity
@TypeConverters({ActionTypeConverter.class})
public class ActionEntity implements Presentable {
    @NonNull
    @PrimaryKey
    private int id;
    private ActionType actionType;
    private String name;
    private String description;
    private int startPrice;
    private float minMultiplier;
    private float maxMultiplier;

    private float actualPrice;
    private float oldPrice;
    private int purchasedCount;

    @NotNull
    @Override
    public String getText() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public float getMinMultiplier() {
        return minMultiplier;
    }

    public void setMinMultiplier(float minMultiplier) {
        this.minMultiplier = minMultiplier;
    }

    public float getMaxMultiplier() {
        return maxMultiplier;
    }
    public void setMaxMultiplier(float maxMultiplier) {
        this.maxMultiplier = maxMultiplier;
    }
    public float getActualPrice() {
        return actualPrice;
    }
    public void setActualPrice(float actualPrice) {
        this.actualPrice = actualPrice;
    }
    public float getOldPrice() {
        if (oldPrice == 0) oldPrice = actualPrice;
        return oldPrice;
    }
    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }
    public int getPurchasedCount() {
        return purchasedCount;
    }
    public void setPurchasedCount(int purchasedCount) {
        this.purchasedCount = purchasedCount;
    }

    @Ignore
    public ActionEntity() {
    }

    @Ignore
    public ActionEntity(int id, ActionType actionType, String name, String description, int startPrice, float minMultiplier, float maxMultiplier) {
        this.id = id;
        this.actionType = actionType;
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.minMultiplier = minMultiplier;
        this.maxMultiplier = maxMultiplier;

        this.actualPrice = startPrice;
        this.oldPrice = startPrice;
    }

    public ActionEntity(int id, ActionType actionType, String name, String description, int startPrice, float minMultiplier, float maxMultiplier, float actualPrice, float oldPrice, int purchasedCount) {
        this.id = id;
        this.actionType = actionType;
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.minMultiplier = minMultiplier;
        this.maxMultiplier = maxMultiplier;
        this.actualPrice = actualPrice;
        this.oldPrice = oldPrice;
        this.purchasedCount = purchasedCount;


    }

    @Override
    public String toString() {
        return "ActionEntity{" +
                "id=" + id +
                ", actionType=" + actionType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startPrice=" + startPrice +
                ", minMultiplier=" + minMultiplier +
                ", maxMultiplier=" + maxMultiplier +
                ", actualPrice=" + actualPrice +
                ", oldPrice=" + oldPrice +
                ", purchasedCount=" + purchasedCount +
                '}';
    }
}

