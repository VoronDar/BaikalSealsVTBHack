package com.astery.vtb.local_storage.rx_utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxTaskManager {

    public static void doTask(RxExecutable executable){
        Observable.create(e -> {
            ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
            Future<Boolean> future = ex.schedule(() -> {
                executable.doSomething();
                e.onComplete();
                return null;
            }, 0, TimeUnit.SECONDS);
            e.setCancellable(() -> future.cancel(true));
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onComplete() {
                        executable.onCompleteListener();
                    }

                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull Object o) {

                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        executable.onErrorListener();
                    }
                });
    }
}
