# Traffic simulation

This project is a Java program designed to simulate decisions different travelling objects make at the crossings without traffic lights.

## Installation and Usage

You do not need to install this project. The project has been capsulated into a Jar file, so that you can simply run this file to open the project.

## Usage

This project is easy to use and modify. The front end has four main pages, which are welcome, configuration, animation, and summary page. 

### Welcome
In this page, the user can either start a new simulation or load an existing a simulation.

### Configuration
In this page, the user can either start a new simulation or load an existing a simulation.
The configuration page allows user to change the object information in a simulation and adjust the factors of each object.
There are also some paramerters for the simulation that can be adjusted, including speed_factor, crash_factor, patience, etc
. API are provided in ``` car.java, cyclist.java, and walker.java```

* Speed_factor: the lower this factor, the highly payoff will be given, thus the opponent will choose to pass.
Possible range: 0.5-1.5

* Crash_factor: Typically, larger cars, like trucks, will have higher crash_factor as their loss for crash is relatively lower. 
Possible range: 0.5-1.5

* Stop_factor: Typically, male and larger cars will have higher stop_factor.
Possible range: 0.1-2

Below is the detailed entry for adjusting each of these parameters.
```python
private static double stop_factor_basic = 1.0;
private static double speed_factor_basic = 0.9;
private static double crash_factor_basic = 1.0;
private static double carelessRate_basic = 0.7;
private static double caution_changeByAge = 0.02;
private static double weakness_changeByAge = 0.03;
private static double caution_genderDifference = 0.05;
private static double stop_factor_basic = 1.0;
private static double speed_factor_basic = 1.0;
private static double crash_factor_basic = 1.0;
private static double carelessRate_basic = 0.6;
private static double caution_changeByAge = 0.02;
private static double weakness_changeByAge = 0.05;
private static double caution_genderDifference = 0.05;
private static double stop_factor_car_basic = 1.0;
private static double stop_factor_bus_basic = 1.1;
private static double stop_factor_truck_basic = 1.0;
private static double speed_factor_car_basic = 1.0;
private static double speed_factor_bus_basic = 0.85;
private static double speed_factor_truck_basic = 0.8;
private static double crash_factor_car_basic = 0.8;
private static double crash_factor_truck_basic = 1.0;
private static double crash_factor_bus_basic = 1.2;
private static double carelessRate_basic = 0.7;
private static double caution_changeByAge = 0.02;
private static double caution_genderDifference = 0.05;
```
Notice: Some paremeters cannot be changed in the frontend now, but all these API are provided already. In the future, you may include them in the configuration.

You can also change the ```density``` level of the map, altering the maximum number of cars on the map.

You might input object information by your own, or you can randomly generalize object information. Settings can be changed at ```random.java```

At present, the age and object types are simply randomly generated. In the future, you may want to change it to a certain distribution.
```                
str.append((int)(Math.random()*83+18)+"\n");
String object = object_types[(int(Math.random()*3)];
```


### Animation
In this page, you will find the animation for the simulation process. You can find if objects have crashes.

### Summary
At the end of a simulation, we provide a brief report of it. Key information involve:

* Each decision made at each round, with action, outcome, and driver info.
* Total number of crashes in the simulation.
* Statistics on crashes happened in the simulation, including age distribution, gender distribution, people’s preference to make certain actions.

## Sample test file and result
Please find it in the ```test/application```, we have provide a sample 1000 test objects for your you as well as the simulation result.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.


