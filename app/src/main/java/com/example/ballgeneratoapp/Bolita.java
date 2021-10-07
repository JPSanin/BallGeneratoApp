package com.example.ballgeneratoapp;

public class Bolita {

    private String grupo;
    private int x,y, tipo, velx,vely;
    private boolean stop;


    public Bolita(String grupo, int x, int y, int tipo, int velx, int vely) {
        this.grupo = grupo;
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.velx = velx;
        this.vely = vely;

        stop= false;
    }

    public Bolita(){

    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getVelx() {
        return velx;
    }

    public void setVelx(int velx) {
        this.velx = velx;
    }

    public int getVely() {
        return vely;
    }

    public void setVely(int vely) {
        this.vely = vely;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
