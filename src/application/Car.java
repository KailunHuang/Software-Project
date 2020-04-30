package application;

import java.util.Random;

public class Car extends Traffic {
<<<<<<< HEAD
	//private String Type;
	
	public Car(String type, Boolean gender, int age) {
		//this.Type = type;
		this.is_Male = gender;
		this.age = age;
		this.carelessRate = 0.7 - 0.1*age*70;
		if(this.carelessRate<0){
			carelessRate = 0;
		}
		this.payoff_crash = -100 - 5*this.age;
		this.payoff_pass = 50 + 5*this.age;
		this.payoff_stop = 0;
		if(this.is_Male){
			carelessRate += 0.05;
			this.payoff_crash -= 10;
			this.payoff_pass += 10;
		}
=======
	public String Type;

	
	public Car(String type, Boolean gender, int age) {
		super(gender, age);
		super.V_type="Car";
		this.Type = type;
>>>>>>> 815d809a4a3738d0aa5959e36160081af94d5095
	}

}
