package application;

public abstract class Traffic {
	protected Boolean is_Male;
	protected int Driver_Age;
	protected String V_type;
	
	public Traffic(Boolean gender, int age) {
		this.is_Male = gender;
		this.Driver_Age = age;

	}
	
}
