package com.ijidou.retrofitdemo;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/29.
 */

public class TestRxJava {

    static Observer<String> mObserver = new Observer<String>() {
        @Override
        public void onCompleted() {
            System.out.println("mObserver   onCompleted  Thread = " + Thread.currentThread().getName());
            System.out.println("mObserver   onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("mObserver   onError");
        }

        @Override
        public void onNext(String s) {
            System.out.println("mObserver   onCompleted  Thread = " + Thread.currentThread().getName());
            System.out.println("mObserver   onNext " + s);
        }
    };


    static Subscriber<String> mSubscriber = new Subscriber<String>() {

        @Override
        public void onCompleted() {
            System.out.println("mSubscriber  onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("mSubscriber  onError");
        }

        @Override
        public void onNext(String s) {
            System.out.println("mSubscriber  onNext");
        }
    };


    static Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            System.out.println("mObserver   onCompleted  Thread = " + Thread.currentThread().getName());
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Tom");
            subscriber.onCompleted();
        }
    });


    public static void main(String args[]) {
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .subscribe(mObserver);
        // 或者：
        // observable.subscribe(mSubscriber);
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable = Observable.from(words);
//        observable.doOnNext()
//        observable.subscribe(mObserver);
//
//        Observable observable1 = Observable.just("Hello", "Hi", "Aloha");
//        observable1.subscribe(mSubscriber);


        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                System.out.println("onNextAction " + s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                System.out.println("onErrorAction ");
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                System.out.println("onCompletedAction ");
            }
        };


// 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        //     observable.subscribe(onNextAction);
// 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        //   observable.subscribe(onNextAction, onErrorAction);
// 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        // observable.subscribe(onNextAction, onErrorAction, onCompletedAction);


        Observable.just("Hello", "Hi", "Aloha")
                .subscribeOn(Schedulers.io())
                //  .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("Action1   Thread " + Thread.currentThread().getName());
                        System.out.println("Action1   " + s);
                    }
                });


    }

}
