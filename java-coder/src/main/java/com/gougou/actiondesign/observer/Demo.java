package com.gougou.actiondesign.observer;

public class Demo {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserverOne();
        subject.registerObserver(observer1);
        subject.registerObserver(new ConcreteObserverTwo());
        subject.notifyObservers(new Message());
        subject.removeObserver(observer1);
        subject.notifyObservers(new Message());
    }
}
