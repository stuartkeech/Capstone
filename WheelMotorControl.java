package capstone;

import com.pi4j.io.gpio.*;

// The two PWM pins are GPIO26 and GPIO23, pin numbers 32 and 33 respectively.
// The current test code should spin a motor at 50% for 5 seconds, 25% for 5 seconds, then turn off.
public class WheelMotorControl {
	
	//Note: max motor velocity reduced to 90% to account for max current draw of ESCs
	//With the switch to treads, the below max velocity may no longer be accurate.
	//private final double MAX_MOTOR_VELOCITY = 19.77*0.9;  // in m/s
	//private final int MAX_TURN_RADIUS = 20000;			  // in mm
	//private final int DRIVE_STRAIGHT = 100000;		  	  // in mm
	//private final double WHEEL_SEPARATION = 0.2;	 	  // in m
	
	//private int robotRadius;
	//private double robotSpeed;
	
	//private GpioController gpio;
	//private GpioPinPwmOutput motor1pin;
	//private GpioPinPwmOutput motor2pin;
	
	//Just for testing purposes
	public static void main(String[] args) throws Exception {
		GpioController gpio = GpioFactory.getInstance();
		
		GpioPinPwmOutput motor1pin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_26);
		//GpioPinPwmOutput motor2pin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_23);
		
		com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(2000);
        com.pi4j.wiringpi.Gpio.pwmSetClock(192);
		
        Thread.sleep(1000);
        
		//motor1pin.setPwm(240);
		//motor2pin.setPwm(240);
		//System.out.println("Connect your power");
	    //System.in.read();
		
		//motor1pin.setPwm(120);
		//motor2pin.setPwm(120);
		//Thread.sleep(1000);
		//motor1pin.setPwm(0);
		//motor2pin.setPwm(0);
		
		//System.out.println("Arming");
	    //System.in.read();
	    //Thread.sleep(1000);
	    
	    motor1pin.setPwm(140);
	    //motor2pin.setPwm(120);
	    Thread.sleep(1500);
	    motor1pin.setPwm(0);
	    //motor2pin.setPwm(0);
	    
		gpio.shutdown();
		
	}
	
	/*
	public WheelMotorControl() {
		gpio = GpioFactory.getInstance();
		
		motor1pin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_26);
		motor2pin = gpio.provisionPwmOutputPin(RaspiPin.GPIO_23);
		
		com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1000);
        //com.pi4j.wiringpi.Gpio.pwmSetClock(500);
		
		// Make sure motors are turned off to start
		motor1pin.setPwm(0);
		motor2pin.setPwm(0);
		
		// Initialize values to stopped, driving straight
		robotSpeed = 0;
		robotRadius = DRIVE_STRAIGHT;
	}
	
	public void destruct() {
		motor1pin.setPwm(0);
		motor2pin.setPwm(0);
		gpio.shutdown();
	}
	*/
	
	
	/*
	 * The main function used by PathPlanning
	 */
/*	public int drive(double velocity, int radius) {
		// Check that the arguments are within bounds
		if (velocity > MAX_MOTOR_VELOCITY || velocity < 0 ||
				((Math.abs(radius) < WHEEL_SEPARATION || Math.abs(radius) > MAX_TURN_RADIUS) 
						&& radius != DRIVE_STRAIGHT) ) {
			return 1;
		}
		
		// Determine the required velocities of the two motors
		double motor1Speed = 0;
		double motor2Speed = 0;
		
		if (velocity != 0) {  //If it is 0, we don't need to do anything.
			if (radius == DRIVE_STRAIGHT) {
				motor1Speed = velocity;
				motor2Speed = velocity;
			} else if (radius == 0) {
				motor1Speed = velocity;
				motor2Speed = -velocity;
			} else {
				double diff = (WHEEL_SEPARATION*velocity)/(2*radius);
				double check = velocity + Math.abs(diff);
				
				if (check > MAX_MOTOR_VELOCITY) {  //One of the motor velocities will be too high
					if (velocity + diff > velocity - diff) {
						motor1Speed = MAX_MOTOR_VELOCITY;
						motor2Speed = (velocity - diff)*(velocity + diff)/MAX_MOTOR_VELOCITY;
					} else {
						motor2Speed = MAX_MOTOR_VELOCITY;
						motor1Speed = (velocity - diff)*(velocity + diff)/MAX_MOTOR_VELOCITY;
					}
				} else {
					motor1Speed = velocity + diff;
					motor2Speed = velocity - diff;
				}
			}
		}
		
		motorPWM(motor1Speed, 1);
		motorPWM(motor2Speed, 2);
		return 0;
	}
	
	
	
	/*
	 * The function which sets the PWM of the motors based on desired speed
	 * These will be slightly more complex if the motors are allowed to reverse direction
	 */
/*	private void motorPWM(double motorSpeed, int motorNumber) {
				
		int dutyCycle = (int) (motorSpeed*900/MAX_MOTOR_VELOCITY + 0.5);
		
		// We shouldn't need this, but just in case, do some error checking
		if (dutyCycle > 900) dutyCycle = 900;
		else if (dutyCycle < 0) dutyCycle = 0;
		
		if (motorNumber == 1) {
			motor1pin.setPwm(dutyCycle);
		} else if (motorNumber == 2){
			motor2pin.setPwm(dutyCycle);
		}
	}	*/
	
	
	/*
	 * Getters!!!
	 */
/*	public int getRadius() {
		return robotRadius;
	}
	
	public double getSpeed() {
		return robotSpeed;
	}*/
}