package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion stand, jump;


	static final int WIDTH = 18;
	static final int HEIGHT = 26;

	float x, y, xv, yv;
	boolean canJump, faceRight = true;

	static final float MAX_VELOCITY = 175;
	static final float DECELERATION = 0.95f;

	static final float MAX_JUMP_VELOCITY = 1500;
	static final int GRAVITY = -50;


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture sheet = new Texture("koalio.png");
		TextureRegion[][] tiles = TextureRegion.split(sheet, WIDTH, HEIGHT);
		stand = tiles[0][0];
		jump = tiles [0][1];

	}

	@Override
	public void render () {
		move();

		TextureRegion img;
		if (y > 0) {
			img = jump;
		}
		else {
			img = stand;
		}

		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (faceRight) {
			batch.draw(img, x, y, WIDTH * 3, HEIGHT * 3);
		}
		else {
			batch.draw(img, x + WIDTH * 3, y, WIDTH * -3, HEIGHT * 3);
		}
		batch.end();
	}

	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canJump) {
			yv = MAX_JUMP_VELOCITY;
			canJump = false;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = -MAX_VELOCITY;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			faceRight = true;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = -MAX_VELOCITY;
			faceRight = false;
		}

		yv += GRAVITY;

		float delta = Gdx.graphics.getDeltaTime();
		y += yv * delta;
		x += xv * delta;

		xv = decelerate(xv);
		yv = decelerate(yv);

		if (y < 0) {
			y =0;
			canJump = true;
		}

	}
		public float decelerate(float velocity) {
		velocity *= DECELERATION;
		if (Math.abs(velocity) < 1) {
		velocity = 0;
		}
		return velocity;
	}
}
