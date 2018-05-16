package com.wepull.mario;

import java.awt.image.BufferedImage;

public class Obstruction   implements  Runnable{
        private  int  x,y;
        private  int  type;
        private  BufferedImage  showImage;
        private int startType;
		public Obstruction(int x, int y,int type,BackGround  bg) {
			this.x = x;
			this.y = y;
			this.bg=bg;
			this.type=type;
			startType=type;
			setImage();
			
			//�����������ĵ�ʱ��͵����߳�
			if(type == 11){
				new Thread(this).start();
			}
		}
        
		public  void  setImage(){
			showImage=StaticValue.allObstructionImage.get(type);
		}
		
		//���Ƶķ���
		public  void  reset(){
			type=startType;
			setImage();
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public BufferedImage getShowImage() {
			return showImage;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
        BackGround  bg;
		public void run() {
			 while(true){
				 try {
					 //ʲôʱ��ʼ�½�.��mario��ϵͳ���Ƶ�ʱ��
					 if(this.bg.isOver()){
						 //��ʼ����
						 if(this.y<420){
							 y+=5;
						 }
						 if(this.y>=420){
							 y=420;
							 //��ʾ��������½�
							 this.bg.setDown(true);
						 }
					 }
					 
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }
		}
        
}
