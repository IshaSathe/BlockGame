import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;

public class GameScreen extends Application {

	Label newShapeLabel, space, space2, workspaceLabel, warning;
	Label boxListLabel, sphereListLabel, cylinderListLabel;
	TextField boxX, boxY, boxZ, sphereR, cylinderR, cylinderH;
	Button newBox, newSphere, newCylinder, clear;
	ComboBox<String> boxes, spheres, cylinders;
	ComboBox<String> boxColor, sphereColor, cylinderColor;
	Pane workspace;
	int WIDTH = 1125;
	int HEIGHT = 800;
	//SmartBox box;
	ArrayList<SmartBox> boxList = new ArrayList<SmartBox>();
	ArrayList<SmartSphere> sphereList = new ArrayList<SmartSphere>();
	ArrayList<SmartCylinder> cylinderList = new ArrayList<SmartCylinder>();
	int z = 0;
	int boxCount = 0, sphereCount = 0, cylinderCount = 0;
	Scene scene;
	Camera cam;
	
	public void start(Stage primaryStage) {
		 Stage secondaryStage = new Stage();
		
		newShapeLabel = new Label("New Shapes: ");
		warning = new Label("");
		newBox = new Button("Create New Box!");
		newBox.setPrefWidth(200);
		boxX = new TextField("length");
		boxY = new TextField("width");
		boxZ = new TextField("depth");
		newSphere = new Button("Create New Sphere!");
		newSphere.setPrefWidth(200);
		sphereR = new TextField("radius");
		newCylinder = new Button("Create New Cylinder");
		newCylinder.setPrefWidth(200);
		cylinderR = new TextField("radius");
		cylinderH = new TextField("height");
		boxListLabel = new Label("Chose A Box To Manuver");
		sphereListLabel = new Label("Chose A Sphere To Manuver");
		cylinderListLabel = new Label("Chose A Cylinder To Manuver");
		boxes = new ComboBox<String>();
		boxes.getItems().add("select");
		boxes.getSelectionModel().selectFirst();
		boxes.setPrefWidth(200);
		spheres = new ComboBox<String>();
		spheres.getItems().add("select");
		spheres.getSelectionModel().selectFirst();
		spheres.setPrefWidth(200);
		cylinders = new ComboBox<String>();
		cylinders.getItems().add("select");
		cylinders.getSelectionModel().selectFirst();
		cylinders.setPrefWidth(200);
		boxColor = new ComboBox<String>();
		boxColor.getItems().addAll("Color", "Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink");
		boxColor.getSelectionModel().selectFirst();
		boxColor.setPrefWidth(200);
		sphereColor = new ComboBox<String>();
		sphereColor.getItems().addAll("Color", "Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink");
		sphereColor.getSelectionModel().selectFirst();
		sphereColor.setPrefWidth(200);
		cylinderColor = new ComboBox<String>();
		cylinderColor.getItems().addAll("Color", "Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Pink");
		cylinderColor.getSelectionModel().selectFirst();
		cylinderColor.setPrefWidth(200);
		clear = new Button("Clear Workspace");
		clear.setPrefWidth(200);
		space = new Label("");
		space2 = new Label("");
		
		GridPane controlPanel = new GridPane();
		controlPanel.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		controlPanel.setHgap(20);
		controlPanel.setVgap(4);
		controlPanel.setPadding(new Insets(10, 10, 10, 10));
		controlPanel.add(newShapeLabel, 0, 0);
		controlPanel.add(boxX, 0, 1);
		controlPanel.add(boxY, 0, 2);
		controlPanel.add(boxZ, 0, 3);
		controlPanel.add(boxColor, 0, 4);
		controlPanel.add(newBox, 0, 5);
		controlPanel.add(space,  0,  6);
		controlPanel.add(sphereR, 0, 7);
		controlPanel.add(sphereColor, 0, 8);
		controlPanel.add(newSphere, 0, 9);
		controlPanel.add(space2,  0,  10);
		controlPanel.add(cylinderR, 0, 11);
		controlPanel.add(cylinderH, 0, 12);
		controlPanel.add(cylinderColor, 0, 13);
		controlPanel.add(newCylinder, 0, 14);
		controlPanel.add(warning, 0, 15);
		controlPanel.add(boxListLabel, 1, 1);
		controlPanel.add(boxes, 1, 2);
		controlPanel.add(sphereListLabel, 1, 7);
		controlPanel.add(spheres, 1, 8);
		controlPanel.add(cylinderListLabel, 1, 10);
		controlPanel.add(cylinders, 1, 11);
		controlPanel.add(clear,  1, 15);
		
		newBox.setOnAction(new CreateShapes());
		newSphere.setOnAction(new CreateShapes());
		newCylinder.setOnAction(new CreateShapes());
		clear.setOnAction(new Clear());
		
		workspace = new Pane();
		
		
		
		BorderPane root = new BorderPane();
		//root.setTop(controlPanel);
		root.setCenter(workspace);
		root.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));

		
		
		Scene scene2 = new Scene(controlPanel, 400, HEIGHT);
		scene2.setFill(Color.BLUE);
		
		
		scene = new Scene(root, WIDTH, HEIGHT);
		scene.setFill(Color.BLUE);
		
		System.out.println("key event applied");
		
		
		boxes.setOnAction(new ShapeLists());
		spheres.setOnAction(new ShapeLists());
		cylinders.setOnAction(new ShapeLists());
		
		cam = new PerspectiveCamera();
		scene.setCamera(cam);
		
		primaryStage.setTitle("Workspace");
        primaryStage.setScene(scene);
        primaryStage.setX(-5);
        primaryStage.setY(0);
        primaryStage.show();
        secondaryStage.setTitle("Control Panel");
        secondaryStage.setScene(scene2);
        secondaryStage.setX(WIDTH);
        secondaryStage.setY(0);
        secondaryStage.show();
		
	}
	
	private class CreateShapes implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			
			warning.setText("");
			
			if(event.getSource() == newBox) {
				try {
					int x = Integer.parseInt(boxX.getText());
					int y = Integer.parseInt(boxY.getText());
					int z = Integer.parseInt(boxZ.getText());
					SmartBox box = new SmartBox();
					box.setHeight(y);
					box.setWidth(x);
					box.setDepth(z);
					box.translateXProperty().set(WIDTH/2);
			    	box.translateYProperty().set(HEIGHT/2);
			    	
			    	PhongMaterial material;
			    	switch(boxColor.getSelectionModel().getSelectedItem()) {
			    	case "Red":
			    		material = new PhongMaterial(Color.ORANGERED);
			    		box.setMaterial(material);
			    		break;
			    	case "Orange":
			    		material = new PhongMaterial(Color.ORANGE);
			    		box.setMaterial(material);
			    		break;
			    	case "Yellow":
			    		material = new PhongMaterial(Color.YELLOW);
			    		box.setMaterial(material);
			    		break;
			    	case "Green":
			    		material = new PhongMaterial(Color.YELLOWGREEN);
			    		box.setMaterial(material);
			    		break;
			    	case "Blue":
			    		material = new PhongMaterial(Color.LIGHTSKYBLUE);
			    		box.setMaterial(material);
			    		break;
			    	case "Purple":
			    		material = new PhongMaterial(Color.VIOLET);
			    		box.setMaterial(material);
			    		break;
			    	case "Pink":
			    		material = new PhongMaterial(Color.LIGHTPINK);
			    		box.setMaterial(material);
			    		break;
			    	default:
			    		box.setMaterial(null);
			    		break;
			    	}
			    	
			    	boxList.add(box);
			    	boxCount++;
			    	boxes.getItems().add("box #" + boxCount);
			    	
					workspace.getChildren().add(box);
					
				} catch(NumberFormatException ex) {
					warning.setText("Please Enter Integer Values");
				}
			}
			
			else if(event.getSource() == newSphere) {
				try {
					int r = Integer.parseInt(sphereR.getText());
					SmartSphere sphere = new SmartSphere();
					sphere.setRadius(r);
					sphere.translateXProperty().set(WIDTH/2);
					sphere.translateYProperty().set(HEIGHT/2);
					
					PhongMaterial material;
			    	switch(sphereColor.getSelectionModel().getSelectedItem()) {
			    	case "Red":
			    		material = new PhongMaterial(Color.ORANGERED);
			    		sphere.setMaterial(material);
			    		break;
			    	case "Orange":
			    		material = new PhongMaterial(Color.ORANGE);
			    		sphere.setMaterial(material);
			    		break;
			    	case "Yellow":
			    		material = new PhongMaterial(Color.YELLOW);
			    		sphere.setMaterial(material);
			    		break;
			    	case "Green":
			    		material = new PhongMaterial(Color.YELLOWGREEN);
			    		sphere.setMaterial(material);
			    		break;
			    	case "Blue":
			    		material = new PhongMaterial(Color.LIGHTSKYBLUE);
			    		sphere.setMaterial(material);
			    		break;
			    	case "Purple":
			    		material = new PhongMaterial(Color.VIOLET);
			    		sphere.setMaterial(material);
			    		break;
			    	case "Pink":
			    		material = new PhongMaterial(Color.LIGHTPINK);
			    		sphere.setMaterial(material);
			    		break;
			    	default:
			    		sphere.setMaterial(null);
			    		break;
			    	}
					
					sphereList.add(sphere);
					sphereCount++;
					spheres.getItems().add("sphere #" + sphereCount);
					
					workspace.getChildren().add(sphere);
					
				} catch(NumberFormatException ex) {
					warning.setText("Please Enter Integer Values");
				}
			}
			
			else if(event.getSource() == newCylinder) {
				try {
					int r = Integer.parseInt(cylinderR.getText());
					int h = Integer.parseInt(cylinderH.getText());
					SmartCylinder cylinder = new SmartCylinder();
					cylinder.setRadius(r);
					cylinder.setHeight(h);
					cylinder.translateXProperty().set(WIDTH/2);
					cylinder.translateYProperty().set(HEIGHT/2);
					
					PhongMaterial material;
			    	switch(cylinderColor.getSelectionModel().getSelectedItem()) {
			    	case "Red":
			    		material = new PhongMaterial(Color.ORANGERED);
			    		cylinder.setMaterial(material);
			    		break;
			    	case "Orange":
			    		material = new PhongMaterial(Color.ORANGE);
			    		cylinder.setMaterial(material);
			    		break;
			    	case "Yellow":
			    		material = new PhongMaterial(Color.YELLOW);
			    		cylinder.setMaterial(material);
			    		break;
			    	case "Green":
			    		material = new PhongMaterial(Color.YELLOWGREEN);
			    		cylinder.setMaterial(material);
			    		break;
			    	case "Blue":
			    		material = new PhongMaterial(Color.LIGHTSKYBLUE);
			    		cylinder.setMaterial(material);
			    		break;
			    	case "Purple":
			    		material = new PhongMaterial(Color.VIOLET);
			    		cylinder.setMaterial(material);
			    		break;
			    	case "Pink":
			    		material = new PhongMaterial(Color.LIGHTPINK);
			    		cylinder.setMaterial(material);
			    		break;
			    	default:
			    		cylinder.setMaterial(null);
			    		break;
			    	}
					
					cylinderList.add(cylinder);
					cylinderCount++;
					cylinders.getItems().add("cylinder #" + cylinderCount);
					
					workspace.getChildren().add(cylinder);
					
				} catch(NumberFormatException ex) {
					warning.setText("Please Enter Integer Values");
				}
			}
		}
		
	}
	
	private class MoveSphere implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
			
			SmartSphere sphereToMove;
			int sphereNum = spheres.getSelectionModel().getSelectedIndex();
			sphereNum--;
			sphereToMove = sphereList.get(sphereNum);
			
			switch(event.getCode()){
			
			case EQUALS:
				sphereToMove.moveZ(-5);
				break;
			case MINUS:
				sphereToMove.moveZ(5);
				break;
			case DOWN:
				sphereToMove.moveY(5);
				break;
			case UP:
				sphereToMove.moveY(-5);
				break;
			case LEFT:
				sphereToMove.moveX(-5);
				break;
			case RIGHT:
				sphereToMove.moveX(5);
				break;
			default:
				break;
				
		}
		}
		
	}
	
	private class MoveBox implements EventHandler<KeyEvent> {

    	public void handle(KeyEvent event) {
    		
    		SmartBox boxToMove; // = new Box();
    		int boxNum = boxes.getSelectionModel().getSelectedIndex();
    		boxNum--;
    		boxToMove = boxList.get(boxNum);
    		
    		switch(event.getCode()) {
		
    		case EQUALS:
    			boxToMove.moveZ(-5);
    			break;
    		case MINUS:
    			boxToMove.moveZ(5);
    			break;
    		case LEFT:
    			boxToMove.moveX(-5);
    			break;
    		case RIGHT:
    			boxToMove.moveX(5);
    			break;
    		case DOWN:
    			boxToMove.moveY(5);
    			break;
    		case UP:
    			boxToMove.moveY(-5);
    			break;
    		case NUMPAD8:
    			boxToMove.rotateByX(-1);
   				break;
    		case NUMPAD2:
   				boxToMove.rotateByX(1);
   				break;
    		case NUMPAD4:
    			boxToMove.rotateByY(1);
    			break;
    		case NUMPAD6:
    			boxToMove.rotateByY(-1);
    			break;
    		case NUMPAD9:
    			boxToMove.rotateByZ(1);
    			break;
    		case NUMPAD7:
    			boxToMove.rotateByZ(-1);
    			break;
			default:
				System.out.println(event.getCode());
				break;
    		}
		
    	}
    }
	
	private class MoveCylinder implements EventHandler<KeyEvent> {

    	public void handle(KeyEvent event) {
    		
    		SmartCylinder cylinderToMove;
    		int cylinderNum = cylinders.getSelectionModel().getSelectedIndex();
    		cylinderNum--;
    		cylinderToMove = cylinderList.get(cylinderNum);
    		
    		switch(event.getCode()) {
		
    		case EQUALS:
    			cylinderToMove.moveZ(-5);
    			break;
    		case MINUS:
    			cylinderToMove.moveZ(5);
    			break;
    		case LEFT:
    			cylinderToMove.moveX(-5);
    			break;
    		case RIGHT:
    			cylinderToMove.moveX(5);
    			break;
    		case DOWN:
    			cylinderToMove.moveY(5);
    			break;
    		case UP:
    			cylinderToMove.moveY(-5);
    			break;
    		case NUMPAD8:
    			cylinderToMove.rotateByX(-1);
   				break;
    		case NUMPAD2:
    			cylinderToMove.rotateByX(1);
   				break;
    		case NUMPAD4:
    			cylinderToMove.rotateByY(1);
    			break;
    		case NUMPAD6:
    			cylinderToMove.rotateByY(-1);
    			break;
    		case NUMPAD9:
    			cylinderToMove.rotateByZ(1);
    			break;
    		case NUMPAD7:
    			cylinderToMove.rotateByZ(-1);
    			break;
			default:
				break;
    		}
		
    	}
    }
	
	private class ShapeLists implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) {
			
			if(event.getSource() == boxes)
				scene.setOnKeyPressed(new MoveBox());
			
			else if(event.getSource() == spheres)
				scene.setOnKeyPressed(new MoveSphere());
			
			else if(event.getSource() == cylinders)
				scene.setOnKeyPressed(new MoveCylinder());
		}
		
	}
	
	private class Clear implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent event) {
			boxes.getItems().clear();
			boxes.getItems().add("select");
			boxes.getSelectionModel().selectFirst();
			boxCount = 0;
			boxList.clear();
			spheres.getItems().clear();
			spheres.getItems().add("select");
			sphereCount = 0;
			sphereList.clear();
			spheres.getSelectionModel().selectFirst();
			cylinders.getItems().clear();
			cylinders.getItems().add("select");
			cylinders.getSelectionModel().selectFirst();
			cylinderCount = 0;
			cylinderList.clear();
			workspace.getChildren().clear();
			
		}
	}

	public static void main(String[] args) { launch(args); }	
}
