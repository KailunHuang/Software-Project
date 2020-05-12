package application;

import javafx.beans.property.SimpleStringProperty;

public class Information {
	private final SimpleStringProperty cartype;
	private final SimpleStringProperty age;
	private final SimpleStringProperty gender;
	
	
	public Information(String ctype, String age, String gder) {
		this.cartype=new SimpleStringProperty(ctype);
		this.age=new SimpleStringProperty(age);
		this.gender=new SimpleStringProperty(gder);
	}
	
	public String getCartype() {
		return cartype.get();
	}
	public void setCartype(String ctype) {
		cartype.set(ctype);;
	}
	public String getAge(){
		return age.get();
	}
	public void setAge(String ae) {
		age.set(ae);
	}
	public String getGender(){
		
		return gender.get();
	}
	public void setGender(String gder) {
		age.set(gder);;
	}
}
