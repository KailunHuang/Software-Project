package application;

/**
 * @author Jiexin.Luo
 * @date {2020/5/13}
 */
public class SingleInterationTest {
    public static void main(String args[]){
        Car c1 = new Car("Car",true,20);
        Walker w1 = new Walker(true,20);
        double[] result = Interation.getCrashRate(c1,w1);
        System.out.println(c1.getType()+" "+c1.getNo()+" passRate: "+result[0]);
        System.out.println(w1.getType()+" "+w1.getNo()+" passRate: "+result[1]);
    }
}
