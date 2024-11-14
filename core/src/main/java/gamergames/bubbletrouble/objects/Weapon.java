package gamergames.bubbletrouble.objects;

import com.badlogic.gdx.math.Vector2;

public class Weapon {
    ///float SPEED = -200;
    float SPEED = -300;
    Vector2 position;
    ///Vector2 size = new Vector2(16,256);
    Vector2 size = new Vector2(16,1024);
    int state = 0; // 0 - idle, 1 - going up
    int textureId = 8;

    public Weapon(Vector2 position) {
        this.position = position;
    }

    public void update(float dt){
        float velocity = 0;
        velocity = dt * state * SPEED;
        position.y += velocity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTextureId() {
        return textureId;
    }

    public void reposition(int x){
        position = new Vector2(x, 400);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
