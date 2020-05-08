package application;

/**
 * @author Jiexin.Luo
 * @date {2020/5/5}
 */
public class Record {
    String selftype;
    String selfname;
    String action;
    String targettype;
    String targetname;
    int posx;
    int posy;

    //type of action: meet, appear, exit, pass, stop, crash, move
    public Record(String type, String name, String action){
        selftype = type;
        selfname = name;
        this.action = action;
    }

    public Record(String type, String name, String action, String targettype, String targetname){
        selftype = type;
        selfname = name;
        this.action = action;
        this.targettype = targettype;
        this.targetname = name;
    }

   public Record(String type, String name, String action, int posx, int posy){
        selftype = type;
        selfname = name;
        this.action = action;
        this.posx = posx;
        this.posy = posy;
    }

    public String toString(){
        switch(this.action){
            case "meet" : return (this.selftype+" "+this.selfname+" "+this.action+" "+this.targettype+" "+this.targetname);
            case "move" : return (this.selftype+" "+this.selfname+" "+"move to ("+posx+","+posy+")");
            case "appear" : return (this.selftype+" "+this.selfname+" "+"appear in "+"("+posx+","+posy+")");
            case "exit" : return (this.selftype+" "+this.selfname+" "+"exit in "+"("+posx+","+posy+")");
            default: return (this.selftype+" "+this.selfname+" "+this.action);
        }
    }
}
