package application;

import java.util.ArrayList;

public abstract class Traffic {
    public static final int MOVE = 1;
    public static final int STOP = 0;
    public static final int EXIT = -1;
    //gender
    protected Boolean is_Male;
    protected int age;
    //rate of careless action
    protected double carelessRate;
    //speed factor>0 && factor<1
    protected double speed_factor;
    protected double stop_factor;
    protected double crash_factor;
    protected String V_type;
    protected Grid currentPos;
    protected Grid intendPos;
    protected int status;
    protected ArrayList<Grid> visited;
    protected String no;
    ArrayList<Record> buffer;

    public Traffic(boolean gender, int age) {
        this.is_Male = gender;
        this.age = age;
        this.status = MOVE;
        visited = new ArrayList<>();
        buffer = new ArrayList<>();
    }

    public void reset() {
        this.status = MOVE;
        visited = new ArrayList<>();
        buffer = new ArrayList<>();
        currentPos = null;
        intendPos = null;
    }

    boolean isTraveled(Grid g) {
        for (Grid temp : visited) {
            if (g.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    public void setCurrentPos(Grid g) {
        this.currentPos = g;
        visited.add(g);
        g.enterGrid(this);
    }

    public Record move() {
        if (status == MOVE) {
            if (currentPos.equals(intendPos)) {
                System.out.println("deadloop: " + currentPos.getAxis()[0] + " , " + currentPos.getAxis()[1]);
                System.exit(0);
            }
            currentPos.exitGrid(this);
            intendPos.intendToLeave(this);
            currentPos = intendPos;
            currentPos.enterGrid(this);
            visited.add(currentPos);
            if (currentPos.isExit()) {
                status = EXIT;
                currentPos.intendToLeave(this);
            }
            //System.out.println(this.getType() + this.getNo() + " move to (" + currentPos.getAxis()[0] + " " + currentPos.getAxis()[1] + ")");
            return new Record(this.getType(), this.no, "move", currentPos.getAxis()[0], currentPos.getAxis()[1]);
        }
        if (status == STOP) {
            //System.out.println(this.getType() + this.getNo() + " stop (" + currentPos.getAxis()[0] + " " + currentPos.getAxis()[1] + ")");
            return new Record(this.getType(), this.no, "stop", currentPos.getAxis()[0], currentPos.getAxis()[1]);
        }
        return null;
    }

    public void selectDirection() {
        if (status == STOP && intendPos != null) {
            return;
        }
        if (this.intendPos != null) {
            intendPos.intendToLeave(this);
        }
        checkDeadEnd();
    }

    public String getV_type() {
        return V_type;
    }

    public abstract String getType();

    public String getNo() {
        return no;
    }

    void chooseAction() {
        buffer = new ArrayList<>();
        if (intendPos.getIntendTraffics().isEmpty()) {
            return;
        }
        double passRate = 1.0;
        for (Traffic t : intendPos.getIntendTraffics()) {
            if (t.equals(this)) {
                continue;
            }
            if (this instanceof Car || t instanceof Car) {
                buffer.add(new Record(this.getType(), this.no,this.is_Male,this.age,"meet", t.getType(), t.no,t.is_Male,t.age));
            }
            passRate = passRate * Interation.passrate_singal(this, t);
        }
        if (Math.random() > passRate) {
            //System.out.println(this.getType()+" "+this.getNo()+" rate: "+passRate);
            this.status = STOP;
        }
    }

    public ArrayList<Record> checkIfMoveAble() {
        if (this.status == STOP) {
            buffer.add(new Record(this.getType(), this.no, "yield"));
            return buffer;
        }
        if (this.intendPos.getTraffics().isEmpty()) {
            buffer.add(new Record(this.getType(), this.no, "pass"));
            return buffer;
        }
        for (Traffic t : this.intendPos.getTraffics()) {
            if ((!this.getV_type().equals("Car")) && (!t.getV_type().equals("Car"))) {
                continue;
            }
            if (t.status == STOP) {
                buffer = new ArrayList<>();
                this.status = STOP;
                buffer.add(new Record(this.getType(), this.no, "yield"));
                return buffer;
            }
        }
        buffer.add(new Record(this.getType(), this.no, "pass"));
        return buffer;
    }

    public boolean equals(Object o){
        if(!(o instanceof Traffic)){
            return false;
        }
        else if(((Traffic) o).getType().equals(this.getType())&&((Traffic) o).no.equals(this.no)){
            return true;
        }
        return false;
    }

    private void checkDeadEnd(){
        int count = 0;
        if(this.V_type.equals("Car")){
            for(Grid g:currentPos.getNextGridsForCar()){
                if(this.isTraveled(g)){
                    count ++;
                }
            }
            if(count == currentPos.getNextGridsForCar().size()){
                System.out.println(getType()+" "+getNo()+" reach to deadend: "+currentPos.getAxis()[0]+" , "+currentPos.getAxis()[1]);
                System.exit(0);
            }
        }
        else{
            for(Grid g:currentPos.getNextGridsForNonCar()){
                if(this.isTraveled(g)){
                    count ++;
                }
            }
            if(count == currentPos.getNextGridsForNonCar().size()){
                System.out.println(getType()+" "+getNo()+" reach to deadend: "+currentPos.getAxis()[0]+" , "+currentPos.getAxis()[1]);
                System.exit(0);
            }
        }
    }
}
