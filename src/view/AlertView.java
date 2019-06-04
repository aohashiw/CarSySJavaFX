package view;

import controller.DialogCancelController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AlertView {


    private static volatile AlertView alertView;

    private Stage alertStage;



    private AlertView() {
        Image logo = new Image("file:"+MainView.path+"\\images\\logo.png");
        this.alertStage = new Stage();
        alertStage.setTitle("Alert");
        alertStage.getIcons().add(logo);
        System.out.println("create ..Alert View");
    }

    public static AlertView getInstance() {
        if (alertView == null) {
            synchronized (GeneralView.class) {
                if (alertView == null) {
                    alertView = new AlertView();
                }
            }
        }
        return alertView;
    }

    public  void show(String msg) {

        AlertView a = AlertView.getInstance();

        Stage alertStage = a.alertStage;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));


        Text Msg = new Text(msg);
        Msg.setWrappingWidth(250);
        Button exitButton = new Button(" Ok ");
        HBox hBox = new HBox();
        hBox.getChildren().add(exitButton);
        hBox.setAlignment(Pos.CENTER);


        //Set Action
        exitButton.setOnAction( new DialogCancelController(alertStage));

        root.setCenter(Msg);
        root.setBottom(hBox);
        Scene scene = new Scene(root,250,200);
        alertStage.setScene(scene);
        alertStage.show();
    }

}
