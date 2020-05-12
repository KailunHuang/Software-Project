package application;

import java.util.ArrayList;

/**
 * @author Jiexin.Luo
 * @date {2020/5/5}
 */
public class Testoutput {
	
	public ArrayList<Record> snapshot = null;
    public ArrayList<ArrayList<Record>> fullrecords = new ArrayList<>();
	
   public Testoutput(){
      
       snapshot = new ArrayList<>();
       snapshot.add(new Record("car","1","appear",3,0));
       snapshot.add(new Record("car","2","appear",0,3));
       fullrecords.add(snapshot);
       snapshot = new ArrayList<>();
       snapshot.add(new Record("car","1","move",3,1));
       snapshot.add(new Record("car","2","move",1,3));
       fullrecords.add(snapshot);
       snapshot = new ArrayList<>();
       snapshot.add(new Record("car","1","move",3,2));
       snapshot.add(new Record("car","2","move",2,3));
       fullrecords.add(snapshot);
       snapshot = new ArrayList<>();
       snapshot.add(new Record("car","2","move",3,3));
       snapshot.add(new Record("car","1","meet","car","2"));
       snapshot.add(new Record("car","1","stop",3,2));
       snapshot.add(new Record("car","2","pass"));
       fullrecords.add(snapshot);
       snapshot = new ArrayList<>();
       snapshot.add(new Record("car","1","move",3,3));
       snapshot.add(new Record("car","2","move",4,3));
       fullrecords.add(snapshot);
       snapshot = new ArrayList<>();
       snapshot.add(new Record("car","1","move",3,4));
       snapshot.add(new Record("car","2","exit",4,3));
       fullrecords.add(snapshot);
       snapshot = new ArrayList<>();
       snapshot.add(new Record("car","1","exit",3,4));
       fullrecords.add(snapshot);
       for(int i = 0;i<fullrecords.size();i++){
           System.out.println("round "+(i+1)+":");
           for(Record record:fullrecords.get(i)){
               System.out.println(record);
           }
       }
   }
}
