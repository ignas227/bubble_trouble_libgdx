package gamergames.bubbletrouble.states;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.Random;

import gamergames.bubbletrouble.Main;
import gamergames.bubbletrouble.TextureManager;
import gamergames.bubbletrouble.objects.Ball;
import gamergames.bubbletrouble.objects.Player;
import gamergames.bubbletrouble.objects.Weapon;

public class PlayState extends State{
    private Texture background;

    StretchViewport viewport;

    Player player;
    Weapon weapon;
    TextureManager textureManager;


    ArrayList<Ball> balls;

    public static int boundY = (int) (Main.HEIGHT * 0.9f);


    protected PlayState(GameStateManager gsm) {
        super(gsm);

        viewport = new StretchViewport(Main.WIDTH, Main.HEIGHT, cam);
        cam.setToOrtho(true, Main.WIDTH, Main.HEIGHT);
        cam.update();

        background = new Texture("level_background.png");
        ///cam.setToOrtho(false, (BoundsX1 - BoundsX0) * 0.5f, (BoundsY1 - BoundsY0) * 0.5f);
        player = new Player(new Vector2(Main.WIDTH * 0.5f, boundY));
        weapon = new Weapon(new Vector2(-1000, -1000));
        textureManager = new TextureManager();
        balls = new ArrayList<>();
        ///Ball ball = new Ball(new Vector2(200, 100), 1, 4, 2);
        Ball ball = new Ball(new Vector2(200, 100), 1, 4, 5, 0);
        balls.add(ball);
    }


    protected void handleInput() {
        ///System.out.println(boundY);
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        boolean touch = (Gdx.app.getType() == Application.ApplicationType.Android) && (Gdx.input.isTouched());
        int thirds = (int) ((3 * touchPos.x) / Main.WIDTH); /// 0 - move left, 1 - shoot, 2 - move right

        int playerMovementState = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.A) || (touch && thirds == 0)){
            playerMovementState += -1;
        }if(Gdx.input.isKeyPressed(Input.Keys.D) || (touch && thirds == 2)){
            playerMovementState += 1;
        }
        player.setState(playerMovementState);

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || (touch && thirds == 1)){
            weapon.setState(1);
            weapon.reposition((int)(player.getPosition().x + player.getSize().x / 2 - weapon.getSize().x / 2));
        }
        ///System.out.println("Mouse X: " + mouseX + ", Mouse Y: " + mouseY);
        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        player.move(dt);

        player.update(dt);
        weapon.update(dt);
        for(int a = 0; a < balls.size(); a++){
            balls.get(a).update(dt);
        }

        for(int a = 0; a < balls.size(); a++){
            Ball ball = balls.get(a);
            ///System.out.println(weapon.getPosition() + " " + ball.getPosition());
            if(AreRectanglesOverlappingChatGPT(weapon.getPosition(), weapon.getSize(), balls.get(a).getPosition(), balls.get(a).getSize())){
                weapon.setPosition(new Vector2(-2000, -2000)); /// narnia
                if(ball.getLevel() > 1){
                    Ball ball1 = new Ball(new Vector2(ball.getPosition().x, ball.getPosition().y), -1, ball.getTextureId(), ball.getLevel() - 1, ball.getVelocity());
                    Ball ball2 = new Ball(new Vector2(ball.getPosition().x, ball.getPosition().y),  1, ball.getTextureId(), ball.getLevel() - 1, ball.getVelocity());
                    balls.add(ball1);
                    balls.add(ball2);
                    balls.remove(a);
                }else{
                    balls.remove(a);
                }
                a = balls.size() + 10;
            }if(AreRectanglesOverlappingChatGPT(player.getPosition(), player.getSize(), ball.getPosition(), ball.getSize())){
                gsm.set(new PlayState(gsm));
            }
        }


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Texture texture;
        ///sb.draw(background, 0, 0, background.getWidth() / 2, background.getHeight() / 2, background.getWidth(), background.getHeight(), (Main.WIDTH) / (float)background.getWidth(), (Main.HEIGHT) / (float)background.getHeight(), 0, 0, 0, background.getWidth(), background.getHeight(), false, false);
        texture = textureManager.getTexture(weapon.getTextureId());
        sb.draw(texture, weapon.getPosition().x, weapon.getPosition().y, weapon.getSize().x, weapon.getSize().y, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
        sb.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT + 60, 0, 0, background.getWidth(), background.getHeight(), false, true);
        texture = textureManager.getTexture(player.getFinalTextureId());
        sb.draw(texture, player.getPosition().x, player.getPosition().y, player.getSize().x, player.getSize().y, 0, 0, texture.getWidth(), texture.getHeight(), false, true);

        for(int a = 0; a < balls.size(); a++){
            Ball ball = balls.get(a);
            texture = textureManager.getTexture(ball.getTextureId());
            sb.draw(texture, ball.getPosition().x, ball.getPosition().y, ball.getSize().x, ball.getSize().y, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
        }


        sb.end();
    }

    @Override
    public void dispose() {
        textureManager.dispose();
    }

    public boolean AreRectanglesOverlapping(Vector2 pos0, Vector2 size0, Vector2 pos1, Vector2 size1){
        if(IsPointInRectangle(pos0, size0, pos1)){
            return true;
        }if(IsPointInRectangle(pos0, size0, new Vector2 (pos1.x, pos1.y + size1.y))){
            return true;
        }if(IsPointInRectangle(pos0, size0, new Vector2 (pos1.x + size1.x, pos1.y))){
            return true;
        }if(IsPointInRectangle(pos0, size0, new Vector2 (pos1.x + size1.x, pos1.y + size1.y))){
            return true;
        }
        return false;
    }

    boolean IsPointInRectangle(Vector2 pos0, Vector2 size0, Vector2 pos1){
        if((pos0.x < pos1.x) && (pos0.x + size0.x > pos1. x) && (pos0.y < pos1.y) && (pos0.y + size0.y > pos1. y)){
            return true;
        }else{
            return false;
        }
    }
    public boolean AreRectanglesOverlappingChatGPTMine(Vector2 pos0, Vector2 size0, Vector2 pos1, Vector2 size1) {
        float left0 = pos0.x;
        float right0 = pos0.x - size0.x;
        float top0 = pos0.y - size0.y; // top corner based on the original y position
        float bottom0 = pos0.y; // bottom corner based on the original y position

        float left1 = pos1.x;
        float right1 = pos1.x - size1.x;
        float top1 = pos1.y - size1.y; // same here
        float bottom1 = pos1.y; // same here

        // Check for overlap in both x and y axes
        boolean xOverlap = right0 > left1 && left0 < right1;
        boolean yOverlap = top0 > bottom1 && bottom0 < top1;

        return xOverlap && yOverlap;
    }
    public boolean AreRectanglesOverlappingChatGPT(Vector2 pos0, Vector2 size0, Vector2 pos1, Vector2 size1) {
        float left0 = pos0.x;
        float right0 = pos0.x + size0.x;
        float top0 = pos0.y + size0.y; // top corner based on the original y position
        float bottom0 = pos0.y; // bottom corner based on the original y position

        float left1 = pos1.x;
        float right1 = pos1.x + size1.x;
        float top1 = pos1.y + size1.y; // same here
        float bottom1 = pos1.y; // same here

        // Check for overlap in both x and y axes
        boolean xOverlap = right0 > left1 && left0 < right1;
        boolean yOverlap = top0 > bottom1 && bottom0 < top1;

        return xOverlap && yOverlap;
    }

}
