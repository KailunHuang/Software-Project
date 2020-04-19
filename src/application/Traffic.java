package application;

public abstract class Traffic {
	public boolean is_Male;
	public int Driver_Age;
	public String V_type;
	
	public Traffic(Boolean gender, int age) {
		this.is_Male = gender;
		this.Driver_Age = age;

	}
	
	
}
