
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    final int ENEMY_SPEED = 5;
    final int PLAYER_SPEED = 8;
    Texture Enemy;
    Vector2 enemyPosition;
    Vector2 enemyVelocity;
    Enemy enemy;
    Rectangle enemyBounds;
    Rectangle bulletBounds;

    Matrix4 IDMATRIX;

    Animation koala;
    Animation hurt;
    Texture[] koalared;
    Texture Player;
    Texture sky;
    Texture youdead;
    boolean movingForward = true;

    Texture tileset, tileset2, tileset3, tileset4, tileset5, tileset6, tileset7, tileset8;
    TiledMapRenderer mapRenderer;
    TiledMap map;
    MapObjects objects, spikes;
    MapLayer GroundObject, SpikesObjects;
    int mapWidth, mapHeight, mapPixelWidth, mapPixelHeight, tilePixelWidth, tilePixelHeight;

    SpriteBatch batch;
    SpriteBatch absoluteBatch;
    ArrayList<Bullet> bullets;
    Texture right, left, Button, Secondbutton;
    Rectangle rightbox, leftbox, buttonbox, seconbuttonbox;

    int jumpCount;
    Sprite bg;
    Texture healthbar;
    Texture healthbar3;
    Texture healthbar2;
    Texture healthbar1;
    TextureRegion[] playerRun;
    TextureRegion playerFrame;

    Vector2 playerPosition;
    Vector2 gravity;
    Vector2 playerVelocity;
    Vector2 getX;
    Vector2 getY;
    Vector2 setLocation;
    float health;
    boolean dead;
    Sound shot;
    Music song;

    OrthographicCamera cam;
    Rectangle playerBounds;
    Rectangle platformBounds;

    Boolean isrunning = false;
    float timer;
    int width;
    int height;
    float dt;
    float stateTime;
    float lastStateTime;

    MapProperties prop;


    @Override
    public void create() {
        bulletBounds = new Rectangle();
        enemy = new Enemy();
        enemyVelocity = new Vector2();
        enemyPosition = new Vector2();
        enemyBounds = new Rectangle();
        enemy.create(1000,100);
        IDMATRIX = new Matrix4();

        shot = Gdx.audio.newSound(Gdx.files.internal("gunshot.wav"));
        song = Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));

        batch = new SpriteBatch();
        absoluteBatch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        gravity = new Vector2();

        health = 3;
        dead = false;
        playerVelocity = new Vector2();
        playerPosition = new Vector2();

        bg = new Sprite(new Texture("sky.png"));
        bg.scale(2.5f);
        tileset = new Texture("tile-duke-example.png");
        tileset2 = new Texture("spike2.png");
        tileset3 = new Texture("map1.png");
        tileset4 = new Texture("tree1.png");
        tileset5 = new Texture("vines1.png");
        tileset6 = new Texture("floatingplatform.png");
        tileset7 = new Texture("New Piskel (2) copy.png");
        tileset8 = new Texture("New Piskel (3) copy.png");
        map = new TmxMapLoader().load("lyleiscool.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        GroundObject = map.getLayers().get("GroundObject");
        objects = GroundObject.getObjects();
        SpikesObjects = map.getLayers().get("SpikesObject");
        spikes = SpikesObjects.getObjects();
        prop = new MapProperties();
        prop = map.getProperties();

        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        getX = new Vector2();
        getY = new Vector2();
        setLocation = new Vector2();
        youdead = new Texture("youdead.png");
        sky = new Texture("sky.png");
        right = new Texture("buttonright.png");
        left = new Texture("buttonleft.png");
        Player = new Texture("koalaidle.png");
        Button = new Texture("rsz_onebutton.png");
        Secondbutton = new Texture("rsz_twobutton_2.png");
        healthbar = new Texture("healthbar4.png");
        healthbar3 = new Texture("healthbar3.png");
        healthbar2 = new Texture("healthbar2.png");
        healthbar1 = new Texture("healthbar1.png");

        playerBounds = new Rectangle();
        rightbox = new Rectangle();
        rightbox.set(25, 425, right.getWidth(), right.getHeight());
        leftbox = new Rectangle();
        leftbox.set(150, 425, right.getWidth(), right.getHeight());
        buttonbox = new Rectangle();
        buttonbox.set(800, 425, Button.getWidth(), Button.getHeight());
        seconbuttonbox = new Rectangle();
        seconbuttonbox.set(900, 425, Secondbutton.getWidth(), Secondbutton.getHeight());


        bullets = new ArrayList<Bullet>();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, width, height);

        StartScreen.create();

        koalared = new Texture[2];
        koalared[0] = new Texture("koalaidle.png");
        koalared[1] = new Texture("koalared.png");
        hurt = new Animation(.1f, new TextureRegion(koalared[0]), new TextureRegion(koalared[1]));
        hurt.setPlayMode(Animation.PlayMode.LOOP);

        playerRun = new TextureRegion[7];
        playerRun[0] = new TextureRegion(new Texture("koalarunning2.png"));
        playerRun[1] = new TextureRegion(new Texture("koalarunning3.png"));
        playerRun[2] = new TextureRegion(new Texture("koalarunning4.png"));
        playerRun[3] = new TextureRegion(new Texture("koalarunning5.png"));
        playerRun[4] = new TextureRegion(new Texture("koalarunning6.png"));
        playerRun[5] = new TextureRegion(new Texture("koalarunning7.png"));
        playerRun[6] = new TextureRegion(new Texture("koalaidle.png"));
        koala = new Animation(.1f, new TextureRegion(playerRun[0]), new TextureRegion(playerRun[1]), new TextureRegion(playerRun[2]), new TextureRegion(playerRun[3]), new TextureRegion(playerRun[4]), new TextureRegion(playerRun[5]));
        koala.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0f;
        dt = 0f;
        resetGame();
    }

    private void resetGame() {
        playerPosition.set(400, 400);
        playerBounds.set(playerPosition.x, playerPosition.y, Player.getWidth(), Player.getHeight());
        playerVelocity.set(0, 0);
        gravity.set(0, -10);
        jumpCount = 0;
        health = 3;
        dead = false;
        enemyBounds.set(enemy.enemyPosition.x, enemy.enemyPosition.y, enemy.BadGuy.getWidth(), enemy.BadGuy.getHeight());
    }

    public void CheckCollision() {
        System.out.println("enemyhealth= " + enemy.health);
        System.out.println("bulletbounds= " + bulletBounds);
        System.out.println("enemybounds= " + enemyBounds);
        for (Bullet bullet : bullets) {
            if (bullet.bounds.overlaps(enemyBounds)) {
                enemy.health = enemy.health - 1;
            }
            if (enemy.health == 0) {
                enemy.isDead = true;
                enemy=new Enemy();

                enemy.create(playerPosition.x+700, playerPosition.y);
            }
        }
    }


    public void updategame() {
        dt = Gdx.graphics.getDeltaTime();

        if (StartScreen.atmenu) {
            StartScreen.updategame();
        } else {
            song.setVolume(.5f);
            song.play();
            song.isLooping();
            enemy.updategame(platformBounds);
            if ((playerPosition.x + 200) > enemy.enemyPosition.x && enemy.enemyPosition.x < playerPosition.x) {
                enemy.enemyPosition.x = enemy.enemyPosition.x + ENEMY_SPEED;
            }

            if (playerPosition.x - 200 < enemy.enemyPosition.x && enemy.enemyPosition.x > playerPosition.x) {
                enemy.enemyPosition.x = enemy.enemyPosition.x - ENEMY_SPEED;
            }

            CheckCollision();

            if (health <= 0)
            {
                dead = true;
            }

            isrunning = false;
            float dt = Gdx.app.getGraphics().getDeltaTime();
            timer = timer - dt;

            for (Bullet bullet : bullets) {
                bullet.update();
            }


            for (int i = 0; i < 4; i++) {
                if (Gdx.input.isTouched(i)) {
                    float X = Gdx.input.getX(i);
                    float Y = Gdx.input.getY(i);

                    if (rightbox.contains(X, Y)) {
                        isrunning = true;
                        playerPosition.x = playerPosition.x - PLAYER_SPEED;
                        movingForward = false;
                    }
                    if (leftbox.contains(X, Y)) {
                        isrunning = true;
                        playerPosition.x = playerPosition.x + PLAYER_SPEED;
                        movingForward = true;
                    }

                    if (seconbuttonbox.contains(X, Y) && timer <= 0) {
                        bullets.add(new Bullet(playerPosition.x, playerPosition.y + Player.getHeight()/2, 1, 10));
                        //bullets.add(new Bullet(playerPosition.x, playerPosition.y, 1, 10));
                        timer = .5f;
                        shot.play(100);
                    }

                    if (buttonbox.contains(X, Y) && jumpCount < 1) {
                        jumpCount = jumpCount + 1;
                        playerVelocity.y = 500;
                        gravity.set(0, -10);
                        playerVelocity.add(gravity);
                        playerPosition.mulAdd(playerVelocity,dt);
                        System.out.println("JUMP HAPPENED");
                    }
                }
            }


            //checks collision with ground
            gravity.set(0, -10);
            jumpCount = 1;

//        checks PLAYER collision with ground
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(playerBounds, rectangle)) {
                    playerVelocity.y = 0;
                    playerPosition.y = rectangle.y + rectangle.getHeight() + 1;
                    gravity.set(0, 0);
                    jumpCount = 0;
                    System.out.println("GRAVITY");
                }
            }

