package com.fh.model.observer;

import com.fh.model.observer.impl.BinaryObserver;
import com.fh.model.observer.impl.HexaObserver;
import com.fh.model.observer.impl.OctalObserver;

/**
 * 观察者模式：在Subject类的的set方法中设定触发条件，触发时调用Observer的update方法
 */
public class ObserverPatternDemo {

    public static void main(String[] args) {
        Subject subject = new Subject();

        new HexaObserver(subject);
        new OctalObserver(subject);
        new BinaryObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("Second state change: 10");
        subject.setState(10);
    }

}
