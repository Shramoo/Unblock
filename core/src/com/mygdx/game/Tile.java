package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

public class Tile {
    public enum Type {
        field, border, lock
    }

    private Block occupant = null;
    private Sprite sprite = new Sprite();
    private GridPoint2 position;
    private Type type;

    public Tile(GridPoint2 position, Type type, Texture texture, float size)
    {
        this.position = position;
        this.type = type;
        this.sprite.setTexture(texture);
        this.sprite.setPosition(position.x * size, position.y * size);
        this.sprite.setSize(size, size);

        switch (type)
        {
            case lock:
                this.sprite.setColor(Color.YELLOW);
                break;
            case field:
                this.sprite.setColor(Color.GRAY);
                break;
            case border:
                this.sprite.setColor(Color.BLACK);
        }
    }

    public void draw(SpriteBatch batch)
    {
        /*if (isOccupied())
            sprite.setColor(Color.GREEN);
        else
            sprite.setColor(Color.GRAY);*/

        sprite.draw(batch);
    }

    public boolean isOccupied()
    {
        return occupant != null || type == Type.border;
    }

    public Block getOccupant()
    {
        return occupant;
    }

    public boolean tryOccupy(Block block)
    {
        if (isOccupied() || type == Type.border)
            return false;

        occupant = block;
        return true;
    }

    public void removeOccupant()
    {
        occupant = null;
    }

    public GridPoint2 getPosition()
    {
        return position;
    }

}
