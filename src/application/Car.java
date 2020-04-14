package application;

public class Car extends Traffic {
	private String Type;
	private Boolean is_Male;
	private int Driver_Age;
	
	public Car(String type, Boolean gender, int age) {
		this.Type = type;
		this.is_Male = gender;
		this.Driver_Age = age;
	}
}
