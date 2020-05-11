package application;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jiexin.Luo
 * @date {2020/4/30}
 */
public class Interation {
    public static final double FAIL_PROB = 0.3;
    private static ArrayList<Traffic> trafficList;
    public static final int CRASH = -1;
    public static final int[][] PAYOFF_CAR_CAR = {{-10,-2},{-4,2},{5,-1},{-20,-4}};
    public static final int[][] PAYOFF_CAR_WALKER = {{10,-2},{-5,2},{5,-1},{-40,-99}};
    public static final int[][] PAYOFF_CAR_BIKE = {{-10,-4},{-5,2},{5,-1},{-30,-99}};
    static{
        trafficList = new ArrayList<>();
    }

    public static double[] getCrashRate(Car a,Traffic b){
        double[] pass_probs = new double[2];
        pass_probs[0] = 0;
        pass_probs[1] = 0;
        int[][] payoffs;
        if(b instanceof Car){
            payoffs = PAYOFF_CAR_CAR.clone();
        }
        else if(b instanceof Walker){
            payoffs = PAYOFF_CAR_WALKER.clone();
        }
        else{
            payoffs = PAYOFF_CAR_BIKE.clone();
        }
        payoffs[0][0] = (int)Math.round((double)payoffs[0][0]*(a.stop_factor));
        payoffs[0][1] = (int)Math.round((double)payoffs[0][1]*(b.stop_factor));
        payoffs[1][0] = (int)Math.round((double)payoffs[1][0]*(a.stop_factor*b.speed_factor));
        payoffs[1][1] = (int)Math.round((double)payoffs[1][1]*b.stop_factor);
        payoffs[2][0] = (int)Math.round((double)payoffs[2][0]*b.stop_factor);
        payoffs[2][1] = (int)Math.round((double)payoffs[1][0]*(b.stop_factor*a.speed_factor));
        payoffs[3][0] = (int)Math.round((double)payoffs[3][0]*a.crash_factor);
        payoffs[3][1] = (int)Math.round((double)payoffs[3][1]*b.crash_factor);
        if(Math.random()<a.carelessRate){
            pass_probs[0] = 1.0;
        }
        else {
            pass_probs[0] = 1.0 - (double) (payoffs[3][1]-payoffs[2][1]) / (double) (payoffs[0][1]+payoffs[3][1]-payoffs[1][1]-payoffs[2][1]);
        }
        if(pass_probs[0]>1.0){
            pass_probs[0] = 1.0;
        }
        if(Math.random()<b.carelessRate){
            pass_probs[1] = 1.0;
        }
        else {
            pass_probs[1]= 1.0 - (double) (payoffs[3][0]-payoffs[1][0]) / (double) (payoffs[0][0]+payoffs[3][0]-payoffs[1][0]-payoffs[2][0]);
        }
        if(pass_probs[1]>1.0){
            pass_probs[1] = 1.0;
        }
        return pass_probs;
    }

    public static double passrate_singal(Traffic a,Traffic b){
        if(a instanceof Car){
            return getCrashRate((Car)a,b)[0];
        }
        if(b instanceof Car){
            return getCrashRate((Car)b,a)[1];
        }
        return 1.0;
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
        result[0] = act_a;
        result[1] = act_b;
        return result;
    }

}
