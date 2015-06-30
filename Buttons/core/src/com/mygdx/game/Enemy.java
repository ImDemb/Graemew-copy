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

/**
 * Created by GraemeWillaisma on 6/23/15.
 */
public class Enemy {
    Texture BadGuy;
    SpriteBatch batch;

    Vector2 enemyVelocity;
    Vector2 enemyPosition;
    Vector2 enemyGravity;
    Vector2 getX;
    Vector2 getY;
    Vector2 setLocation;
    Enemy enemy;
    int health;
    boolean isDead;

    Rectangle enemyBounds;

    public void create() {

        isDead = false;
        health = 3;
        enemyGravity = new Vector2();
        enemyGravity.set(0, -100);
        batch = new SpriteBatch();
        BadGuy = new Texture("Enemy.png");
        enemyPosition = new Vector2();
        enemyVelocity = new Vector2();
        enemyPosition.set(550, 500);
        enemyBounds = new Rectangle();
    }

    private void resetGame() {


        enemyVelocity.set(0, 0);

    }

    public void updategame(Rectangle platformBounds) {

        enemyBounds.set(enemyPosition.x, enemyPosition.y, BadGuy.getWidth(), BadGuy.getHeight());


        float dt = Gdx.app.getGraphics().getDeltaTime();
         enemyVelocity.add(enemyGravity);
        enemyPosition.mulAdd(enemyGravity, dt);


        if (enemyBounds.overlaps(platformBounds)) {
            enemyGravity.set(0, 0);
            enemyVelocity.y = 0;
        }


    }











    public Vector2 getEnemyPosition() {
        return enemyPosition;
    }

    public Rectangle getEnemyBounds(){
        return  enemyBounds;
    }


    public void draw() {
        batch.begin();
        batch.draw(BadGuy, enemyPosition.x, enemyPosition.y);
        batch.end();
    }
}