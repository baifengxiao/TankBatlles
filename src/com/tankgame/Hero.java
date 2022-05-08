package com.tankgame;

import java.util.Vector;

/**
 * @author: baifengxiao
 * @date: 2022/2/18 18:48
 */
public class Hero extends Tank {
    //定义一个Shot对象,表示一个射击（线程）
    Shot shot = null;
    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    //坦克坐标
    public Hero(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemyTank() {
        if (shots.size()==5){
            return;
        }
        switch (getDirect()) {
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 20, getY() + 60, 1);
                break;
            case 2:
                shot = new Shot(getX(), getY() + 20, 2);
                break;
            case 3:
                shot = new Shot(getX() + 60, getY() + 20, 3);
                break;
        }
        shots.add(shot);
        //启动Shot线程
        new Thread(shot).start();
    }
}
