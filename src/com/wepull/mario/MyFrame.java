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
 * ������Ϸ��ʼ
 */
public class MyFrame extends JFrame  implements  Runnable {
	List<BackGround>  allbg=new ArrayList<BackGround>();
	BackGround  nowbg;
	Mario  m;
	boolean  isStart=false;
	//�Ƿ������Ծ
	static boolean  flag=true;
	 public  MyFrame(){
		 this.setTitle("�ҵ������");
		 this.setSize(900,600);
		 //��ʼ�����е�ͼƬ
		 StaticValue.init();
		 //��ʼ�����еĳ���
		 for(int i=1;i<=3;i++){
			 allbg.add(new BackGround(i,i==3?true:false));
		 } 
		 //��ʼ����ǰ�ĳ���
		 nowbg=allbg.get(0);
		 //��ʼ��mario
		 m=new Mario(0,480);
		 //Ϊ��������ó���
		 m.setBg(nowbg);
		 m.setScore(0);
		 m.setLife(3);
		 //ʵ���߳�
		 new Thread(this).start();
		 //ʵ�ּ��̵ļ�����
		 this.addKeyListener(new MyKeyListener());
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
	 }
	public void paint(Graphics g) {
		BufferedImage  image=new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2=image.getGraphics();
	if(isStart){
		g2.drawImage(nowbg.getShowImage(), 0, 0, this);
		 //�������еĵ���
        Iterator<Enemy> iters=this.nowbg.getAllEnemy().iterator();
		while(iters.hasNext()){
			Enemy  e=iters.next();
			g2.drawImage(e.getShowImage(),e.getX(), e.getY(), this);
		}
		
		//ȡ�����е��ϰ���
		Iterator<Obstruction> iter=this.nowbg.getAllObstruction().iterator();
        while(iter.hasNext()){
        	Obstruction  obs=iter.next();
        	g2.drawImage(obs.getShowImage(), obs.getX(),obs.getY(), this);
        }	
        //����mario
        g2.drawImage(m.getShowImage(),m.getX(), m.getY(), this);
	}else{
		//��Ϸû�п�ʼ
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
                    //�л�����
					if(m.getX() >840){
						 int s=this.nowbg.getSort();
					     this.nowbg=this.allbg.get(s);
					     m.setBg(nowbg);
					     m.setX(0);
					     nowbg.enemyMove();
					     
					}
					//�ж�������Ƿ���
					if(!m.isLive()){
						JOptionPane.showMessageDialog(null,"mario����,��Ϸ����!");
					    System.exit(0);
					 }
//					/�ж�ͨ��
					if(m.isOK){
						JOptionPane.showMessageDialog(null,"��Ϸͨ��!");
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
