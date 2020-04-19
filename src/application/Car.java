package application;

public class Car extends Traffic {
	public String Type;

	
	public Car(String type, Boolean gender, int age) {
		super(gender, age);
		super.V_type="Car";
		this.Type = type;
	}
}
