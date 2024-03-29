package com.gougou.state.finite.table;

import com.gougou.state.finite.State;

public class ApplicationDemo {
    public static void main(String[] args) {
//        MarioStateMachine mario = new MarioStateMachine();
        MarioStateMachine mario = new MarioStateMachine();
        mario.obtainMushRoom();
        int score = mario.getScore();
        State state = mario.getCurrentState();
        System.out.println("mario score: " + score + "; state: " + state);
        mario.obtainFireFlower();
        score = mario.getScore();
        state = mario.getCurrentState();
        System.out.println("mario score: " + score + "; state: " + state);
    }
}
