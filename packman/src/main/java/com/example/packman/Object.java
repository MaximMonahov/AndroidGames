package com.example.packman;

/**
 * Created by Максим on 04.09.2017.
 */
public class Object {

    protected int m_type;
    protected int m_dir;
    protected Pos m_pos = new Pos();

    Object()
    {
    }

    Object(int x, int y)
    {
        m_pos.x = x;
        m_pos.y = y;
    }

    public int getType() {
        return m_type;
    }

    public void setType(int m_type) {
        this.m_type = m_type;
    }

    public int getDir() {
        return m_dir;
    }

    public void setDir(int m_dir) {
        this.m_dir = m_dir;
    }

    public Pos getPos() {
        return m_pos;
    }

    public void setPos(Pos m_pos) {
        this.m_pos = m_pos;
    }
}
