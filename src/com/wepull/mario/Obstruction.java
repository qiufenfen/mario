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
			
			//当类型是旗帜的时候就调用线程
			if(type == 11){
				new Thread(this).start();
			}
		}
        
		public  void  setImage(){
			showImage=StaticValue.allObstructionImage.get(type);
		}
		
		//绘制的方法
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
					 //什么时候开始下降.当mario由系统控制的时候
					 if(this.bg.isOver()){
						 //开始降旗
						 if(this.y<420){
							 y+=5;
						 }
						 if(this.y>=420){
							 y=420;
							 //表示旗帜完成下降
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
