package application;

import java.util.Random;

public abstract class Traffic {
    public static final int MOVE = 1;
    public static final int STOP = 0;
    //gender
    protected Boolean is_Male;
    protected int age;
    //rate of careless action
    protected double carelessRate;
    //payoff of passing
    protected int payoff_pass;
    //payoff of stopping
    protected int payoff_stop;
    //payoff of crashing
    protected int payoff_crash;

}
