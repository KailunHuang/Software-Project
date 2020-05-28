package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class Random_config {

    private int count;

    public void simulate(){
        try {
            String[] object_types = {"Car","Walker","Cyclist"};
            String[] car_types = {"Car","Bus","Truck"};
            String[] gender = {"Female","Male"};
            FileWriter fw = new FileWriter("Random.txt");
            for (int i = 0; i < count ; i++) {
                StringBuffer str = new StringBuffer();
                String object = object_types[(int)(Math.random()*3)];
                str.append(object+",");
                if(object.equals("Car")){
                    str.append(car_types[(int)(Math.random()*3)]+",");
                }
                str.append(gender[(int)(Math.random()*2)]+",");
                if(object.equals("Car")) {
                    str.append((int) (Math.random() * 83 + 18) + "\n");
                }else {
                    str.append((int) (Math.random() * 101 ) + "\n");
                }
                fw.write(str.toString());
                fw.flush();
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Random_config(int count) {
        this.count = count;
    }
}