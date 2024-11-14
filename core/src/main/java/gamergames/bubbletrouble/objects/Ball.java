package gamergames.bubbletrouble.objects;

import com.badlogic.gdx.math.Vector2;

import gamergames.bubbletrouble.Main;
import gamergames.bubbletrouble.states.PlayState;

public class Ball {
    ///float SPEED_X = 50;
    float SPEED_X = 100;
    float SPEED_Y = 40;
    float GRAVITY = 3;
    float velocity;
    Vector2 position;
    ///Vector2 size = new Vector2(16,256);
    int level;
    Vector2 size = new Vector2(20,20);
    int state = 0; // -1 - going left, 0 - idle, 1 - going right
    int textureId;

    int boundY = PlayState.boundY;
    int boundX0 = 0;
    int boundX1 = Main.WIDTH;

    public Ball(Vector2 position, int state, int textureId, int level, float velocity) {
        this.position = position;
        this.state = state;
        this.textureId = textureId;
        this.level = level;
        this.velocity = velocity;
    }

    public void update(float dt){
        velocity += GRAVITY * dt;
        position.y += velocity * dt * SPEED_Y;
        position.x += SPEED_X * dt * state;
        if((position.y + getSize().y) > boundY){ /// Hit rock bottom :(
            velocity *= -1;
            position.y = (boundY - getSize().y) * 0.99999f;
        }

        if(position.x < boundX0){
            state *= -1;
            position.x = boundX0 + 0.00001f;
        }if((position.x + getSize().x) > boundX1){
            state *= -1;
            position.x = (boundX1 - getSize().x) - 0.00001f;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return new Vector2(size.x * level, size.y * level);
    }

    public int getState() {
        return state;
    }

    public int getTextureId() {
        return textureId;
    }

    public int getLevel() {
        return level;
    }

    public float getVelocity() {
        return velocity;
    }
}
