package com.ls.rxjava;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Main {

    public static void main(String[] args) {
        rxSubscribe();
        rxSchedule();
    }

    /**
     * 简单调用rxjava
     */
    private static void rxSubscribe(){
        //第一步初始化Observable
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onNext(4);
            emitter.onComplete();
            emitter.onNext(5);
            emitter.onNext(5);
        }).subscribe(new Observer<Integer>() {//第三步，订阅
            private int i;
            private Disposable disposable;

            //第二步，初始化observer
            @Override
            public void onSubscribe(Disposable d) {
                this.disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                System.out.println("onNext:" + integer);
                if (i == 3) {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    /**
     * 线程调度
     */
    private static void rxSchedule(){
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            System.out.println("currentThread:"+Thread.currentThread().getName());
            emitter.onNext(1);
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
                .doOnNext(integer -> System.out.println("doOnNext:single:"+Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .subscribe(integer -> System.out.println("doOnNext:io:"+Thread.currentThread().getName()));
    }
}
