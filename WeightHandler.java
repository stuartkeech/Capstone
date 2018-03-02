package capstone;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class WeightHandler {
	
	private long OFFSET = 0;
	private long SCALE = 1;
	private final int GAIN = 1;  // 128 => 1; 64 => 3; 32 => 2
	private final int AVG_TIMES = 3;
	
	private GpioController gpio;
	private GpioPinDigitalInput dout;
	private GpioPinDigitalOutput sck;

	public void main(String[] args) throws Exception {
		WeightHandler handler = new WeightHandler();
		while(true) {
			System.out.println(handler.getMass());
			Thread.sleep(500);
		}
	}
	
	public WeightHandler() {
		gpio = GpioFactory.getInstance();
		
        sck = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "PinSCK", PinState.LOW);
        dout = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, "PinDOUT", PinPullResistance.PULL_DOWN);
        
        sck.setState(PinState.LOW);
	}
	
	public void destruct() {
		gpio.shutdown();
	}
	
	private long getMass() {
		long val = getAvg();
		return (val-OFFSET)/SCALE;
	}
	
	private long getAvg() {
		long val = 0;
		for (int i = 0; i < AVG_TIMES; i++) {
			val += read();
		}
		return val/AVG_TIMES;
	}
	
	private void tare() {
		OFFSET = getAvg();
		return;
	}
	
	private long read() {
		//Wait for sensor to be ready
		while (dout.getState() == PinState.HIGH);
				
		int[] bytes = new int[3];
		int filler = 0;
		long weight = 0;
		
		for (int i = 2; i >= 0; i--) {
			for (int j = 7; j >= 0; j--) {
				sck.setState(PinState.HIGH);
				if (dout.getState() == PinState.HIGH) {
					bytes[i] += 2^j;
				} 
				sck.setState(PinState.LOW);
			}
		}
		
		for (int i = 0; i < GAIN; i++) {
			sck.setState(PinState.HIGH);
			sck.setState(PinState.LOW);
		}
		
		if ((bytes[2] & 128) != 0) {  //if (bytes[2] & 128)
			filler = 255;
		} else {
			filler = 0;
		}
		
		weight = ((long)filler << 24  | (long)bytes[2] << 16 | (long)bytes[1] << 8 | (long)bytes[0]);
		
		return weight;
	}
}
