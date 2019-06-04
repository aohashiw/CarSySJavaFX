package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Stage;



public class DialogCancelController implements EventHandler<ActionEvent>  {
		
	private Stage dialogBox;
	
	public DialogCancelController(Stage dialogBox) {
		this.dialogBox = dialogBox;
	}
	
	@Override // Override the handle method
	public void handle(ActionEvent e) {
		System.out.println("Cancel!");
		dialogBox.close();
	}
	
}