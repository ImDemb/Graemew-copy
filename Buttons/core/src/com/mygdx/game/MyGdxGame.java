package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    ArrayList<Bullet> bullets;

    Animation koala;
    Texture up, right, left, down;
    Texture Player;
    Texture Enemy;
    Texture platform;
    Texture Button;
    Texture Secondbutton;
    Texture health;
    Texture[] playerRun;

    Vector2 enemyVelocity;
    Vector2 enemyPosition;
    Vector2 playerPosition;
    Vector2 platformPosition;
    Vector2 gravity;
    Vector2 enemyGravity;
    Vector2 playerVelocity;
    Vector2 getX;
    Vector2 getY;
    Vector2 setLocation;


    Rectangle enemyBounds;
    Rectangle playerBounds;
    Rectangle platformBounds;

    Boolean isrunning = false;
    float timer;
    int width;
    int height;


    private TextureRegion[] regions = new TextureRegion[6];
    TextureRegion currentFrame;
    float stateTime;


    @Override
    public void create() {

        batch = new SpriteBatch();

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gravity = new Vector2();
        enemyGravity = new Vector2();
        playerVelocity = new Vector2();
        playerPosition = new Vector2();
        enemyBounds = new Rectangle();
        enemyPosition = new Vector2();
        enemyVelocity = new Vector2();
        platformPosition = new Vector2();
        getX = new Vector2();
        getY = new Vector2();
        setLocation = new Vector2();
        right = new Texture("buttonright.png");
        up = new Texture("buttonup.png");
        left = new Texture("buttonleft.png");
        down = new Texture("buttondown.png");
        Player = new Texture("koalaidle.png");
        Button = new Texture("rsz_onebutton.png");
        Secondbutton = new Texture("rsz_twobutton_2.png");
        platform = new Texture("platform.png");
        Enemy = new Texture("Enemy.png");
        platformBounds = new Rectangle();
        playerBounds = new Rectangle();
        bullets = new ArrayList<Bullet>();
        playerPosition.set(400, 400);
        enemyPosition.set(550,500);
        platformPosition.set(0, 0);
        playerRun = new Texture[7];
        playerRun[0] = new Texture("koalarunning2.png");
        playerRun[1] = new Texture("koalarunning3.png");
        playerRun[2] = new Texture("koalarunning4.png");
        playerRun[3] = new Texture("koalarunning5.png");
        playerRun[4] = new Texture("koalarunning6.png");
        playerRun[5] = new Texture("koalarunning7.png");
        playerRun[6] = new Texture("koalaidle.png");
        koala = new Animation(.1f, new TextureRegion(playerRun[0]), new TextureRegion(playerRun[1]), new TextureRegion(playerRun[2]), new TextureRegion(playerRun[3]), new TextureRegion(playerRun[4]), new TextureRegion(playerRun[5]));
        koala.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0.f;

        resetGame();


    }


    private void resetGame() {
        platformBounds.set(platformPosition.x, platformPosition.y, platform.getWidth(), platform.getHeight());
        playerBounds.set(playerPosition.x, playerPosition.y, Player.getWidth(), Player.getHeight());
        playerVelocity.set(0, 0);
        gravity.set(0, -10);
        enemyGravity.set(0,-10);
        enemyVelocity.set(0,0);

    }

    public void updategame() {
        isrunning = false;
        float dt = Gdx.app.getGraphics().getDeltaTime();
        timer = timer - dt;
        playerVelocity.add(gravity);
        playerPosition.mulAdd(playerVelocity, dt);
        enemyVelocity.add(enemyGravity);
        enemyPosition.mulAdd(enemyVelocity, dt);

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        if (playerBounds.overlaps(platformBounds)) {
            playerVelocity.y = 0;
            gravity.set(0, 0);
        } else {
            gravity.set(0, -10);
        }
        if (enemyBounds.overlaps(platformBounds)) {
            enemyVelocity.y = 0;
            enemyGravity.set (0,0);
        } else {
            enemyGravity.set(0,-10);
        }


        if (Gdx.input.isTouched()) {
            float X = Gdx.input.getX();
            float Y = Gdx.input.getY();
            System.out.println(X + " " + Y);


            if (X > 25 && X < 117 && Y < 525 && Y > 425) {
                isrunning = true;
                playerPosition.x = playerPosition.x - 5;
            }
            if (X > 150 && X < 242 && Y < 525 && Y > 425) {
                isrunning = true;
                playerPosition.x = playerPosition.x + 5;
            }
            System.out.println(timer);

            if (X > 826 && X < 905 && Y > 450 && Y < 500) {
                bullets.add(new Bullet(playerPosition.x, playerPosition.y, 1, 10));
                timer = 1;
                System.out.println("b button pressed");
            }

            if (X > 735 && X < 800 && Y < 525 && Y > 425) {
                playerVelocity.y = 200;
            }

        }
        playerBounds.setX(playerPosition.x);
        playerBounds.setY(playerPosition.y);

    }

   /* public void moveToPlayer(Player player) {
        int deltaX = getX() - player.getX();
        int deltaY = getY() - player.getY();
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            setLocation(getX() - 1, getY());
        }
        else if (deltaX < 0) {
            setLocation(getX() + 1, getY());
        }

    else {
        if (deltaY > 0) {
            setLocation(getX(), getY() - 1);
        }
        else if (deltaY < 0) {
            setLocation(getX(), getY() + 1);
        }
    }
    }
*/

    @Override
    public void render() {
        updategame();

        Gdx.gl.glClearColor(255, 255, 255, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(right, 150, 25);
        batch.draw(left, 25, 25);
        batch.draw(platform, platformPosition.x, platformPosition.y);
        if (isrunning) {
            batch.draw(koala.getKeyFrame(stateTime), playerPosition.x, playerPosition.y);
        } else {
            batch.draw(playerRun[6], playerPosition.x, playerPosition.y);
        }
        //batch.draw(Player, playerPosition.x, playerPosition.y);
        batch.draw(right, 150, 25);
        batch.draw(Enemy, enemyPosition.x, enemyPosition.y);
        //batch.draw(health, 500, 500);
        batch.draw(left, 25, 25);
        batch.draw(Button, 710, 25);
        batch.draw(Secondbutton, 825, 25);
        for (Bullet bullet : bullets) {
            if (bullet.position.x < width) {
                batch.draw(bullet.BulletImage, bullet.position.x, bullet.position.y);
            }
        }
        batch.end();
    }
}

public class Enemy{

        
        }

