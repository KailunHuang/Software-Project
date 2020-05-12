package application;

import java.util.ArrayList;
import java.util.Random;

public class Car extends Traffic {
    private static int count = 0;
    private static Random rnd = new Random();
    private String type;

    Car(String type, boolean gender, int age) {
        super(gender, age);
        count++;
        this.no = Integer.toString(count);
        this.V_type = "Car";
        this.type = type;
        if (type.equals("Truck")) {
            this.speed_factor = 0.8;
            this.crash_factor = 0.8;
            this.stop_factor = 1.0;
        } else if (type.equals("Bus")) {
            this.speed_factor = 0.85;
            this.crash_factor = 1.2;
        } else {
            this.speed_factor = 0.6;
            this.crash_factor = 1.0;
        }
        this.carelessRate = 0.7 - 0.02 * age;
        this.crash_factor += 0.02 * age;
        if (this.carelessRate < 0) {
            carelessRate = 0;
        }
        if (this.is_Male) {
            carelessRate += 0.05;
            this.stop_factor += 0.05;
        }
    }

    Car(boolean gender, int age) {
        this("Car", gender, age);
    }

    public static void resetCount() {
        count = 0;
    }

    @Override
    public void chooseAction() {
        this.selectDirection();
        super.chooseAction();
        if (status == STOP && Math.random() <= Interation.FAIL_PROB) {
            status = MOVE;
        }
    }

    public void selectDirection() {
        super.selectDirection();
        if (currentPos == null || currentPos.isExit()) {
            return;
        }
        ArrayList<Grid> availableGrids = this.currentPos.getNextGridsForCar();
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

    public String getType() {
        return type;
    }
}
