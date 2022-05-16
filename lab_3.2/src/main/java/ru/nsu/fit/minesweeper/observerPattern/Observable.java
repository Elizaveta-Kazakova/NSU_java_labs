package ru.nsu.fit.minesweeper.observerPattern;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<Observer> observers = new ArrayList<>();
    List<TimeObserver> timeObservers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void addTimeObserver(TimeObserver timeObserver) {
        timeObservers.add(timeObserver);
    }

    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void notifyTimeObserver() {
        for (TimeObserver timeObserver : timeObservers) {
            timeObserver.updateTime();
        }
    }
}
