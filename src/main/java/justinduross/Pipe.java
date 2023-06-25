package justinduross;

import javafx.scene.image.Image;

public class Pipe {

    private Sprite pipe;
    private String downPipePath = "/images/down_pipe.png";
    private String upPipePath = "/images/up_pipe.png";
    private int width;
    private int height;
    private int x;
    private int y;
    
    public Pipe(boolean isBottom, int height) {
        this.pipe = new Sprite();
        this.width = 70;
        this.height = 400;
        if (isBottom) {
            this.pipe.setImage( new Image(getClass().getResource(upPipePath).toExternalForm(), 0, this.height, true, false) );
        }
        else {
            this.pipe.setImage( new Image(getClass().getResource(downPipePath).toExternalForm(), 0, this.height, true, false) );
        }
        x = 400;
        if (isBottom) {
            y = 400 - height + 125;
        }
        else {
            y = 0 - height;
        }
        this.pipe.setPosition(x, y);
    }

    public Sprite getPipe() {
        return pipe;
    }
}
