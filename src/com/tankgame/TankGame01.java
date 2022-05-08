package com.tankgame;

import javax.swing.*;

/**
 * @author: baifengxiao
 * @date: 2022/2/18 22:22
 */
//游戏区域绘制
public class TankGame01 extends JFrame {

    //定义MyPanel(游戏区域)
    MyPanel mp = null;
    public static void main(String[] args) {

        TankGame01 tankGame01 = new TankGame01();
    }

    public TankGame01(){
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
//        this.setSize(1000,750);
        this.setSize(1030,810);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
