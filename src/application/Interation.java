package application;

import java.util.Random;

/**
 * @author Jiexin.Luo
 * @date {2020/4/30}
 */
public class Interation {
    public static final double FAIL_PROB = 0.2;
    public static final int CRASH = -1;
    public static double[] getCrashRate(Car a,Traffic b){
        double[] pass_probs = new double[2];
        pass_probs[0] = 0;
        pass_probs[1] = 0;
        if(Math.random()<a.carelessRate){
            pass_probs[0] = 1.0;
        }
        else {
            pass_probs[0] = (double) (b.payoff_stop - b.payoff_pass) / (double) (b.payoff_crash - b.payoff_pass);
        }
        if(pass_probs[0]>1.0){
            pass_probs[0] = 1.0;
        }
        if(Math.random()<b.carelessRate){
            pass_probs[1] = 1.0;
        }
        else {
            pass_probs[1]= (double) (a.payoff_stop - a.payoff_pass) / (double) (a.payoff_crash - a.payoff_pass);
        }
        if(pass_probs[1]>1.0){
            pass_probs[1] = 1.0;
        }
        return pass_probs;
    }

    public static int[] interact(Car a,Traffic b){
        int result[] = new int[2];
        result[0] = 0;
        result[1] = 0;
        double[] pass_probs = getCrashRate(a,b);
        int act_a = 0;
        int act_b = 0;
        if(Math.random()<pass_probs[0]){
            act_a = Traffic.MOVE;
        }
        else{
            act_a = Traffic.STOP;
            if(Math.random()<FAIL_PROB){
                //fail to stop
                act_a = Traffic.MOVE;
            }
        }
        if(Math.random()<pass_probs[1]){
            act_b = Traffic.MOVE;
        }
        else{
            if(b instanceof Car){
                if(Math.random()<FAIL_PROB){
                    //fail to stop
                    act_a = Traffic.MOVE;
                }
            }
        }
        if(act_a == Traffic.MOVE && act_b == Traffic.MOVE){
            result[0] = CRASH;
            result[1] = CRASH;
        }
        result[0] = act_a;
        result[1] = act_b;
        return result;
    }
}
