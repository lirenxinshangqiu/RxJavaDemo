package com.ls.rxjava;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        rxSubscribe();
        rxSchedule();
        setMemory();
        long l = Runtime.getRuntime().maxMemory();
        System.out.println("最大内存：" + l / (1024 * 1024) + "MB");
        StringDemo.equalsTest();
    }

    private static void setMemory() {
        Set<Person> set = new HashSet<>();
        Person p1 = new Person("a", 1);
        Person p2 = new Person("b", 2);
        Person p3 = new Person("c", 3);
        set.add(p1);
        set.add(p2);
        set.add(p3);
        System.out.println("set元素个数：" + set.size());
        p3.setAge(10);
        set.remove(p3);
        set.add(p3);
        System.out.println("set元素个数：" + set.size());
        for (Person p :
                set) {
            System.out.println(p.toString());
        }

    }

    /**
     * 简单调用rxjava
     */
    private static void rxSubscribe() {
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
    private static void rxSchedule() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            System.out.println("currentThread:" + Thread.currentThread().getName());
            emitter.onNext(1);
        }).subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
                .doOnNext(integer -> System.out.println("doOnNext:single:" + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .subscribe(integer -> System.out.println("doOnNext:io:" + Thread.currentThread().getName()));
    }
}
