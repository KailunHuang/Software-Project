package application;

import java.util.ArrayList;
import java.util.Random;

public class Car extends Traffic {
    private static int count = 0;
    private static Random rnd = new Random();
    private String type;
    private ArrayList<Grid> intendPath;


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
            this.stop_factor = 1.10;
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
