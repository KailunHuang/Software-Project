package application;

import java.util.ArrayList;

/**
 * @author Jiexin.Luo
 * @date {2020/5/21}
 */
public class Gnode {
    Gnode prev;
    Grid current;

    public Gnode(Grid current,Gnode prev){
        this.prev = prev;
        this.current = current;
    }
}
