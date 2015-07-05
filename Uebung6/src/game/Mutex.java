package game;

import java.io.Serializable;

public class Mutex implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static boolean mutex=false;

public static void setMutex(boolean mutex1) {
	mutex = mutex1;
	try {
		Thread.sleep(50);
	} catch (InterruptedException e) {}
}

}
