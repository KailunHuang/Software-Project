package application;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jiexin.Luo
 * @date {2020/5/11}
 */
public class Simulation {
    private ArrayList<Grid> grids;
    private ArrayList<Traffic> allTraffic;
    private int crashCount;
    private int turnCount;
    private ArrayList<Traffic> unEmployedTraffic;
    private ArrayList<Traffic> inGridTraffic;
    private int max_involve_traffic;
    private ArrayList<ArrayList<Record>> fullRecord;
    private ArrayList<ArrayList<Record>> movementRecord;
    private Random rnd;

    /**                        /\                 \\\\\\\\\\\\\          \\\\\\\\\\\           \\\\\\\\\\\
     *                        //\\                \\\\      \\\              \\\               \\
     *                      /// \\\               \\\\      \\\              \\\               \\
     *                    ////  \\\\              \\\\\\\\\\\\\              \\\               \\\\\\\\\\\
     *                  ///////\\\\\\             \\\\                       \\\                        \\
     *                /////////\\\\\\\            \\\\                       \\\                        \\
     *              ///////       \\\\\           \\\\                   \\\\\\\\\\\           \\\\\\\\\\\
     */
    /**
     * Constructor
     *
     * @param trafficList
     */
    Simulation(ArrayList<Traffic> trafficList) {
        this.allTraffic = trafficList;
        fullRecord = new ArrayList<>();
        max_involve_traffic = 6;
    }

    public void reset() {
        grids = Grid.generateGrids();
        for (Traffic t : allTraffic) {
            t.reset();
        }
        Car.resetCount();
        Walker.resetCount();
        Cyclist.resetCount();
        unEmployedTraffic = new ArrayList<>();
        for (Traffic t : allTraffic) {
            unEmployedTraffic.add(t);
        }
        inGridTraffic = new ArrayList<>();
        fullRecord = new ArrayList<>();
        movementRecord = new ArrayList<>();
        rnd = new Random();
        crashCount = 0;
        turnCount = 0;
    }

    /**
     * After generate the simulation, you can use this method to start simulation.
     */
    public void start_simulate() {
        System.out.println("Simulation start...");
        long timeStarted = System.currentTimeMillis();
        this.reset();
        System.out.println("Initialize complete.");
        while ((!unEmployedTraffic.isEmpty()) || (!inGridTraffic.isEmpty())) {
            fullRecord.add(new ArrayList<Record>());
            movementRecord.add(new ArrayList<Record>());
            for (Traffic t : inGridTraffic) {
                Record r = t.move();
                if (r != null) {
                    fullRecord.get(turnCount).add(r);
                    movementRecord.get(turnCount).add(r);
                }
            }
            crash_detection();
            deploy();
            exit_detection();
            for (Traffic t : inGridTraffic) {
                t.chooseAction();
            }
            for (Traffic t : inGridTraffic) {
                ArrayList<Record> records = t.checkIfMoveAble();
                for (Record r : records) {
                    fullRecord.get(turnCount).add(r);
                }
            }
            turnCount++;
        }
        System.out.println("Simulation ends.Time consumed: " + (System.currentTimeMillis() - timeStarted) + " ms.");
    }

    /**
     * get the number of crashes in the simulation.
     *
     * @return
     */
    public int getCrashCount() {
        return crashCount;
    }

    /**
     * get the number of turns of the whole simulation.
     *
     * @return
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * get the full record of each traffic object during the simulation,
     * the record is a 2-d array, the first index indicate the number of turn and the second
     * index indicate the index of records during that turn;
     *
     * @return
     */
    public ArrayList<ArrayList<Record>> getFullRecord() {
        return fullRecord;
    }

    /**
     * get the record of the movement record of each traffic object during the simulation,
     * the record is a 2-d array, the first index indicate the number of turn and the second
     * index indicate the index of records during that turn;
     *
     * @return
     */
    public ArrayList<ArrayList<Record>> getMovementRecord() {
        return movementRecord;
    }

