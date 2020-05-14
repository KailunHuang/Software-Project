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
