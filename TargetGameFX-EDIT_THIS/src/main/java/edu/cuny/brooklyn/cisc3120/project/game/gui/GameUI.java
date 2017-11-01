// importting packeges
package edu.cuny.brooklyn.cisc3120.project.game.gui;

import edu.cuny.brooklyn.cisc3120.project.game.GameBoard;

import edu.cuny.brooklyn.cisc3120.project.game.ShootingPane;

import edu.cuny.brooklyn.cisc3120.project.game.Shot;
import edu.cuny.brooklyn.cisc3120.project.game.Target;
import edu.cuny.brooklyn.cisc3120.project.game.TargetGame.PostShotAction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameUI {
    final static String APP_TITLE = "CISC 3120 Fall 2017: TargetGame";
    
    private final static int PADDING = 20;
    private final static int INIT_TARGET_CANVAS_WIDTH = 400;
    private final static int INIT_TARGET_CANVAS_HEIGHT = 400;
    private final static int INIT_MAIN_SCENE_WIDTH = 600;
    private final static int INIT_MAIN_SCENE_HEIGHT = 500;
    private final static int MAX_TRIES =5; 
    
    private Stage primaryStage;
    private GameBoard gameBoard;
    private ShootingPane gamePane;
    private Canvas targetCanvas;
    private Target target;
    private EventHandler<ActionEvent> shootingActionHandler;
    private PostShotAction postShotAction;
    
    public GameUI(GameBoard board, Stage stage, ShootingPane pane) {
        gameBoard = board;
        primaryStage = stage;
        gamePane = pane;

        HBox hboxShooting = buildKeyboardInputBox();
        
        HBox hboxMain = buildMainBox();

        VBox vboxMain = new VBox();
        vboxMain.setPadding(new Insets(PADDING));
        vboxMain.getChildren().addAll(hboxMain, hboxShooting);
        
        Scene scene = new Scene(vboxMain, INIT_MAIN_SCENE_WIDTH, INIT_MAIN_SCENE_HEIGHT);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("stylesheet.css");
    }
    
    public void addTargetToUI(Target target) {
        this.target = target;
        double width = targetCanvas.getWidth();
        double height = targetCanvas.getHeight();
        double cellWidth = width / gameBoard.getWidth();
        double cellHeight = height / gameBoard.getHeight();
        double xPos = cellWidth * target.getX();
        double yPos = cellHeight * target.getY();
        
        GraphicsContext gc = targetCanvas.getGraphicsContext2D();
        gc.setFill(target.getColor());
        gc.fillRect(xPos, yPos, cellWidth, cellHeight);
    }
    
    
    public void show() {
        primaryStage.show();
    }

    public void setPostShotActionHandler(PostShotAction postShotAction) {
        this.postShotAction = postShotAction;
    }
    
    TextField xGuessedTextField;
    TextField yGuessedTextField;
    
    private HBox buildKeyboardInputBox() {
    	xGuessedTextField = new TextField(Integer.toString((int)gameBoard.getWidth()/2));
        xGuessedTextField.setOnMouseClicked((MouseEvent e) -> {xGuessedTextField.selectAll();});
        yGuessedTextField = new TextField(Integer.toString((int)gameBoard.getHeight()/2));
        yGuessedTextField.setOnMouseClicked((MouseEvent e) -> {yGuessedTextField.selectAll();});
        Button btnShoot = new Button("Shoot!");
        postShotAction = null;
        shootingActionHandler = (ActionEvent e) -> {
            int shotX = Integer.parseInt(xGuessedTextField.getText());
            int shotY = Integer.parseInt(yGuessedTextField.getText());
            Shot shot = new Shot(shotX, shotY);
            handleShotAction(target, shot);
        };
        btnShoot.setOnAction(shootingActionHandler);
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(PADDING));
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.getChildren().addAll(xGuessedTextField, yGuessedTextField, btnShoot);
        
        return hbox;
    }
    
    private VBox buildSideBar() {
        VBox vboxSideBar = new VBox();
        StackPane shootingPane = gamePane.buildShootingPane();
        VBox vboxStatistics = buildStatisticsBox();
        vboxSideBar.setPadding(new Insets(0, 0, PADDING, PADDING));
        vboxSideBar.getChildren().addAll(shootingPane, vboxStatistics);
         vboxSideBar
        return vboxSideBar;
         
         private HBox buildMainBox() {
             targetCanvas = new Canvas(INIT_TARGET_CANVAS_WIDTH, INIT_TARGET_CANVAS_HEIGHT);
             StackPane canvasHolder = new StackPane();
             canvasHolder.setMaxWidth(Double.MAX_VALUE);
             canvasHolder.setBackground(new Background(new BackgroundFill(Color.WHITE, 
                     CornerRadii.EMPTY , Insets.EMPTY)));
             canvasHolder.getChildren().add(targetCanvas);
             
             VBox vboxSideBar = buildSideBar();
             
             HBox hbox = new HBox();
             hbox.getChildren().addAll(canvasHolder, vboxSideBar);
             
             return hbox;
         }
         
        	double spacing=80;
    	
    	 	VBox vboxSideBar = new VBox(spacing);
	    StackPane shootingPane = buildShootingPane();
	    StackPane statisticsbox = buildStatisticsBox(); //lets see how this works w.o the statistics box
	    vboxSideBar.setPadding(new Insets(0, 0, PADDING, PADDING));
       vboxSideBar.getChildren().addAll(shootingPane, statisticsbox); //, vboxStatistics
       return vboxSideBar;
    }
    
    private double CANVAS_WIDTH = 0;
    private double CANVAS_HEIGHT = 0;
    
    private StackPane buildShootingPane() {
    	final double CANVAS_WIDTH = gameBoard.getWidth() * 3;
    	final double CANVAS_HEIGHT = gameBoard.getHeight() * 3;
        Canvas shootingCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        StackPane canvasHolder = new StackPane();
        canvasHolder.setMaxWidth(Double.MAX_VALUE);
        canvasHolder.setBackground(new Background(new BackgroundFill(Color.WHITE, 
                CornerRadii.EMPTY , Insets.EMPTY)));

          addMouseMotionListener(shootingCanvas);

       canvasHolder.getChildren().add(shootingCanvas);
        return canvasHolder;
    }
    
    private StackPane buildStatisticsBox() {
    	final double CANVAS_WIDTH = gameBoard.getWidth() * 3;
    	final double CANVAS_HEIGHT = gameBoard.getHeight() * 3;
        Canvas shootingCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        StackPane canvasHolder = new StackPane();
        canvasHolder.setMaxWidth(Double.MAX_VALUE);
        canvasHolder.setBackground(new Background(new BackgroundFill(Color.WHITE, 
                CornerRadii.EMPTY , Insets.EMPTY)));


       canvasHolder.getChildren().add(shootingCanvas);
        return canvasHolder;
    }

    private void addMouseMotionListener(final Canvas cv){
    	Cross cross = new Cross(INIT_TARGET_CANVAS_WIDTH, INIT_TARGET_CANVAS_HEIGHT);
    	GraphicsContext gc = cv.getGraphicsContext2D();
    	EventHandler<MouseEvent> mouseMovedEventHandler = (MouseEvent event)->{
        	cross.setPos(event.getX(),event.getY());
        	cross.draw(gc);
        	String msg =
        			"(x: "       + event.getX()      + ", y: "       + event.getY()       + ") -- " +
        					"(sceneX: "  + event.getSceneX() + ", sceneY: "  + event.getSceneY()  + ") -- " +
        					"(screenX: " + event.getScreenX()+ ", screenY: " + event.getScreenY() + ")";
       	// System.out.println(msg);     
        };
    	cv.setOnMouseMoved(mouseMovedEventHandler);
    	EventHandler<MouseEvent> mouseClickedEventHandler = (MouseEvent event)->{
    		xGuessedTextField.setText(Integer.toString((int)event.getX()/3));
     	yGuessedTextField.setText(Integer.toString((int)event.getY()/3));
    		String msg = "(x: "       + event.getX()/3      + ", y: "       + event.getY()/3       + ")" ;
    		System.out.println("Mouse clicked at "+msg);
    	};
	    cv.setOnMouseClicked(mouseClickedEventHandler);
    }

    private HBox buildMainBox() {
        targetCanvas = new Canvas(INIT_TARGET_CANVAS_WIDTH, INIT_TARGET_CANVAS_HEIGHT);
        StackPane canvasHolder = new StackPane();
        canvasHolder.setMaxWidth(Double.MAX_VALUE);
        canvasHolder.setBackground(new Background(new BackgroundFill(Color.WHITE, 
                CornerRadii.EMPTY , Insets.EMPTY)));
        canvasHolder.getChildren().add(targetCanvas);
        
        VBox vboxSideBar = buildSideBar();
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(canvasHolder, vboxSideBar);
        
        return hbox;
    }
    
    private HBox getStatRow(String text, Text data){
    	HBox hbox = new HBox();
    	Text label = new Text(text);
    	label.getStyleClass().add("label");
    	data.getStyleClass().add("data");
    	hbox.getChildren().addAll(label, data);
    	return hbox;
    }
    Text wonCountText;
    Text lossCountText;
    Text avgAttCountText;
    Text minAttCountText;
    Text maxAttCountText;
    private VBox buildStatisticsBox() {
    	Text labelStats = new Text("GAME STATISTICS:");
    	labelStats.setId("header");
    	Text labelBlank = new Text("");
    	wonCountText = new Text("0");
    	HBox wonHbox = getStatRow("Won:",wonCountText);
    	lossCountText = new Text("0");
    	HBox lossHbox = getStatRow("Lost:",lossCountText);
    	avgAttCountText = new Text("N/A");
    	HBox avgAttHbox = getStatRow("Average Attempts:",avgAttCountText);
    	minAttCountText = new Text("N/A");
    	HBox minAttHbox = getStatRow("Min Attempts Per Round:",minAttCountText);
    	maxAttCountText = new Text("N/A");
    	HBox maxAttHbox = getStatRow("Max Attempts Per Round:",maxAttCountText);
    	VBox vbox = new VBox();
    	vbox.setPadding(new Insets(PADDING, 0, 0, 0));
    	vbox.getChildren().addAll(labelStats,labelBlank,wonHbox,lossHbox,avgAttHbox,minAttHbox,maxAttHbox);

    	return vbox;
    }
    
    private void clearTarget() {
        double width = targetCanvas.getWidth();
        double height = targetCanvas.getHeight();
        double cellWidth = width / gameBoard.getWidth();
        double cellHeight = height / gameBoard.getHeight();
        double xPos = cellWidth * target.getX();
        double yPos = cellHeight * target.getY();
        
        GraphicsContext gc = targetCanvas.getGraphicsContext2D();
        gc.clearRect(xPos, yPos, cellWidth, cellHeight);
        
    }
    
    private int tries = 0;
    private int won = 0;
    private int lost = 0;
    private int avgAttempt = 0;
    private int minAttempt = MAX_TRIES;
    private int maxAttempt = 0;
    private void handleShotAction(Target target, Shot shot) {
    	tries++;
        if (target.isTargetShot(shot)) {
        	calculateWin();
            Alert alert = new Alert(AlertType.INFORMATION, "You shot the target!", ButtonType.CLOSE);
            alert.setTitle(APP_TITLE + ":Target Shot");
            alert.setHeaderText("Shot!");
            alert.showAndWait();
        	tries = 0; // reset tries
            clearTarget();
            if (postShotAction != null)  {
                postShotAction.postAction();
            }                
        } else {
            Alert alert = new Alert(AlertType.INFORMATION, "Missed!", ButtonType.CLOSE);
        	if (tries >= MAX_TRIES){
        		calculateLoss();
                alert.setTitle(APP_TITLE + ":Game Lost");
                alert.setHeaderText("You missed the target! Starting again !!");
            	tries = 0; // reset tries
                clearTarget();
                if (postShotAction != null)  {
                    postShotAction.postAction();
                }
        	}else{
                alert.setTitle(APP_TITLE + ":Target Missed");
                alert.setHeaderText("You missed the target!Tries Remaining:"+(MAX_TRIES - tries)); 
        	}
               
            alert.showAndWait();
        }
   }
    private void calculateWin(){
    	won ++;
    	wonCountText.setText(Integer.toString(won));
    	minAttempt = minAttempt > tries ? tries: minAttempt;
    	minAttCountText.setText(Integer.toString(minAttempt));
    	maxAttempt = maxAttempt < tries ? tries: maxAttempt;
    	maxAttCountText.setText(Integer.toString(maxAttempt));
    }
    private void calculateLoss(){
		lost ++;
		lossCountText.setText(Integer.toString(lost));
    }
}

}
