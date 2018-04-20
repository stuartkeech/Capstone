import java.util.concurrent.locks.ReentrantLock;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Encoder {
	
	private boolean full_pins;
	private GpioController gpio;
	private GpioPinDigitalInput Apin;
	private GpioPinDigitalInput Bpin;
	private GpioPinListenerDigital listener;
	
	private long prev_time;
	private long curr_time;
	private double velocity;
	
	private ReentrantLock mux = new ReentrantLock();
	
	public Encoder(Pin A_pin, Pin B_pin) {
		gpio = GpioFactory.getInstance();
		
		listener  = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }
        };
		
        Apin = gpio.provisionDigitalInputPin(A_pin, "A", PinPullResistance.PULL_DOWN);
        Bpin = gpio.provisionDigitalInputPin(B_pin, "B", PinPullResistance.PULL_DOWN);
        full_pins = true;
	}
	
	public Encoder(Pin A_pin) {
		gpio = GpioFactory.getInstance();
		
		prev_time = System.nanoTime();
		
		listener  = new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            	curr_time = System.nanoTime();
            	
            	// If longer than 200ms has passed, we can assume it's not moving.
            	mux.lock();
            	try {
            		if (curr_time-prev_time > 1000000L*20) {
                		velocity = 0;
                	} else velocity = 1;
            	} finally {
            		mux.unlock();
            	}
            	
            	prev_time = curr_time;
            }
        };
		
        Apin = gpio.provisionDigitalInputPin(A_pin, "A", PinPullResistance.PULL_DOWN);
        full_pins = false;
	}
	
	public void destruct() {
		gpio.shutdown();
	}

	// Note: this will be 1 or 0 (on or off) if you only use one pin
	public double getVelocity() {
		double my_vel;
		mux.lock();
		try {
			my_vel = velocity;
		} finally {
			mux.unlock();
		}
		return my_vel;
	}
}