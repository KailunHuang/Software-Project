package application;

import java.util.ArrayList;
import java.util.Random;

public class Walker extends Traffic {
    private static int count = 0;
    private static Random rnd = new Random();

    public Walker(boolean gender, int age) {
        super(gender, age);
        V_type = "Walker";
        count++;
        this.no = Integer.toString(count);
        this.stop_factor = 1.0;
        this.speed_factor = 1.0;
        this.crash_factor = 1.0;
        this.carelessRate = 0.6 - 0.02 * age;
        this.crash_factor += 0.06 * age;
        this.speed_factor += 0.02 * age;
        if (this.carelessRate < 0) {
            carelessRate = 0;
        }
        if (this.is_Male) {
            carelessRate += 0.05;
            this.stop_factor += 0.05;
        }
    }

    @Override
    public void selectDirection() {
        super.selectDirection();
        if (currentPos == null) {
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
    }

    public void chooseAction(){
        this.selectDirection();
        super.chooseAction();
    }

    public static void resetCount(){
        count = 0;
    }

    public String getType(){
        return V_type;
    }
}
