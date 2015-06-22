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
    Texture Player;
    Vector2 playerVelocity;
    Rectangle playerBounds;
    Vector2 playerPosition;

    Texture Enemy;
    Vector2 enemyVelocity;
    Rectangle enemyBounds;
    Vector2 enemyPosition;

    Texture platform;
    Vector2 platformPosition;
    Rectangle platformBounds;

    SpriteBatch batch;
    ArrayList<Bullet> bullets;
    Texture right, left;
    Texture Button;
    Texture Secondbutton;
    Texture health;
    Vector2 gravity;
    float timer;

    Animation koala;
    SpriteBatch SpriteBatch;
    Texture[] playerRun;
    Boolean isrunning = false;
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
        enemyVelocity = new Vector2();
        enemyPosition = new Vector2();
        playerVelocity = new Vector2();
        playerPosition = new Vector2();
        platformPosition = new Vector2();
        right = new Texture("buttonright.png");
        left = new Texture("buttonleft.png");
        Player = new Texture("koalaidle.png");
        Button = new Texture("rsz_onebutton.png");
        Secondbutton = new Texture("rsz_twobutton_2.png");
        platform = new Texture("platform.png");
        health = new Texture("healthbar4.png");
        Enemy = new Texture("gummybear.png");
        platformBounds = new Rectangle();
        playerBounds = new Rectangle();
        enemyBounds = new Rectangle();
        bullets = new ArrayList<Bullet>();
        playerPosition.set(400, 400);
        enemyPosition.set(400, 400);
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
        enemyBounds.set(enemyPosition.x, enemyPosition.y, Enemy.getWidth(), Enemy.getHeight());
        playerVelocity.set(0, 0);
        enemyVelocity.set(0, 0);
        gravity.set(0, -10);
    }

    public void updategame() {
        isrunning = false;
        float dt = Gdx.app.getGraphics().getDeltaTime();
        timer = timer - dt;
        playerVelocity.add(gravity);
        playerPosition.mulAdd(playerVelocity, dt);

        enemyVelocity.add(gravity);
        enemyPosition.mulAdd(enemyVelocity, dt);

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        if (playerBounds.overlaps(platformBounds)) {
            playerVelocity.y = 0;
            gravity.set(0,0);
        }
        else{
            gravity.set(0,-10);
        }
        
        if (enemyBounds.overlaps(platformBounds)) {
            enemyVelocity.y = 0;
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
                gravity.set(0, -10);
            }

        }
        playerBounds.setX(playerPosition.x);
        playerBounds.setY(playerPosition.y);

        enemyBounds.setX(enemyPosition.x);
        enemyBounds.setY(enemyPosition.y);

        System.out.println("gravity " + gravity.y);

    }


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
        batch.draw(Enemy, 400, 400);
        batch.draw(right, 150, 25);
        batch.draw(health, 35, 475);
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

