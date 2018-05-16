package com.wepull.mario;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author 
 * 处理游戏开始
 */
public class MyFrame extends JFrame  implements  Runnable {
	List<BackGround>  allbg=new ArrayList<BackGround>();
	BackGround  nowbg;
	Mario  m;
	boolean  isStart=false;
	//是否可以跳跃
	static boolean  flag=true;
	 public  MyFrame(){
		 this.setTitle("我的马里奥");
		 this.setSize(900,600);
		 //初始化所有的图片
		 StaticValue.init();
		 //初始化所有的场景
		 for(int i=1;i<=3;i++){
			 allbg.add(new BackGround(i,i==3?true:false));
		 } 
		 //初始化当前的场景
		 nowbg=allbg.get(0);
		 //初始化mario
		 m=new Mario(0,480);
		 //为马里奥设置场景
		 m.setBg(nowbg);
		 m.setScore(0);
		 m.setLife(3);
		 //实现线程
		 new Thread(this).start();
		 //实现键盘的监听：
		 this.addKeyListener(new MyKeyListener());
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
	 }
	public void paint(Graphics g) {
		BufferedImage  image=new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2=image.getGraphics();
	if(isStart){
		g2.drawImage(nowbg.getShowImage(), 0, 0, this);
		 //绘制所有的敌人
        Iterator<Enemy> iters=this.nowbg.getAllEnemy().iterator();
		while(iters.hasNext()){
			Enemy  e=iters.next();
			g2.drawImage(e.getShowImage(),e.getX(), e.getY(), this);
		}
		
		//取得所有的障碍物
		Iterator<Obstruction> iter=this.nowbg.getAllObstruction().iterator();
        while(iter.hasNext()){
        	Obstruction  obs=iter.next();
        	g2.drawImage(obs.getShowImage(), obs.getX(),obs.getY(), this);
        }	
        //绘制mario
        g2.drawImage(m.getShowImage(),m.getX(), m.getY(), this);
	}else{
		//游戏没有开始
		g2.drawImage(StaticValue.startImage, 0, 0,this);
	}
        g.drawImage(image, 0, 0, this);
	}
	
	private  class  MyKeyListener  implements KeyListener{
		public void keyPressed(KeyEvent e) {
			int  key=e.getKeyCode();
		if(isStart){	
				if(key == KeyEvent.VK_LEFT){
					m.leftmove();
				}
				if(key == KeyEvent.VK_RIGHT){
					m.rightmove();
				}
				if(key ==KeyEvent.VK_SPACE){
					if(flag)
					m.jump();
				}
		 }else{
			  if(key ==KeyEvent.VK_SPACE){
				     isStart=true; 
				     nowbg.enemyMove();
				}
		}
		}
		public void keyReleased(KeyEvent e) {
			int  key=e.getKeyCode();
		 if(isStart){	
			if(key == KeyEvent.VK_LEFT){
				m.leftstop();
			}
			if(key == KeyEvent.VK_RIGHT){
				m.rightstop();
			}
		 }
		}
		public void keyTyped(KeyEvent arg0) {
			
		}
		
	}
	  public void run() {
			while(true){
				try {
					Thread.sleep(50);
                    //切换场景
					if(m.getX() >840){
						 int s=this.nowbg.getSort();
					     this.nowbg=this.allbg.get(s);
					     m.setBg(nowbg);
					     m.setX(0);
					     nowbg.enemyMove();
					     
					}
					//判断马里奥是否存活
					if(!m.isLive()){
						JOptionPane.showMessageDialog(null,"mario死亡,游戏结束!");
					    System.exit(0);
					 }
//					/判断通关
					if(m.isOK){
						JOptionPane.showMessageDialog(null,"游戏通关!");
						System.exit(0);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}	
		}
	 public static void main(String[] args) {
		new MyFrame();
	}
	
}
