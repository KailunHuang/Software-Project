package application;

public class Car extends Traffic {
	private String Type;

	
	public Car(String type, Boolean gender, int age) {
		super(gender, age);
		super.V_type="car";
		this.Type = type;
	}
}
