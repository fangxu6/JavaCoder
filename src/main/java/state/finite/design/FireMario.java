package state.finite.design;

import state.finite.State;

public class FireMario implements IMario  {
    private MarioStateMachine stateMachine;
    public FireMario(MarioStateMachine stateMachine) {
        this.stateMachine = stateMachine;

    }

    @Override
    public State getName() {
        return State.FIRE;
    }

    @Override
    public void obtainMushRoom() {

    }

    @Override
    public void obtainCape() {

    }

    @Override
    public void obtainFireFlower() {

    }

    @Override
    public void meetMonster() {
//        stateMachine.setCurrentState(new SmallMario(stateMachine));
//        stateMachine.setScore(stateMachine.getScore() - 100);
        stateMachine.setCurrentState(new SmallMario(stateMachine));
        stateMachine.setScore(stateMachine.getScore() - 100);
    }
}
