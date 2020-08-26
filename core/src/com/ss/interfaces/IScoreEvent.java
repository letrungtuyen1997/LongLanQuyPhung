package com.ss.interfaces;

public interface IScoreEvent {
    public void onScoreUp(int score);
    public void boardTerminated(boolean temp);
}
