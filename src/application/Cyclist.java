package application;

import java.util.ArrayList;
import java.util.Random;

public class Cyclist extends Traffic {
    private static int count = 0;
    private static Random rnd = new Random();

    public Cyclist(boolean gender, int age) {
        super(gender, age);
        super.V_type = "Cyclist";
        count++;
        this.no = Integer.toString(count);
        this.stop_factor = 1.0;
        this.speed_factor = 0.9;
        this.crash_factor = 1.0;
        this.carelessRate = 0.7 - 0.02 * age;
        this.crash_factor += 0.04 * age;
        this.speed_factor += 0.03 * age;
        if (this.carelessRate < 0) {
            carelessRate = 0;
        }
        if (this.is_Male) {
            carelessRate += 0.05;
            this.stop_factor += 0.05;
        }
    }

    public static void resetCount() {
        count = 0;
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

    public void chooseAction() {
        this.selectDirection();
        super.chooseAction();
    }

    public String getType() {
        return V_type;
    }
}
