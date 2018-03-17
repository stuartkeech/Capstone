package capstone;

//import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.SoftPwm;

public class EndEffectorControl {
	private static final int PIN = 3;
	//GPIO_03 => pin 15
	public static void main(String[] args) throws Exception{
		com.pi4j.wiringpi.Gpio.wiringPiSetup();
		SoftPwm.softPwmCreate(PIN, 0, 100);
		
		Thread.sleep(1000);
		SoftPwm.softPwmWrite(PIN, 12);
		System.out.println("Connect your power");
	    System.in.read();
	    
	    SoftPwm.softPwmWrite(PIN, 6);
		Thread.sleep(1000);
		SoftPwm.softPwmWrite(PIN, 0);
		
		System.out.println("Arming");
	    System.in.read();
	    Thread.sleep(1000);
	    
	    SoftPwm.softPwmWrite(PIN, 6);
	    Thread.sleep(1500);
	    SoftPwm.softPwmWrite(PIN, 0);
		
		SoftPwm.softPwmStop(PIN);
		
/*		GpioController gpio = GpioFactory.getInstance();
		GpioPinPwmOutput endEffector = gpio.provisionSoftPwmOutputPin(RaspiPin.GPIO_03);
		endEffector.setPwmRange(100);
		
		Thread.sleep(1000);
        
		endEffector.setPwm(12);
		System.out.println("Connect your power");
	    System.in.read();
		
	    endEffector.setPwm(6);
		Thread.sleep(1000);
		endEffector.setPwm(0);
		
		System.out.println("Arming");
	    System.in.read();
	    Thread.sleep(1000);
	    
	    endEffector.setPwm(6);
	    Thread.sleep(1500);
	    endEffector.setPwm(0);
	    
	    gpio.shutdown();*/
	}
	
/*	public EndEffectorControl() {
		Gpio.wiringPiSetup();
		SoftPwm.softPwmCreate(PIN, 0, 100);
	}
	
	public void startEndEffector() {
		SoftPwm.softPwmWrite(PIN, SPEED);
	}
	
	public void stopEndEffector() {
		SoftPwm.softPwmWrite(PIN, 0);
	}*/
}