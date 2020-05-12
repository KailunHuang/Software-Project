package application;

import java.util.ArrayList;

/**
 * @author Jiexin.Luo
 * @date {2020/5/11}
 */
public class SimulationTest {
    public static void main(String[] args){
        ArrayList<Traffic> list = new ArrayList<>();
        for(int i=0;i<=10;i++){
            list.add(new Walker(true,20));
        }
        for(int i=0;i<=10;i++){
            list.add(new Car(true,25));
        }
        for(int i=0;i<=5;i++){
            list.add(new Car("Truck",true,25));
        }
        Simulation s = new Simulation(list);
        s.start_simulate();
        ArrayList<ArrayList<Record>> result = s.getMovementRecord();
        for(int i=0;i<result.size();i++){
            System.out.println("round "+i+" :");
            for(Record r:result.get(i)){
                System.out.println("     "+r);
            }
        }
    }
}
