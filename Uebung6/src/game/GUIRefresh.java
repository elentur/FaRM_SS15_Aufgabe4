package game;

import javafx.application.Platform;

public class GUIRefresh implements Runnable{
private Control ctrl;
	public GUIRefresh(Control ctrl){
		this.ctrl = ctrl;
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					ctrl.refreshColor();
					ctrl.setText();
				}
				
			});
		}
		
		
	}

}
