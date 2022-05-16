package ru.nsu.fit.minesweeper.timer;


import ru.nsu.fit.minesweeper.observerPattern.Observable;

public class Timer extends Observable implements Runnable {
    private static final int START_TIMER_VAL = 0;
    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int UNFORMATTED_TIME_BOUND = 9;
    private static final String ADDITIONAL_SYMBOL_FOR_TIME_FORMAT = "0";
    private static final String TIME_DELIMITER = ":";

    private int intervalSeconds;
    private boolean isActive = true;

    public Timer() {
        intervalSeconds = START_TIMER_VAL;
    }

    @Override
    public void run() {
        while(isActive) {
            notifyTimeObserver();
            try {
                Thread.sleep(MILLISECONDS_IN_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++intervalSeconds;
        }
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getFormattedTime() {
        int minutes = intervalSeconds / SECONDS_IN_MINUTE;
        int seconds = intervalSeconds % SECONDS_IN_MINUTE;
        String minutesFormat = String.valueOf(minutes);;
        String secondsFormat = String.valueOf(seconds);
        if (minutes <= UNFORMATTED_TIME_BOUND) {
            minutesFormat = ADDITIONAL_SYMBOL_FOR_TIME_FORMAT + minutesFormat;
        }
        if (seconds <= UNFORMATTED_TIME_BOUND) {
            secondsFormat = ADDITIONAL_SYMBOL_FOR_TIME_FORMAT + secondsFormat;
        }
        return minutesFormat + TIME_DELIMITER + secondsFormat;
    }

    public void restartTimer() {
        intervalSeconds = START_TIMER_VAL;
        isActive = true;
        notifyTimeObserver();
    }


}
