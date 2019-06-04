package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import model.entity.Vehicle;
import model.exception.InvalidException;
import model.util.JdbcUtils;
import view.AlertView;

import java.util.regex.Pattern;


public class SaveController implements EventHandler<ActionEvent> {

    private Stage addStage;

    public SaveController(Stage addStage) {
        this.addStage = addStage;
    }

    @Override
    public void handle(ActionEvent e) {


        Scene scene = addStage.getScene();

        TextField yearField = (TextField) (scene.lookup("#year"));
        TextField makeField = (TextField) scene.lookup("#make");
        TextField typeField = (TextField) scene.lookup("#type");
        TextField seatField = (TextField) scene.lookup("#seat");

        String year = yearField.getText();
        String make = makeField.getText().replace(" ", "");
        String type = typeField.getText();
        String seat = seatField.getText();
        try {
            checkValid(year, make, type, seat);
            year = year.replace(" ", "");
            seat = seat.replace(" ", "");
            type = type.replace(" ", "");
            int year1 = Integer.parseInt(year);
            int seats = Integer.parseInt(seat);
            Vehicle v = new Vehicle(year1, seats, make, type);
            JdbcUtils.insertVehicle(v);
            addStage.close();
            AlertView.getInstance().show("Success");

        } catch (InvalidException e1) {
            AlertView.getInstance().show("Exception:\n" + e1.getMessage());

        }

    }

    static void checkValid(String year, String make, String type, String seat) throws InvalidException {
        StringBuffer msg = new StringBuffer();

        if (!Pattern.matches("^\\s*\\d{4}\\s*$", year))
            msg.append("Invalid Year!\n");

        if (make.replace(" ", "").length() == 0)
            msg.append("Make can not be empty.\n");

        if (!Pattern.matches("\\s*^(Car)|(Van)$\\s*", type)) {

            msg.append("Type must be 'Car' or 'Van'.\n");

        } else {
            // Car
            if (Pattern.matches("\\s*^(Car)$\\s*", type)) {

                if (!Pattern.matches("\\s*^(4)|(7)$\\s*", seat))
                    msg.append("Car's seats must be 4 or 7.\n");

            }
            // Van
            else {
                if (!Pattern.matches("^(15)$", seat))
                    msg.append("Van's seats must be 15 .\n");
            }
        }

        if (msg.length() > 0) {
            msg.append("Please try again");
            throw new InvalidException(msg.toString());
        }
    }

}



