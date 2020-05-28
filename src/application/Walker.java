package application;

import java.util.ArrayList;
import java.util.Random;

public class Walker extends Traffic {
    private static int count = 0;
    private static Random rnd = new Random();
    private static double stop_factor_basic = 1.0;
    private static double speed_factor_basic = 1.0;
    private static double crash_factor_basic = 1.0;
    private static double carelessRate_basic = 0.6;
    private static double caution_changeByAge = 0.02;
    private static double weakness_changeByAge = 0.05;
    private static double caution_genderDifference = 0.05;

    public static void setCount(int count) {
        Walker.count = count;
    }

    public static double getStop_factor_basic() {
        return stop_factor_basic;
    }

    public static void setStop_factor_basic(double stop_factor_basic) {
        Walker.stop_factor_basic = stop_factor_basic;
    }

    public static double getSpeed_factor_basic() {
        return speed_factor_basic;
    }

    public static void setSpeed_factor_basic(double speed_factor_basic) {
        Walker.speed_factor_basic = speed_factor_basic;
    }

    public static double getCrash_factor_basic() {
        return crash_factor_basic;
    }

    public static void setCrash_factor_basic(double crash_factor_basic) {
        Walker.crash_factor_basic = crash_factor_basic;
    }

    public static double getCarelessRate_basic() {
        return carelessRate_basic;
    }

    public static void setCarelessRate_basic(double carelessRate_basic) {
        Walker.carelessRate_basic = carelessRate_basic;
    }

    public static double getCaution_changeByAge() {
        return caution_changeByAge;
    }

    public static void setCaution_changeByAge(double caution_changeByAge) {
        Walker.caution_changeByAge = caution_changeByAge;
    }

    public static double getWeakness_changeByAge() {
        return weakness_changeByAge;
    }

    public static void setWeakness_changeByAge(double weakness_changeByAge) {
        Walker.weakness_changeByAge = weakness_changeByAge;
    }

    public static double getCaution_genderDifference() {
        return caution_genderDifference;
    }

    public static void setCaution_genderDifference(double caution_genderDifference) {
        Walker.caution_genderDifference = caution_genderDifference;
    }

    public Walker(boolean gender, int age) {
        super(gender, age);
        V_type = "Walker";
        count++;
        this.no = Integer.toString(count);
        this.stop_factor = stop_factor_basic;
        this.speed_factor = speed_factor_basic;
        this.crash_factor = crash_factor_basic;
        this.carelessRate = carelessRate - caution_genderDifference * age;
        this.crash_factor += weakness_changeByAge * age;
        this.speed_factor += weakness_changeByAge * 0.7 * age;
        if (this.carelessRate < 0) {
            carelessRate = 0;
        }
        if (this.is_Male) {
            carelessRate += caution_genderDifference;
            this.stop_factor += caution_genderDifference;
        }
    }

    public static void resetCount() {
        count = 0;
    }

    @Override
    public void selectDirection() {
        if (status == STOP && intendPos != null) {
            status = MOVE;
            return;
        }
        super.selectDirection();
        if (currentPos == null || status == STOP) {
            return;
        }
        ArrayList<Grid> availableGrids = this.currentPos.getNextGridsForNonCar();
        boolean notSelected = true;
        while (notSelected) {
            Grid g = availableGrids.get(rnd.nextInt(availableGrids.size()));
            if (isTraveled(g)) {
                continue;
            }
            notSelected = false;
            intendPos = g;
            g.intendToGo(this);
        }
        status = MOVE;
    }

    public void chooseAction() {
        this.selectDirection();
        super.chooseAction();
    }

    public String getType() {
        return V_type;
    }
}
