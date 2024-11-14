package gamergames.bubbletrouble.objects;

import com.badlogic.gdx.math.Vector2;

public class Player {
    float SPEED = 200;
    Vector2 position;
    ///Vector2 size = new Vector2(100,100);
    Vector2 size = new Vector2(50,50);
    int state = 0; // -1 - walking left, 0 - idle, 1 - walking right
    int textureId = 2;

    public Player(Vector2 position) {
        this.position = new Vector2(position.x, position.y - size.x);
    }

    public void update(float dt){

    }

    public int getFinalTextureId() {
        return textureId + state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void move(float a){
        position.x += a * SPEED * state;
    }
}
