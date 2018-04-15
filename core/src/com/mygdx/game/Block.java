package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Block {
    public enum Orientation
    {
        horizontal,
        vertical
    }

    public enum Type
    {
        simple,
        key
    }

    public enum Direction
    {
        up, down, left, right
    }

    private GridPoint2 position;
    private int length;
    private Orientation orientation;
    private Type type;
    private Sprite sprite;
    private UnblockBoard board;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Block(GridPoint2 position, int length, Orientation orientation, Type type, Texture texture,
                 UnblockBoard board) throws Exception
    {
        this.length = length;
        this.orientation = orientation;
        this.type = type;

        this.sprite = new Sprite(texture);
        this.sprite.setPosition(position.x * UnblockBoard.TILE_SIZE, position.y * UnblockBoard.TILE_SIZE);
        this.board = board;

        switch (orientation)
        {
            case vertical:
                this.sprite.setSize(UnblockBoard.TILE_SIZE, length * UnblockBoard.TILE_SIZE);
                break;
            case horizontal:
                this.sprite.setSize(length * UnblockBoard.TILE_SIZE,  UnblockBoard.TILE_SIZE);
                break;
        }

        switch (type)
        {
            case key:
                this.sprite.setColor(Color.RED);
                break;
            case simple:
                this.sprite.setColor(Color.WHITE);
                break;
        }


        setPosition(position);
    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public void setPosition(GridPoint2 position) throws Exception
    {
        ArrayList<Tile> oldTiles = new ArrayList<Tile>();
        ArrayList<Tile> newTiles = new ArrayList<Tile>();

        for (int i = 0; i < length; i++) {
            switch (orientation) {
                case horizontal:
                    if (this.position != null)
                        oldTiles.add(board.getTile(this.position.x+i, this.position.y));
                    newTiles.add(board.getTile(position.x+i, position.y));
                    break;
                case vertical:
                    if (this.position != null)
                        oldTiles.add(board.getTile(this.position.x, this.position.y + i));
                    newTiles.add(board.getTile(position.x, position.y + i));
                    break;
            }
        }

        for (Tile t : newTiles ) {
            if (t.isOccupied() && t.getOccupant() != this)
                throw new Exception();
        }

        for (Tile t : newTiles)
        {
            t.tryOccupy(this);
        }

        for (Tile t: oldTiles)
        {
            t.removeOccupant();
        }

        this.position = position;
        this.sprite.setPosition(position.x * UnblockBoard.TILE_SIZE, position.y * UnblockBoard.TILE_SIZE);
    }

    public boolean move(Direction direction) throws IllegalArgumentException
    {
        try {
            if (orientation == Orientation.horizontal && (direction == Direction.up || direction == Direction.down))
                throw new IllegalArgumentException("Can't move up or down as horizontal block");
            else if (orientation == Orientation.vertical && (direction == Direction.left || direction == Direction.right))
                throw new IllegalArgumentException("Can't move left or right as vertical block");

            Tile newTile, leavingTile;
            GridPoint2 new_pos;
            switch (direction) {
                case up:
                    newTile = board.getTile(position.x, position.y + length);
                    leavingTile = board.getTile(position.x, position.y);
                    new_pos = new GridPoint2(position.x, position.y + 1);
                    break;
                case down:
                    newTile = board.getTile(position.x, position.y - 1);
                    leavingTile = board.getTile(position.x, position.y + length - 1);
                    new_pos = new GridPoint2(position.x, position.y - 1);
                    break;
                case left:
                    newTile = board.getTile(position.x - 1, position.y);
                    leavingTile = board.getTile(position.x + length - 1, position.y);
                    new_pos = new GridPoint2(position.x - 1, position.y);
                    break;
                case right:
                    newTile = board.getTile(position.x + length, position.y);
                    leavingTile = board.getTile(position.x, position.y);
                    new_pos = new GridPoint2(position.x + 1, position.y);
                    break;
                default:
                    return false;
            }

            if (newTile.isOccupied())
                return false;

            newTile.tryOccupy(this);
            leavingTile.removeOccupant();
            this.position = new_pos;
            this.sprite.setPosition(position.x * UnblockBoard.TILE_SIZE, position.y * UnblockBoard.TILE_SIZE);

            return true;
        } catch (ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
    }

    public GridPoint2 getPosition()
    {
        return position;
    }

    public int getLength()
    {
        return length;
    }

    public Type getType()
    {
        return type;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
