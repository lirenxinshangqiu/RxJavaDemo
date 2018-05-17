# RxJavaDemo
rxjava学习demo

第一步：创建观察者;
第二步：创建被观察者；
第三步：订阅事件
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


Observer 方法onSubscribe中参数 Disposable用于取消订阅

线程调度

subScribeOn，制定Observable的线程
observeOn制定下游Observer回调线程

Schedulers.io()代表io操作线程，通常用于网络、读写文件等io密集型的操作
Schedulers.computation()代表CPU计算密集型的操作，例如需要进行大量计算的操作
Schedulers.newThread()代表一个常规的新线程
AndroidSchedulers.mainThread()代表Android的主线程

RxJava的操作符

