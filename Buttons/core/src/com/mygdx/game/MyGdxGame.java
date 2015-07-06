package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

    Animation koala;
    Texture Player;
    Texture platform;
    Texture Enemy;
    boolean movingForward = true;

    Texture tileset,tileset2, tileset3;
    TiledMapRenderer mapRenderer;
    TiledMap map;

    SpriteBatch batch;
    ArrayList<Bullet> bullets;
    Texture right, left, Button, Secondbutton;
    Rectangle rightbox, leftbox, buttonbox, seconbuttonbox;

    int jumpCount;
    int buttonMove;
    Texture health;
    Texture[] playerRun;

    Vector2 playerPosition;
    Vector2 platformPosition;
    Vector2 gravity;
    Vector2 playerVelocity;
    Vector2 getX;
    Vector2 getY;
    Vector2 setLocation;
    Vector2 enemyPosition;
    Vector2 enemyVelocity;
    Enemy enemy;


    OrthographicCamera cam;
    Rectangle playerBounds;
    Rectangle platformBounds;
    Rectangle enemyBounds;
    Rectangle bulletBounds;


    Boolean isrunning = false;
    float timer;
    int width;
    int height;
    TextureRegion currentFrame;
    float stateTime;
    private TextureRegion[] regions = new TextureRegion[6];

    @Override
    public void create() {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gravity = new Vector2();

        playerVelocity = new Vector2();
        playerPosition = new Vector2();
        platformPosition = new Vector2();

//        tileset = new Texture("New Piskel (2) copy 2.png");
  //      tileset2 = new Texture("platform3.png");
    //    tileset3 = new Texture("tile-duke-example.png");
        //map = new TmxMapLoader().load("lyleiscool.tmx");
       // mapRenderer = new OrthogonalTiledMapRenderer(map);

        getX = new Vector2();
        enemy = new Enemy();
        enemyVelocity = new Vector2();
        enemyPosition = new Vector2();
        enemyBounds = new Rectangle();
        getY = new Vector2();
        setLocation = new Vector2();
        right = new Texture("buttonright.png");
        left = new Texture("buttonleft.png");
        Player = new Texture("koalaidle.png");
        Button = new Texture("rsz_onebutton.png");
        Secondbutton = new Texture("rsz_twobutton_2.png");
        platform = new Texture("platform.png");
        health = new Texture("healthbar4.png");
        platform = new Texture("platform.png");
        platformBounds = new Rectangle();
        bulletBounds = new Rectangle();
        playerBounds = new Rectangle();
        rightbox = new Rectangle();
        rightbox.set(25, 425, right.getWidth(), right.getHeight());
        leftbox = new Rectangle();
        leftbox.set(150, 425, right.getWidth(), right.getHeight());
        buttonbox = new Rectangle();
        buttonbox.set(735, 425, Button.getWidth(), Button.getHeight());
        seconbuttonbox = new Rectangle();
        seconbuttonbox.set(826, 450, Secondbutton.getWidth(), Secondbutton.getHeight());

        bullets = new ArrayList<Bullet>();
        playerPosition.set(350, 350);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, width, height);

        enemy.create();
        StartScreen.create();


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
        stateTime = 0f;

        resetGame();
    }


    private void resetGame() {
        playerPosition.set(400, 400);
        buttonMove = 0;
        platformBounds.set(platformPosition.x, platformPosition.y, platform.getWidth(), platform.getHeight());
        playerBounds.set(playerPosition.x, playerPosition.y, Player.getWidth(), Player.getHeight());
        playerVelocity.set(0, 0);
        gravity.set(0, -10);
        jumpCount = 0;
        enemyBounds.set(enemy.enemyPosition.x, enemy.enemyPosition.y, enemy.BadGuy.getWidth(), enemy.BadGuy.getHeight());





    }

    public void CheckCollision() {
        if(bulletBounds.overlaps(enemyBounds)) {
            enemy.health = enemy.health-1;
        }
        if (enemy.health == 0){
            enemy.isDead = true;
        }

    }




    public void updategame() {

        if (StartScreen.atmenu) {
            StartScreen.updategame();

        } else {
            isrunning = false;
            float dt = Gdx.app.getGraphics().getDeltaTime();
            timer = timer - dt;
            playerVelocity.add(gravity);
            playerPosition.mulAdd(playerVelocity, dt);

            for (Bullet bullet : bullets) {
                bullet.update();
            }

            if (playerBounds.overlaps(platformBounds)) {
                playerVelocity.y = 0;
                gravity.set(0, 0);
                jumpCount = 0;
                System.out.println("GRAVITY");
            } else {
                gravity.set(0, -10);
            }

            enemy.updategame(platformBounds);



            for (int i = 0; i < 4; i++) {
                if (Gdx.input.isTouched(i)) {
                    float X = Gdx.input.getX(i);
                    float Y = Gdx.input.getY(i);
                    System.out.println(X + " " + Y);


                    if (rightbox.contains(X, Y)) {
                        isrunning = true;
                        playerPosition.x = playerPosition.x - 5;
                        buttonMove -= 5;
                        movingForward = false;
                    }
                    if (leftbox.contains(X, Y)) {
                        isrunning = true;
                        playerPosition.x = playerPosition.x + 5;
                        buttonMove += 5;
                        movingForward = true;
                    }
                    System.out.println(timer);

                    if (seconbuttonbox.contains(X, Y) && timer <= 0) {
                        bullets.add(new Bullet(playerPosition.x, playerPosition.y, 1, 10));
                        timer = 1;
                    }

                    if (buttonbox.contains(X, Y) && jumpCount < 1) {
                        jumpCount = jumpCount + 1;
                        playerVelocity.y = 400;
                        gravity.set(0, -10);
                    }

                    if (playerPosition.y < 0) {
                        resetGame();
                    }
                }
            }
            playerBounds.setX(playerPosition.x);
            playerBounds.setY(playerPosition.y);
            cam.position.set(width - playerPosition.x, (height / 2), 0);
        }
        if((playerPosition.x + 200) > enemy.enemyPosition.x && enemy.enemyPosition.x < playerPosition.x) {
            enemy.enemyPosition.x= enemy.enemyPosition.x + 10;

        }

        if(playerPosition.x - 200 < enemy.enemyPosition.x && enemy.enemyPosition.x > playerPosition.x){
            enemy.enemyPosition.x= enemy.enemyPosition.x -10;


        }
        CheckCollision();

        playerBounds.setX(playerPosition.x);
        playerBounds.setY(playerPosition.y);
        enemyBounds.setX(enemy.enemyPosition.x);
        enemyBounds.setY(enemy.enemyPosition.y);
    }



    private void enemy() {

    }




    public void drawGame() {
        cam.position.set(playerPosition.x, (height / 2), 0);
        cam.update();
        mapRenderer.setView(cam);
        mapRenderer.render();

        batch.begin();

        if (StartScreen.atmenu) {
            StartScreen.render();

        } else {
            //batch.draw(platform, platformPosition.x, platformPosition.y);
            if (isrunning) {
                batch.draw(koala.getKeyFrame(stateTime), playerPosition.x, playerPosition.y);
            } else {
                batch.draw(playerRun[6], playerPosition.x, playerPosition.y);
            }

            batch.draw(right, 65 + buttonMove, 25);
            batch.draw(health, -55 + buttonMove, 475);
            batch.draw(left, -55 + buttonMove, 25);
            batch.draw(Button, 650 + buttonMove, 25);
            batch.draw(Secondbutton, 770 + buttonMove, 25);
            for (Bullet bullet : bullets) {
                if (bullet.position.x < cam.position.x + width) {
                    batch.draw(bullet.BulletImage, bullet.position.x, bullet.position.y);
                }
            }
        }
        batch.end();
        batch.setProjectionMatrix(cam.combined);
        batch.end();
        if (!enemy.isDead) {
            enemy.draw();
        }
    }

    @Override
    public void render() {
        enemy.updategame(platformBounds);
        Gdx.gl.glClearColor(1,1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        updategame();
        drawGame();

    }

}