package view;


import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import model.entity.Vehicle;

import java.util.Iterator;
import java.util.List;

public class GeneralView {

    private static volatile GeneralView generalView;

    private GeneralView() {

        System.out.println("create ..General View");
    }

    public static GeneralView getInstance() {
        if (generalView == null) {
            synchronized (GeneralView.class) {
                if (generalView == null) {
                    generalView = new GeneralView();
                }
            }
        }
        return generalView;
    }


     public Group itemCreate(Vehicle v){


        Image img = new Image("file:"+MainView.path+"\\images\\"+v.getImg());
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(100);
        imgView.setFitWidth(200);

        VBox vBox = new VBox();
        Button detailButton = new Button("Look Details");
        Label status = new Label("Status:"+v.getStatus());
        Label type = new Label("Type:"+v.getType());
        Label seats = new Label("Seats:"+v.getSeats());
        Line line = new Line();
        line.setStartX(0);
        line.setStartY(105);
        line.setEndX(400);
        line.setEndY(105);

        // Set Button Action
        detailButton.setOnAction(actionEvent-> SingleVehicleView.show(v.getId()));


        vBox.getChildren().addAll(status,type,seats,detailButton);
        vBox.setLayoutX(220);

        // create Group named item
        Group item = new Group();
        item.getChildren().addAll(imgView,vBox,line);

        return item;

    }

     public Group listCreate(List<Vehicle> list){

        // create  itemGroups
        Group groups = new Group();
        Iterator it= list.iterator();
        int count = 0;
        while(it.hasNext()){
            Vehicle v = (Vehicle)it.next();
            Group item = GeneralView.getInstance().itemCreate(v);
            item.setLayoutY(count*109);
            groups.getChildren().add(item);
            count++;
        }
        return groups;
    }
}