    /**
     * set the number of traffic object
     *
     * @param max_involve_traffic
     */
    public void setMax_involve_traffic(int max_involve_traffic) {
        this.max_involve_traffic = max_involve_traffic;
    }

    /**
     * Get the list of traffic object involved into the simulation.
     *
     * @return
     */
    public ArrayList<Traffic> getTrafficList() {
        return allTraffic;
    }

    /**
     * Set the list of traffic object involved into the simulation.
     *
     * @return
     */
    public void setTrafficList(ArrayList<Traffic> allTraffic) {
        this.allTraffic = allTraffic;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////private methods///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void crash_detection() {
        for (Grid g : grids) {
            if (g.getTraffics().size() > 1) {
                if (!checkCar(g.getTraffics())) {
                    continue;
                }
                ArrayList<Traffic> temp = new ArrayList<>();
                for (Traffic t : g.getTraffics()) {
                    Record r = new Record(t.getType(), t.getNo(), "crash", g.getAxis()[0], g.getAxis()[1]);
                    fullRecord.get(turnCount).add(r);
                    movementRecord.get(turnCount).add(r);
                    temp.add(t);
                    t.intendPos.intendToLeave(t);
                    inGridTraffic.remove(t);
                }
                for (Traffic t : temp) {
                    g.exitGrid(t);
                }
                crashCount++;
            }
        }
    }

    private boolean checkCar(ArrayList<Traffic> trafficList) {
        for (Traffic t : trafficList) {
            if (t instanceof Car) {
                return true;
            }
        }
        return false;
    }

    private void exit_detection() {
        ArrayList<Traffic> temp = new ArrayList<>();
        for (Traffic t : inGridTraffic) {
            if (t.status == Traffic.EXIT) {
                Record r = new Record(t.getType(), t.getNo(), "exit", t.currentPos.getAxis()[0], t.currentPos.getAxis()[1]);
                fullRecord.get(turnCount).add(r);
                movementRecord.get(turnCount).add(r);
                t.currentPos.exitGrid(t);
                temp.add(t);
            }
        }
        for (Traffic t : temp) {
            inGridTraffic.remove(t);
        }
    }

    private void deploy() {
        if (unEmployedTraffic.isEmpty() || inGridTraffic.size() >= max_involve_traffic) {
            return;
        }
        int counter = unEmployedTraffic.size();
        for (int i = 0; i < counter; i++) {
            Traffic t = unEmployedTraffic.get(rnd.nextInt(unEmployedTraffic.size()));
            double rndnum = Math.random();
            if ((t instanceof Car) && inGridTraffic.size() < max_involve_traffic && rndnum <= 0.6) {
                ArrayList<Grid> listOfGrid = Grid.findEmptyEntry(grids, true);
                addTraffic(t, listOfGrid);
                unEmployedTraffic.remove(t);
            } else if (!(t instanceof Car) && inGridTraffic.size() < max_involve_traffic && rndnum <= 0.6) {
                ArrayList<Grid> listOfGrid = Grid.findEmptyEntry(grids, false);
                addTraffic(t, listOfGrid);
                unEmployedTraffic.remove(t);
            }
        }
    }

    private void addTraffic(Traffic t, ArrayList<Grid> listOfGrid) {
        if (!listOfGrid.isEmpty()) {
            t.setCurrentPos(listOfGrid.get(rnd.nextInt(listOfGrid.size())));
            Record r = new Record(t.getType(), t.getNo(), "appear", t.currentPos.getAxis()[0], t.currentPos.getAxis()[1]);
            fullRecord.get(turnCount).add(r);
            movementRecord.get(turnCount).add(r);
            inGridTraffic.add(t);
            //System.out.println(t.getType()+t.getNo()+"deploy in "+t.currentPos.getAxis()[0]+" "+t.currentPos.getAxis()[1]);
        }
    }
}
