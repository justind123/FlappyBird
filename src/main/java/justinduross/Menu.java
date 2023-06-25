package justinduross;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Menu extends Application {

    private Stage stage;
    private String backgroundPath = "/images/background.png";
    private String titlePath = "/images/title.png";
    private String floorPath = "/images/floor.png";
    private String playButtonPath = "/images/play_button.png";

    private int HEIGHT = 700;
    private int WIDTH = 400;



    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Pane root = new Pane();
        ImageView bgView = getBackground();
        ImageView titleView = getTitle();
        ImageView floorView = getFloor();
        ImageView playButtonView = getPlayButton();

        root.getChildren().addAll(bgView, titleView, floorView, playButtonView);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                playButtonView.setScaleY(-1);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                playButtonView.setScaleY(1);
                startGame();
            }
        });

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Flappy Bird");
        stage.show();
    }

    private void startGame() {
        Game game = new Game();
        try {
            game.start(stage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageView getBackground() {
        ImageView bgImageView = new ImageView(new Image(getClass().getResource(backgroundPath).toExternalForm()));
        bgImageView.setFitWidth(WIDTH);
        bgImageView.setFitHeight(HEIGHT);

        return bgImageView;
    }

    private ImageView getTitle() {
        ImageView titleView = new ImageView(new Image(getClass().getResource(titlePath).toExternalForm()));
        titleView.setPreserveRatio(true);
        titleView.setFitWidth(250);
        titleView.setTranslateX((WIDTH - titleView.getFitWidth()) / 2);
        titleView.setTranslateY(50);

        return titleView;
    }

    private ImageView getFloor() {
        ImageView floorView = new ImageView(new Image(getClass().getResource(floorPath).toExternalForm()));
        floorView.setPreserveRatio(true);
        floorView.setFitHeight(200);
        floorView.setTranslateY(HEIGHT - 150);
        
        return floorView;
    }

    private ImageView getPlayButton() {
        ImageView playButtonView = new ImageView(new Image(getClass().getResource(playButtonPath).toExternalForm()));
        playButtonView.setPreserveRatio(true);
        playButtonView.setFitWidth(200);
        playButtonView.setTranslateX((WIDTH - playButtonView.getFitWidth()) / 2);
        playButtonView.setTranslateY(250);

        playButtonView.setOnMousePressed(event -> {
            System.out.println("mouse pressed");
            playButtonView.setScaleY(-1);
        });

        playButtonView.setOnMouseReleased(event -> {
            playButtonView.setScaleY(1);
            startGame();
        });

        playButtonView.setOnKeyPressed(event -> {
            System.out.println(event);
        });

        return playButtonView;
    }

    public static void main(String[] args) {
        launch();
    }

}