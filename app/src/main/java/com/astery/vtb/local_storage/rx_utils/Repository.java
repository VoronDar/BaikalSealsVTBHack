package com.astery.vtb.local_storage.rx_utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.astery.copying.forms_adapter.utils.JobListener;
import com.astery.vtb.local_storage.AppDatabase;
import com.astery.vtb.model.ActionEntity;
import com.astery.vtb.model.ActionType;
import com.astery.vtb.model.ChoresHistory;
import com.astery.vtb.model.GameItemHolder;
import com.astery.vtb.model.InvestHistory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    public final AppDatabase database;
    public Repository(AppDatabase database) {
        this.database = database;
    }

    public <T> void loadValue(T item, @Nullable JobListener loadable){
        RxTaskManager.doTask(new RxExecutable() {
            @Override public void doSomething() {
                if (item.getClass().getSimpleName().equals("ChoresHistory"))
                    database.actionDao().addItem((ChoresHistory)item);
                if (item.getClass().getSimpleName().equals("InvestHistory"))
                    database.actionDao().addItem((InvestHistory)item);
                if (item.getClass().getSimpleName().equals("ActionEntity"))
                    database.actionDao().addItem((ActionEntity)item);
            }
            @Override
            public void onCompleteListener() { if (loadable != null) loadable.done(true); }

            @Override
            public void onErrorListener() { if (loadable != null) loadable.done(false); }
        });
    }

    public void getActions(DisposableSingleObserver<List<ActionEntity>> observer, int difficulty){
        //Log.i("main get actions", GameItemHolder.Companion.actionTypes(difficulty).toString());
        //subscribe(database.actionDao().getActionsWithType(GameItemHolder.Companion.actionTypes(difficulty)), observer);
        subscribe(database.actionDao().getActions(), observer);
    }
    public void getAllActions(DisposableSingleObserver<List<ActionEntity>> observer){
        subscribe(database.actionDao().getActions(), observer);
    }


    public void getActions(DisposableSingleObserver<List<ActionEntity>> observer, ActionType type){
        subscribe(database.actionDao().getActionsWithType(null), observer);
        //TODO("not implemented")
    }

    public void getSelectedActions(DisposableSingleObserver<List<ActionEntity>> observer) {
        subscribe(database.actionDao().getSelectedActions(), observer);

    }


    public void getInvestHistory(DisposableSingleObserver<List<InvestHistory>> observer){
        subscribe(database.actionDao().getInvestHistory(), observer);
    }

    public void getChoresHistory(DisposableSingleObserver<List<ChoresHistory>> observer){

        subscribe(database.actionDao().getChoresHistory(), observer);
    }

    private <T> void subscribe(Single<List<T>> item, DisposableSingleObserver<List<T>> observer){
        item
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void deleteItem(@NotNull InvestHistory i) {
        RxTaskManager.doTask(new RxExecutable() {
            @Override public void doSomething() {
                database.actionDao().deleteItem(i);
            }
            @Override
            public void onCompleteListener() { }

            @Override
            public void onErrorListener() {}
        });
    }
    public void deleteItem(@NotNull ChoresHistory i) {
        RxTaskManager.doTask(new RxExecutable() {
            @Override public void doSomething() {
                database.actionDao().deleteItem(i);
            }
            @Override
            public void onCompleteListener() { }

            @Override
            public void onErrorListener() {}
        });
    }
}
