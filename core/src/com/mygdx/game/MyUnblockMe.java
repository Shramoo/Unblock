package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

public class MyUnblockMe extends ApplicationAdapter {
	private Texture BoardBorderTexture;
	private Texture BoardFieldTexture;
	private Texture BlockTexture;

	private UnblockBoard GameBoard;

	public static final int RESOLUTION = 850;

	@Override
	public void create() {
		BoardBorderTexture = new Texture(Gdx.files.internal("data/Tile.png") ) ;
		BoardFieldTexture = new Texture(Gdx.files.internal("data/Tile.png") ) ;
		BlockTexture = new Texture(Gdx.files.internal("data/block.png") ) ;

		GameBoard = new UnblockBoard(BoardBorderTexture, BoardFieldTexture);
        generate();
	}

    private void generate()
    {
        try
        {
            GameBoard.addBlock(
                    new Block(new GridPoint2(3,4), 2,
                            Block.Orientation.horizontal,
                            Block.Type.key, BlockTexture,
                            GameBoard));
            GameBoard.addBlock(
                    new Block(new GridPoint2(2,3), 3,
                    Block.Orientation.vertical,
                    Block.Type.simple,
                    BlockTexture, GameBoard));
        } catch (Exception e)
        {
            System.out.println("Exception while generating");
        }
    }

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
        GameBoard.update();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		GameBoard.render();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

