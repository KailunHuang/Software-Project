package application;

import java.util.ArrayList;

/**
 * @author Jiexin.Luo
 * @date {2020/5/5}
 */

public class Testoutput {
    public static void main(String[] args) {
        ArrayList<Record> snapshot = null;
        ArrayList<ArrayList<Record>> fullrecords = new ArrayList<>();
        snapshot = new ArrayList<>();
        snapshot.add(new Record("car", "1", "appear", 1, 1));
        snapshot.add(new Record("car", "2", "appear", 2, 2));
        fullrecords.add(snapshot);
        snapshot = new ArrayList<>();
        snapshot.add(new Record("car", "1",true ,22,"meet", "car", "2",true,22));
        snapshot.add(new Record("car", "1", "stop"));
        snapshot.add(new Record("car", "2", "pass"));
        fullrecords.add(snapshot);
        snapshot = new ArrayList<>();
        snapshot.add(new Record("car", "1", "move", 1, 2));
        fullrecords.add(snapshot);
        snapshot = new ArrayList<>();
        snapshot.add(new Record("car", "1", "exit", 1, 2));
        snapshot.add(new Record("car", "2", "move", 1, 2));
        fullrecords.add(snapshot);
        snapshot = new ArrayList<>();
        snapshot.add(new Record("car", "2", "exit", 1, 2));
        fullrecords.add(snapshot);
        for (int i = 0; i < fullrecords.size(); i++) {
            System.out.println("round " + (i + 1) + ":");
            for (Record record : fullrecords.get(i)) {
                System.out.println(record);
            }
        }
    }

}
