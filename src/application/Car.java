package application;

import java.util.ArrayList;
import java.util.Random;

public class Car extends Traffic {
    private static int count = 0;
    private static Random rnd = new Random();
    private String type;
    private ArrayList<Grid> intendPath;
    private static double stop_factor_car_basic = 1.0;
    private static double stop_factor_bus_basic = 1.1;
    private static double stop_factor_truck_basic = 1.0;
    private static double speed_factor_car_basic = 1.0;
    private static double speed_factor_bus_basic = 0.85;
    private static double speed_factor_truck_basic = 0.8;
    private static double crash_factor_car_basic = 0.8;
    private static double crash_factor_truck_basic = 1.0;
    private static double crash_factor_bus_basic = 1.2;
    private static double carelessRate_basic = 0.7;
    private static double caution_changeByAge = 0.02;
    private static double caution_genderDifference = 0.05;

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Car.count = count;
    }

    public static double getStop_factor_car_basic() {
        return stop_factor_car_basic;
    }

    public static void setStop_factor_car_basic(double stop_factor_car_basic) {
        Car.stop_factor_car_basic = stop_factor_car_basic;
    }

    public static double getStop_factor_bus_basic() {
        return stop_factor_bus_basic;
    }

    public static void setStop_factor_bus_basic(double stop_factor_bus_basic) {
        Car.stop_factor_bus_basic = stop_factor_bus_basic;
    }

    public static double getStop_factor_truck_basic() {
        return stop_factor_truck_basic;
    }

    public static void setStop_factor_truck_basic(double stop_factor_truck_basic) {
        Car.stop_factor_truck_basic = stop_factor_truck_basic;
    }

    public static double getSpeed_factor_car_basic() {
        return speed_factor_car_basic;
    }

    public static void setSpeed_factor_car_basic(double speed_factor_car_basic) {
        Car.speed_factor_car_basic = speed_factor_car_basic;
    }

    public static double getSpeed_factor_bus_basic() {
        return speed_factor_bus_basic;
    }

    public static void setSpeed_factor_bus_basic(double speed_factor_bus_basic) {
        Car.speed_factor_bus_basic = speed_factor_bus_basic;
    }

    public static double getSpeed_factor_truck_basic() {
        return speed_factor_truck_basic;
    }

    public static void setSpeed_factor_truck_basic(double speed_factor_truck_basic) {
        Car.speed_factor_truck_basic = speed_factor_truck_basic;
    }

    public static double getCrash_factor_car_basic() {
        return crash_factor_car_basic;
    }

    public static void setCrash_factor_car_basic(double crash_factor_car_basic) {
        Car.crash_factor_car_basic = crash_factor_car_basic;
    }

    public static double getCrash_factor_truck_basic() {
        return crash_factor_truck_basic;
    }

    public static void setCrash_factor_truck_basic(double crash_factor_truck_basic) {
        Car.crash_factor_truck_basic = crash_factor_truck_basic;
    }

    public static double getCrash_factor_bus_basic() {
        return crash_factor_bus_basic;
    }

    public static void setCrash_factor_bus_basic(double crash_factor_bus_basic) {
        Car.crash_factor_bus_basic = crash_factor_bus_basic;
    }

    public static double getCarelessRate_basic() {
        return carelessRate_basic;
    }

    public static void setCarelessRate_basic(double carelessRate_basic) {
        Car.carelessRate_basic = carelessRate_basic;
    }

    public static double getCaution_changeByAge() {
        return caution_changeByAge;
    }

    public static void setCaution_changeByAge(double caution_changeByAge) {
        Car.caution_changeByAge = caution_changeByAge;
    }

    public static double getCaution_genderDifference() {
        return caution_genderDifference;
    }

    public static void setCaution_genderDifference(double caution_genderDifference) {
        Car.caution_genderDifference = caution_genderDifference;
    }

    Car(String type, boolean gender, int age) {
        super(gender, age);
        count++;
        this.no = Integer.toString(count);
        this.V_type = "Car";
        this.type = type;
        if (type.equals("Truck")) {
            this.speed_factor = speed_factor_truck_basic;
            this.crash_factor = crash_factor_truck_basic;
            this.stop_factor = stop_factor_truck_basic;
        } else if (type.equals("Bus")) {
            this.stop_factor = stop_factor_bus_basic;
            this.speed_factor = speed_factor_bus_basic;
            this.crash_factor = crash_factor_bus_basic;
        } else {
            this.speed_factor = speed_factor_car_basic;
            this.crash_factor = crash_factor_car_basic;
            this.stop_factor = stop_factor_car_basic;
        }
        this.carelessRate = carelessRate_basic - caution_changeByAge * age;
        this.crash_factor += caution_changeByAge * age;
        if (this.carelessRate < 0) {
            carelessRate = 0;
        }
        if (this.is_Male) {
            carelessRate += caution_genderDifference;
            this.stop_factor += caution_genderDifference;
        }
    }

    Car(boolean gender, int age) {
        this("Car", gender, age);
    }

    public static void resetCount() {
        count = 0;
    }

    private static boolean BFS_visited(ArrayList<Grid> visited, Grid g) {
        for (Grid temp : visited) {
            if (g.equals(temp)) {
                return true;
            }
        }
        return false;
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
        if (status == STOP && intendPos != null) {
            status = MOVE;
            return;
        }
        if (currentPos == null || currentPos.isExit()) {
            return;
        }
        int indexNow = intendPath.indexOf(currentPos);
        if (indexNow < (intendPath.size() - 1)) {
            intendPos = intendPath.get(indexNow + 1);
            intendPos.intendToGo(this);
        }
        status = MOVE;
    }

    public void setGoal(ArrayList<Grid> grids) {
        intendPath = new ArrayList<>();
        ArrayList<Grid> exits = Grid.findExitsForCar(grids);
        Grid goal = exits.get(rnd.nextInt(exits.size()));
        BFS(currentPos, goal);
    }

    public String getType() {
        return type;
    }

    private void BFS(Grid startPoint, Grid goal) {
        ArrayList<Grid> visited = new ArrayList<>();
        ArrayList<Gnode> queue = new ArrayList<>();
        queue.add(new Gnode(startPoint, null));
        while (!queue.isEmpty()) {
            Gnode temp = queue.get(0);
            visited.add(temp.current);
            if (temp.current.equals(goal)) {
                intendPath.add(0, goal);
                Gnode nodePrev = temp.prev;
                while (nodePrev != null) {
                    intendPath.add(0, nodePrev.current);
                    nodePrev = nodePrev.prev;
                }
                return;
            }
            for (Grid g : temp.current.getNextGridsForCar()) {
                if (!BFS_visited(visited, g)) {
                    queue.add(new Gnode(g, temp));
                }
            }
            queue.remove(0);
        }
    }
}
