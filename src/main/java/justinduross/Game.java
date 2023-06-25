package justinduross;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Game extends Application{

    private Stage stage;

    private String backgroundPath = "/images/background.png";
    private String titlePath = "/images/title.png";
    private String floorPath = "/images/floor.png";
    private String playButtonPath = "/images/play_button.png";

    private int HEIGHT = 700;
    private int WIDTH = 400;

    private double motionTime, elapsedTime;
    private long startTime, spaceClickA;
    private Sprite floor1, floor2;
    private GraphicsContext gc;
    private List<Pipe> pipes = new ArrayList<>();
    private Bird bird;
    private Sprite birdSprite;
    private AnimationTimer timer;
    private int score;
    private boolean hasCollided;


    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Parent root = getRoot();
        Scene gameScene = new Scene(root, WIDTH, HEIGHT);
        gameScene.setOnKeyPressed(event -> {
            if (!hasCollided && event.getCode() == KeyCode.SPACE) {
                //spaceClickA = System.currentTimeMillis();
                birdSprite.setVelocity(0, -350);
            }
        });
        gameScene.setOnMousePressed(event -> {
                //spaceClickA = System.currentTimeMillis();
            if (!hasCollided) {
                birdSprite.setVelocity(0, -350);
            }
        });
        stage.setScene(gameScene);
        stage.show();

        startGame();

    }

    private Group getRoot() {
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        ImageView bgView = getBackground();
        setFloor();
        setPipes();
        setBird();

        root.getChildren().addAll(bgView, canvas);

        return root;
    }

    private void startGame() {
        startTime = System.nanoTime();

        timer = new AnimationTimer() {
            public void handle(long now) {
                //System.out.println(now - startTime);
                elapsedTime = ((now - startTime) / 1_000_000_000.0);
                startTime = now;

                gc.clearRect(0, 0, WIDTH, HEIGHT);
                renderPipes();
                checkPipes();
                moveFloor();
                renderBird();
                updateScore();

                if (checkCollision()) {
                    timer.stop();
                    Menu menu = new Menu();
                    try {
                        menu.start(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                /*motionTime += .18;
                if (motionTime > .5) {
                    birdSprite.addVelocity(-200, 400);
                    birdSprite.render(gc);
                    birdSprite.update(elapsedTime);
                    motionTime = 0;
                }*/
            }
        };

        timer.start();

       
    }

    private void updateScore() {
        for (Pipe pipe : pipes) {
            if (pipe.getPipe().getPositionX() == birdSprite.getPositionX()) {
                score++;
                System.out.println("Score: " + score);
                return;
            }
        }
    }

    private boolean checkCollision() {
        if (birdSprite.intersects(floor1) || birdSprite.intersects(floor2)) {
            hasCollided = true;
            return hasCollided;
        }

        for (Pipe pipe : pipes) {
            if (birdSprite.intersects(pipe.getPipe())) {
                hasCollided = true;
                return hasCollided;
            }
        }

        return false;
    }

    private void animateBird() {
        //birdSprite.render(gc);
        //birdSprite.update(elapsedTime);
        
        motionTime += .18;
        if (motionTime > .6) {
            Sprite temp = birdSprite;
            birdSprite = bird.animate();
            birdSprite.setPosition(temp.getPositionX(), temp.getPositionY());
            birdSprite.setVelocity(temp.getVelocityX(), temp.getVelocityY());
            motionTime = 0;
        }
    }

    private void setBird() {
        bird = new Bird();
        birdSprite = bird.getBird();
        birdSprite.setVelocity(0, 0);
        birdSprite.render(gc);
    }

    private void renderBird() {
        //System.out.println(elapsedTime);
        birdSprite.addVelocity(0, 20);
        birdSprite.update(elapsedTime);
        birdSprite.render(gc);
        animateBird();
        
    }

    private void moveFloor() {
        floor1.update(5);
        floor2.update(5);

        if (floor1.getPositionX() <= -(WIDTH + 200)) {
            floor1.setPosition(floor2.getPositionX() + floor2.getWidth(), HEIGHT - 150);
        } 
        else if (floor2.getPositionX() <= -(WIDTH + 200)) {
            floor2.setPosition(floor1.getPositionX() + floor1.getWidth(), HEIGHT - 150);
        }
        floor1.render(gc);
        floor2.render(gc);
    }

    private ImageView getBackground() {
        ImageView bgImageView = new ImageView(new Image(getClass().getResource(backgroundPath).toExternalForm()));
        bgImageView.setFitWidth(WIDTH);
        bgImageView.setFitHeight(HEIGHT);

        return bgImageView;
    }

    private void setFloor() {
        floor1 = new Sprite();
        floor1.resizeImage(floorPath, 600, 200);
        floor1.setPosition(0, HEIGHT - 150);
        floor1.setVelocity(-.4, 0);

        floor2 = new Sprite();
        floor2.resizeImage(floorPath, 600, 200);
        floor2.setPosition(600, HEIGHT - 150);
        floor2.setVelocity(-.4, 0);
    }

    private void setPipes() {
        int height = (int) (Math.random() * (350 - 25) + 20);
        //System.out.println(height);

        Pipe topPipe = new Pipe(false, height);
        Pipe bottomPipe = new Pipe(true, height);

        topPipe.getPipe().setVelocity(-.4, 0);
        bottomPipe.getPipe().setVelocity(-.4, 0);

        topPipe.getPipe().render(gc);
        bottomPipe.getPipe().render(gc);

        pipes.add(topPipe);
        pipes.add(bottomPipe);

    }

    private void renderPipes() {
        for (Pipe pipe : pipes) {
            Sprite pipeSprite = pipe.getPipe();
            pipeSprite.render(gc);
            pipeSprite.update(5);
        }
    }

    private void checkPipes() {
        if (pipes.size() > 0) {
            Sprite pipe = pipes.get(pipes.size() - 1).getPipe();
            if (pipe.getPositionX() <= WIDTH / 2 - 75) {
                setPipes();
            }
            else if (pipe.getPositionX() <= -pipe.getWidth()) {
                pipes.remove(0);
                pipes.remove(0);
            }
            
        }
    }

    
    
}
