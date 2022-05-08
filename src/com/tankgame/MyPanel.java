package com.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author: baifengxiao
 * @date: 2022/2/18 22:15
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我方坦克
    Hero hero = null;
    //定义敌人坦克，放入到Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个Vector,用于存放炸弹(爆炸效果)
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel() {
        //初始化自己的坦克
        hero = new Hero(500, 100);
        hero.setSpeed(3);
        //初始化敌人坦克
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            enemyTank.setDirect(1);
            //启动敌人坦克线程，让他动起来
            new Thread(enemyTank).start();
            //给敌方坦克加上一颗子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            enemyTank.shots.add(shot);
            new Thread(shot).start();
            enemyTanks.add(enemyTank);
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    //游戏背景填充
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);

        //画坦克
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //画出Hero射击的子弹
//        if (hero.shot != null && hero.shot.isLive == true) {
//            g.draw3DRect(hero.shot.getX(), hero.shot.getY(), 3, 3, false);
//        }
        //升级，遍历集合中的子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                g.draw3DRect(shot.getX(), shot.getY(), 3, 3, false);
            } else {
                hero.shots.remove(shot);
            }
        }
        //bombs有炸弹就画
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据life值画对应图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让炸弹生命值减少
            bomb.lifeDown();
            //如果bombLife为0，就从bombs的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出敌人的坦克，遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌方坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否存活
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出所有坦克子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }

    /**
     * @param x
     * @param y
     * @param g      画笔
     * @param direct 坦克方向
     * @param type   坦克类型
     */
    //编写方法，画出坦克
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        switch (type) {
            case 0: //敌人坦克
                g.setColor(Color.CYAN);
                break;
            case 1: //我的坦克
                g.setColor(Color.ORANGE);
                break;
        }

        //根据坦克方向，来绘制对应坦克
        //0 向上， 1 向下，2 向左， 3 向右
        switch (direct) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false);//左轮
                g.fill3DRect(x + 30, y, 10, 60, false);//右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间方形
                g.fillOval(x + 10, y + 20, 20, 20);//小圆圈
                g.drawLine(x + 20, y + 30, x + 20, y);//炮筒
                break;
            case 1:
                g.fill3DRect(x, y, 10, 60, false);//左轮
                g.fill3DRect(x + 30, y, 10, 60, false);//右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//中间方形
                g.fillOval(x + 10, y + 20, 20, 20);//小圆圈
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//炮筒
                break;
            case 2:
                g.fill3DRect(x, y, 60, 10, false);//左轮
                g.fill3DRect(x, y + 30, 60, 10, false);//右轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间方形
                g.fillOval(x + 20, y + 10, 20, 20);//小圆圈
                g.drawLine(x + 30, y + 20, x, y + 20);//炮筒
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);//左轮
                g.fill3DRect(x, y + 30, 60, 10, false);//右轮
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//中间方形
                g.fillOval(x + 20, y + 10, 20, 20);//小圆圈
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    public void hitEnemyTank() {

        //遍历子弹集合
        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);
            //判断是否击中了敌人坦克
            if (shot != null && shot.isLive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }

    }

    //敌人击中我方坦克
    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                //判断是否击中我方坦克
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }


    //判断是否击中某个坦克
    public void hitTank(Shot heroShot, Tank tank) {
        switch (tank.getDirect()) {
            case 0:
            case 1:
                if (heroShot.getX() > tank.getX() && heroShot.getX() < tank.getX() + 40 && heroShot.getY() > tank.getY() && heroShot.getY() < tank.getY() + 60) {
                    heroShot.isLive = false;
                    tank.isLive = false;
                    //子弹击中坦克时，移除该坦克
                    enemyTanks.remove(tank);
                    //击中坦克后，将enemyTank从Vector中移除
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 2:
            case 3:
                if (heroShot.getX() > tank.getX() && heroShot.getX() < tank.getX() + 60 && heroShot.getY() > tank.getY() && heroShot.getY() < tank.getY() + 40) {
                    heroShot.isLive = false;
                    tank.isLive = false;
                    enemyTanks.remove(tank);
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //改变坦克的方向
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(1);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(2);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(3);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        }

        //按J发射子弹,就发射
        if (e.getKeyCode() == KeyEvent.VK_J) {

            //判度子弹是否销毁(单颗子弹)
//            if (hero.shot == null || !hero.shot.isLive) {
//                hero.shotEnemyTank();
//            }
            hero.shotEnemyTank();
        }
        //不停重绘面板
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            hitHero();
            hitEnemyTank();
            this.repaint();
        }

    }
}
