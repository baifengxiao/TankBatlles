package com.tankgame;

import java.util.Vector;

/**
 * @author: baifengxiao
 * @date: 2022/2/19 1:40
 */
@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {
    //在敌人坦克类，使用Vector保存多个子弹
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {

            //判断敌人子弹状态,没子弹了就创建一颗子弹，放入到集合中
            if (isLive && shots.size() < 1) {

                Shot s = null;
                //判断坦克方向，用于创建子弹
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 20, getY() + 60, 1);
                        break;
                    case 2:
                        s = new Shot(getX(), getY() + 20, 2);
                        break;
                    case 3:
                        s = new Shot(getX() + 60, getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }
            //根据坦克的方向来继续移动
            switch (getDirect()) {
                case 0:
                    //保持一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0) {
                            moveUp();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:

                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                        }
                        //休眠
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }


            //然后随机改变坦克方向
            setDirect((int) (Math.random() * 4));
            //线程结束条件
            if (!isLive) {
                break;
            }
        }
    }
}
