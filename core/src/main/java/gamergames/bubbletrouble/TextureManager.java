package gamergames.bubbletrouble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    Texture[] textures;
    int size = 9;

    public TextureManager() {
        textures = new Texture[size]; /// MIGHT NEED 0 ADDED
        textures[0] = new Texture(Gdx.files.internal("blank.png"));
        textures[1] = new Texture(Gdx.files.internal("player_left.png"));
        textures[2] = new Texture(Gdx.files.internal("player_idle.png"));
        textures[3] = new Texture(Gdx.files.internal("player_right.png"));
        textures[4] = new Texture(Gdx.files.internal("red.png"));
        textures[5] = new Texture(Gdx.files.internal("yellow.png"));
        textures[6] = new Texture(Gdx.files.internal("blue.png"));
        textures[7] = new Texture(Gdx.files.internal("blue2.png"));
        textures[8] = new Texture(Gdx.files.internal("weapon.png"));
    }

    public Texture getTexture(int a) {
        return textures[a];
    }

    public void dispose(){
        for(int a = 0; a < size; a++){
            textures[a].dispose();
        }
    }
}
