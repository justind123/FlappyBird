package justinduross;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bird {
    private String birdPath = "/images/bird1.png";
    private Sprite bird;
    private int currBird;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private double locationX = 100;
    private double locationY = 200;


    public Bird() {
        bird = new Sprite();
        bird.resizeImage(birdPath, 50, 45);
        bird.setPosition(70, 200);
        setFlightAnimation();
    }

    public void setFlightAnimation() {
        Sprite bird2 = new Sprite();
        bird2.resizeImage("/images/bird2.png", 50, 45);
        bird2.setPosition(locationX, locationY);

        Sprite bird3 = new Sprite();
        bird3.resizeImage("/images/bird1.png", 50, 45);
        bird3.setPosition(locationX, locationY);

        Sprite bird4 = new Sprite();
        bird4.resizeImage("/images/bird3.png", 50, 45);
        bird4.setPosition(locationX, locationY);

        sprites.addAll(Arrays.asList(bird, bird2, bird3, bird4));
    }

    public Sprite getBird() {
        return bird;
    }

     public Sprite animate() {
        if (currBird == sprites.size() - 1) {
            currBird = 0;
        }

        return sprites.get(currBird++);
    }
}
