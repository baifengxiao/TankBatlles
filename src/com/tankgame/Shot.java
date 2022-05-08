package com.tankgame;

/**
 * @author: baifengxiao
 * @date: 2022/2/20 22:07
 */
public class Shot implements Runnable {
    private int x;
    private int y;
    private int direct = 0;
    private int speed = 20;
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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

    @Override
    public void run() {
        while (true) {

            //子弹休眠
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变x,y坐标
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    y += speed;
                    break;
                case 2:
                    x -= speed;
                    break;
                case 3:
                    x += speed;
                    break;
            }
            //测试，输出x,y坐标
            System.out.println("子弹 x=" + x + "y=" + y);
            //碰到敌人坦克也应该结束线程
            if (!(x >- 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)){
                isLive = false;
                break;
            }
        }
    }
}
