package application;

/**
 * @author Jiexin.Luo
 * @date {2020/5/5}
 */
public class Record {
    //The type of the traffic subject;
    String selftype;
    //The name of the traffic subject;
    String selfname;
    //The action of the subject do;
    String action;

    boolean is_male1;

    boolean is_male2;

    int age1;

    int age2;
    //The type of the object that the subject interact with
    String targettype;
    //The name of the object that the subject interact with
    String targetname;
    //The x position of the location when the action performed
    int posx;
    //The y position of the location when the action performed
    int posy;

    //type of action: meet, appear, exit, pass, stop, crash, move , yield
    public Record(String type, String name, String action) {
        selftype = type;
        selfname = name;
        this.action = action;
    }

    public Record(String type, String name, boolean is_male1, int age1, String action, String targettype, String targetname, boolean is_male2, int age2) {
        selftype = type;
        selfname = name;
        this.action = action;
        this.targettype = targettype;
        this.targetname = targetname;
        this.is_male1 = is_male1;
        this.is_male2 = is_male2;
        this.age1 = age1;
        this.age2 = age2;
    }

    public Record(String type, String name, String action, int posx, int posy) {
        selftype = type;
        selfname = name;
        this.action = action;
        this.posx = posx;
        this.posy = posy;
    }

    public String getSelftype() {
        return selftype;
    }

    public String getSelfname() {
        return selfname;
    }

    public String getAction() {
        return action;
    }

    public String getTargettype() {
        return targettype;
    }

    public String getTargetname() {
        return targetname;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public String toString() {
        switch (this.action) {
            case "meet":
                return (this.selftype + " " + this.selfname + " ,age:" + this.age1 + " ,gender:" + (this.is_male1 ? "male " : "female ")
                        + this.action + " " + this.targettype + " " + this.targetname + " ,age:"+this.age2 + " ,gender:" + (this.is_male2 ? "male " : "female "));
            case "move":
                return (this.selftype + " " + this.selfname + " " + "move to (" + posx + "," + posy + ")");
            case "appear":
                return (this.selftype + " " + this.selfname + " " + "appear in " + "(" + posx + "," + posy + ")");
            case "crash":
                return (this.selftype + " " + this.selfname + " " + "crash at " + "(" + posx + "," + posy + ")");
            case "exit":
                return (this.selftype + " " + this.selfname + " " + "exit in " + "(" + posx + "," + posy + ")");
            case "stop":
                return (this.selftype + " " + this.selfname + " " + "stop at " + "(" + posx + "," + posy + ")");
            default:
                return (this.selftype + " " + this.selfname + " " + this.action);
        }
    }
}
