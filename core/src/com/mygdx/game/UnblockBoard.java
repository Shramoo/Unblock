package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

public class UnblockBoard {
    Texture borderTexture;
    Texture fieldTexture;

    private SpriteBatch Batch;
    private ArrayList<Block> Blocks = new ArrayList<Block>();
    private Tile[][] Tiles;

    public static final int SIZE = 9;
    public static final int TILE_SIZE = MyUnblockMe.RESOLUTION / SIZE;


    private Block dragging = null;

    private boolean last_pressed = false;
    private GridPoint2 last_point;


    public UnblockBoard(Texture borderTexture, Texture fieldTexture)
    {
        Batch = new SpriteBatch();
        Tiles = new Tile[SIZE][SIZE];

        this.borderTexture = borderTexture;
        this.fieldTexture = fieldTexture;

        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {

                if (y == SIZE / 2  && x >= SIZE-2) {
                    Tiles[x][y] = new Tile(
                            new GridPoint2(x,y),
                            Tile.Type.lock, fieldTexture,
                            TILE_SIZE);
                }
                else if (x != 0 && y != 0 && x < SIZE-2 && y < SIZE-2) {
                    Tiles[x][y] = new Tile(
                            new GridPoint2(x,y),
                            Tile.Type.field, fieldTexture,
                            TILE_SIZE);
                }
                else {
                    Tiles[x][y] = new Tile(
                            new GridPoint2(x,y),
                            Tile.Type.border, borderTexture,
                            TILE_SIZE);

                }
            }
        }
    }

    public void render()
    {
        Batch.begin();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                Tiles[x][y].draw(Batch);
            }
        }

        for(Block block : Blocks)
        {
            block.draw(Batch);
        }
        Batch.end();
    }

    public Tile getTile(int x, int y) {
        return Tiles[x][y];
    }

    public void addBlock(Block block)
    {
        Blocks.add(block);
    }

    public void update()
    {
        try {
            GridPoint2 mouse_pos = new GridPoint2(Gdx.input.getX() / TILE_SIZE,  (MyUnblockMe.RESOLUTION - Gdx.input.getY()) / TILE_SIZE);
            GridPoint2 tile_pos = Tiles[mouse_pos.x][mouse_pos.y].getPosition();

            System.out.println( "(" + Gdx.input.getX() + "," + (MyUnblockMe.RESOLUTION - Gdx.input.getY()) + ");"
                    + mouse_pos.toString() + ";"
                    + tile_pos.toString() + ";"
                    + Tiles[mouse_pos.x][mouse_pos.y].isOccupied());
        }catch (Exception e)
        {}

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if(dragging != null)
            {
                int current_mouse_pos, compare = 0;
                GridPoint2 currPos = dragging.getPosition();
                boolean success = false;
                switch (dragging.getOrientation())
                {
                    case horizontal:
                        current_mouse_pos = Gdx.input.getX();
                        compare = (current_mouse_pos / TILE_SIZE) - last_point.x;
                        System.out.println(currPos.x + ", " +  (current_mouse_pos / TILE_SIZE) + ", " + compare);

                        if (compare >= 1)
                            success = dragging.move(Block.Direction.right);
                        else if (compare <= -1)
                            success = dragging.move(Block.Direction.left);

                        if (success)
                            last_point.x = current_mouse_pos / TILE_SIZE;

                        break;
                    case vertical:
                        current_mouse_pos = 800 - Gdx.input.getY();
                        compare = (current_mouse_pos / TILE_SIZE) - last_point.y;
                        System.out.println(currPos.x + ", " +  (current_mouse_pos / TILE_SIZE) + ", " + compare);

                        if (compare >= 1)
                            success = dragging.move(Block.Direction.up);
                        else if (compare <= -1)
                            success = dragging.move(Block.Direction.down);

                        if (success)
                            last_point.y = current_mouse_pos / TILE_SIZE;

                        break;
                }

                if (!success && compare != 0)
                    System.out.println("Collision!");
            }
            else if(!last_pressed)
            {
                last_pressed = true;

                last_point = new GridPoint2(Gdx.input.getX() / TILE_SIZE,
                        (MyUnblockMe.RESOLUTION - Gdx.input.getY()) / TILE_SIZE);

                dragging = Tiles[last_point.x][last_point.y].getOccupant();
            }
        } else
        {
            last_pressed = false;
            dragging = null;
        }
    }
}
