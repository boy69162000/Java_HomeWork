package chinesecode;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public class TestTrans extends Application {
    
	String path = new String("C:/Users/admin/workspace/chinesecode/src/chinesecode/CHINESECODE");
	Table t = null;
	FileChooser fc = new FileChooser();

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("NHC&DOS Converter");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 25, 25, 25));

        /*Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);*/

        Button btn3 = new Button("Open File...");
        grid.add(btn3, 0, 0);

        TextField FileTextField = new TextField();
        FileTextField.setText(path);
        grid.add(FileTextField, 1, 0);

        Button btn4 = new Button("Build Table");
        grid.add(btn4, 1, 1);
        
        final Text buildmsg = new Text();
        grid.add(buildmsg, 0, 1);
        
        Label DOSLabel = new Label("DOS:");
        grid.add(DOSLabel, 0, 2);

        TextField DOSTextField = new TextField();
        grid.add(DOSTextField, 1, 2);

        Label NHCLabel = new Label("NHC:");
        grid.add(NHCLabel, 0, 3);

        TextField NHCTextField = new TextField();
        grid.add(NHCTextField, 1, 3);
        
        Button btn1 = new Button("DOS to NHC");
        Button btn2 = new Button("NHC to DOS");
        grid.add(btn1, 0, 4);
        grid.add(btn2, 1, 4);
        //grid.setGridLinesVisible(true);
        
        final Text convertmsg = new Text();
        grid.add(convertmsg, 1, 5);
        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	String DOSs = DOSTextField.getText();
            	if (t == null) {
            		convertmsg.setFill(Color.FIREBRICK);
            		convertmsg.setText("Table not found");
            	}
            	else if (t.convertStringToWord(DOSs) >= 0) {
            		NHCTextField.setText(t.convertDOS2NHC(DOSs));
            		convertmsg.setFill(Color.BLACK);
            		convertmsg.setText("DOS to NHC");
            	}
            	else {
            		convertmsg.setFill(Color.FIREBRICK);
            		convertmsg.setText("Invalid DOS value!");
            	}
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent e) {
            	String NHCs = NHCTextField.getText();
            	if (t == null) {
            		convertmsg.setFill(Color.FIREBRICK);
            		convertmsg.setText("Table not found");
            	}
            	else if (t.convertStringToWord(NHCs) >= 0) {
            		DOSTextField.setText(t.convertNHC2DOS(NHCs));
            		convertmsg.setFill(Color.BLACK);
            		convertmsg.setText("NHC to DOS");
            	}
            	else {
            		convertmsg.setFill(Color.FIREBRICK);
            		convertmsg.setText("Invalid NHC value!");
            	}
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e) {
            	 path = fc.showOpenDialog(primaryStage).getAbsolutePath();
            	 FileTextField.setText(path);
            }
        });
        btn4.setOnAction(new EventHandler<ActionEvent>() {
         	 
            @Override
            public void handle(ActionEvent e) {
            	path = FileTextField.getText();
            	File file = new File(path);
            	if (file.isFile() == true) {
            		t = new Table(path);
            		buildmsg.setFill(Color.BLACK);
            		buildmsg.setText("Table builded");
            	}
            	else {
            		buildmsg.setFill(Color.FIREBRICK);
            		buildmsg.setText("Invalid code file!");
            	}
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}

}