//        checks ENEMY collision with ground
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(enemy.enemyBounds,rectangle)) {
                    enemy.enemyVelocity.y = 0;
                    enemy.enemyPosition.y = rectangle.y + rectangle.getHeight() + 1;
                }
            }

            playerVelocity.add(gravity);
            playerPosition.mulAdd(playerVelocity, dt);
            playerBounds.setX(playerPosition.x);
            playerBounds.setY(playerPosition.y);

            enemyBounds.setX(enemy.enemyPosition.x);
            enemyBounds.setY(enemy.enemyPosition.y);

            //Checks collision with spikes
            for (RectangleMapObject rectangleObject : spikes.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, playerBounds)) {
                    if (Math.floor(stateTime * 3) > Math.floor(lastStateTime * 3)) {
                        health = health - 1f;
                    }
                    playerFrame = hurt.getKeyFrame(stateTime);
                }
            }

            if (playerPosition.y < 0) {
                resetGame();
                dead = true;
            }
        }
    }



    public void drawGame() {

        cam.position.set(playerPosition.x, playerPosition.y + 100, 0);
        cam.update();
        mapRenderer.setView(cam);
        mapRenderer.render();
        batch.setProjectionMatrix(cam.combined);

        absoluteBatch.begin();
        absoluteBatch.draw(bg, 0, 0);
        absoluteBatch.end();

        mapRenderer.setView(cam);
        mapRenderer.render();

        if (StartScreen.atmenu) {

            batch.begin();
            StartScreen.render();
            batch.end();
        } else {
            batch.begin();
            if (isrunning) {
                playerFrame = koala.getKeyFrame(stateTime);
            } else {
                playerFrame = playerRun[6];
            }

            for (Bullet bullet : bullets) {
                if (bullet.position.x < cam.position.x + width) {
                    batch.draw(bullet.BulletImage, bullet.position.x, bullet.position.y);
                }
            }

            if (movingForward && playerFrame.isFlipX() || !movingForward && !playerFrame.isFlipX()) {
                playerFrame.flip(true, false);


            }
            batch.draw(playerFrame, playerPosition.x, playerPosition.y);

            if (!enemy.isDead) {
                enemy.draw(batch);

            }
            batch.end();

            absoluteBatch.begin();
            absoluteBatch.draw(right, 125, 25);
            absoluteBatch.draw(left, 15, 25);
            if (health == 3) {
                absoluteBatch.draw(healthbar, 25, height - 50);
            } else if (health >= 2) {
                absoluteBatch.draw(healthbar3, 25, height - 50);
            } else if (health >= 1) {
                absoluteBatch.draw(healthbar2, 25, height - 50);
            } else {
                absoluteBatch.draw(healthbar1, 25, height - 50);
            }


            absoluteBatch.draw(Button, width - 200, 25);
            absoluteBatch.draw(Secondbutton, width - 100, 25);
            absoluteBatch.end();

        }
    }


    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lastStateTime = stateTime;
        stateTime += Gdx.graphics.getDeltaTime();

        updategame();
        drawGame();

        }
    }


