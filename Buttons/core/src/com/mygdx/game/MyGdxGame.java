package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

    Animation koala;
    Texture Player;
    Texture sky;
    Texture platform;
    boolean movingForward = true;

    Texture tileset, tileset2, tileset3, tileset4, tileset5, tileset6, tileset7, tileset8;
    TiledMapRenderer mapRenderer;
    TiledMap map;
    MapObjects objects, spikes;
    MapLayer GroundObject,SpikesObjects;

    SpriteBatch batch;
    ArrayList<Bullet> bullets;
    Texture right, left, Button, Secondbutton;
    Rectangle rightbox, leftbox, buttonbox, seconbuttonbox;
    int buttonUp;

    int jumpCount;
    int buttonMove;
    Texture healthbar;
    Texture healthbar3;
    Texture healthbar2;
    Texture[] playerRun;

    Vector2 playerPosition;
    Vector2 platformPosition;
    Vector2 gravity;
    Vector2 playerVelocity;
    Vector2 getX;
    Vector2 getY;
    Vector2 setLocation;
    int health;

    OrthographicCamera cam;
    Rectangle playerBounds;
    Rectangle platformBounds;

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

        health = 3;

        playerVelocity = new Vector2();
        playerPosition = new Vector2();
        platformPosition = new Vector2();

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

        getX = new Vector2();
        getY = new Vector2();
        setLocation = new Vector2();
        sky = new Texture("sky.png");
        right = new Texture("buttonright.png");
        left = new Texture("buttonleft.png");
        Player = new Texture("koalaidle.png");
        Button = new Texture("rsz_onebutton.png");
        Secondbutton = new Texture("rsz_twobutton_2.png");
        platform = new Texture("platform.png");
        healthbar = new Texture("healthbar4.png");
        healthbar3 = new Texture ("healthbar3.png");
        healthbar2 = new Texture ("healthbar2.png");
        platform = new Texture("platform.png");
        platformBounds = new Rectangle();
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
        health = 3;
    }

    public void tiledUpdate(){

//        MapLayer collisionLayer = (TiledMapTileLayer)map.getLayers().get("Ground");
//        MapObjects objects = collisionLayer.get("SpikesObject").getObjects();

        int tileWidth = 16;
        int tileHeight = 16;

    // there are several other types, Rectangle is probably the most common one

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

//        checks collision with ground
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (rectangle.contains(playerBounds)) {
                    playerVelocity.y = 0;
                    platformPosition.y = rectangle.y + rectangle.getHeight() + 1;
                    gravity.set(0, 0);
                    jumpCount = 0;
                    System.out.println("GRAVITY");
                }
            }

            //Checks collision with spikes
            for (RectangleMapObject rectangleObject : spikes.getByType(RectangleMapObject.class)) {
                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, playerBounds)){
                  health = health -1;
                }
            }


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

                }

                if (playerPosition.y < 0) {
                    resetGame();
                }
            }
            playerBounds.setX(playerPosition.x);
            playerBounds.setY(playerPosition.y);
            cam.position.set(width - playerPosition.x, (height / 2), 0);
        }
    }

    public void drawGame() {
        cam.position.set(playerPosition.x, playerPosition.y, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        mapRenderer.setView(cam);
        mapRenderer.render();

        batch.begin();
        //batch.draw(sky, cam.position.x - sky.getWidth() / 2, 0);

        if (StartScreen.atmenu) {
            StartScreen.render();

        } else {
            if (isrunning) {
                batch.draw(koala.getKeyFrame(stateTime), playerPosition.x, playerPosition.y);
            } else {
                batch.draw(playerRun[6], playerPosition.x, playerPosition.y);
            }


            batch.draw(right, 65 + buttonMove, cam.position.y - (height/2));
            batch.draw(left, -55 + buttonMove, cam.position.y - (height / 2));
            if (health == 3){
                System.out.println("I'm Fine");
                batch.draw(healthbar, -55 + buttonMove, cam.position.y + (height / 2) - 40);
            } else if (health == 2){
                System.out.println("i've been better...");

                batch.draw(healthbar3, -55 + buttonMove, cam.position.y + (height/2) -40);
            } else if (health == 1){
                System.out.println("I'm fucked...");
                batch.draw(healthbar2,-55 + buttonMove, cam.position.y + (height/2) -40);
            }
            batch.draw(Button, 650 + buttonMove, cam.position.y - (height / 2));
            batch.draw(Secondbutton, 770 + buttonMove, cam.position.y - (height / 2));
            for (Bullet bullet : bullets) {
                if (bullet.position.x < cam.position.x + width) {
                    batch.draw(bullet.BulletImage, bullet.position.x, bullet.position.y);
                }
            }
        }


        batch.end();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        updategame();
        drawGame();

    }

}


