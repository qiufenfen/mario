package com.wepull.mario;

import java.awt.image.BufferedImage;

/**
 * @author wolves
 */
public class Enemy   implements  Runnable{
         private  int  x,y;
         private  int  type;//到底是什么敌人
         private  boolean isleftOrup=true;//食人花是向上的.普通的敌人是默能是向左移动
         private  int   upmax,downmax;
         private  BufferedImage  showImage;
         private  int startX,startY;
         private Thread  t=new Thread(this);
         BackGround  bg;
         //普通敌人的构造器
		public Enemy(int x, int y, int type, boolean isleftOrup,BackGround  bg) {
			this.x = x;
			this.y = y;
			this.type = type;
			this.bg=bg;
			this.startX=x;
			this.startY=y;
			this.isleftOrup = isleftOrup;
			if(type==1){
				showImage=StaticValue.allTriangleImage.get(0);
			}
			//实现监听
			t.start();
			//挂起
			t.suspend();
		}
		
		//食人花的构造器
		public Enemy(int x, int y, int type, boolean isleftOrup, int upmax,
				int downmax,BackGround  bg) {
			this.x = x;
			this.y = y;
			this.bg=bg;
			this.type = type;
			this.startX=x;
			this.startY=y;
			this.isleftOrup = isleftOrup;
			this.upmax = upmax;
			this.downmax = downmax;
			if(type==2){
			  showImage=StaticValue.allFlowerImage.get(0);
			}
			//实现监听
			t.start();
			//挂起
			t.suspend();
		}
		//卸载挂起
		public  void move(){
            t.resume();			
		}
		//绘制的方法
		public void reset(){
			x=startX;
			y=startY;
		    showImage=StaticValue.allTriangleImage.get(0);
		}
		//敌人死亡
		public  void  dead(){
            showImage=StaticValue.allTriangleImage.get(2);
            this.bg.getAllEnemy().remove(this);
            this.bg.removedEnemy.add(this);
		}
       //定义一个标记
		private  int  imageType=0;
		public void run() {
			while(true){
				try {
					//判断是否可以向左和向右
					 boolean  canleft=true;//可以左
					 boolean  canright=true;//可以右
					 //得到所有的障碍物
					for(int i=0;i< this.bg.getAllObstruction().size();i++){
						Obstruction  obs =this.bg.getAllObstruction().get(i);
						// java.lang.NullPointerException
						//当界面上有两个普通的敌人的时候就会出现空指针
						//但是多执行几次中偶尔没有或者打印输出也没有
						//不能向右走
						if(x+60 == obs.getX() && y>obs.getY()-60 && y<obs.getY()+60){
							canright=false;
						}
						//不能向左走
						if(x-60 == obs.getX() && y>obs.getY()-60 && y<obs.getY()+60){
							canleft=false;
						}
					}
					
					//判断敌人的类型
					if(type ==1){
						//正在向左走但是不能向左走  就改变方向
						if(isleftOrup&&!canleft|| x==0){
							 isleftOrup=false;
						}
						if(!isleftOrup&&!canright|| x==840){
							 isleftOrup=true;
						}
						//判断方向
						if(isleftOrup){
							//向左
							x-=2;
						}else{
							x+=2;
						}
						 //说明是普通的敌人
						//走起来的感觉
						if(imageType ==0){
							imageType=1;
						}else{
						    imageType=0;
						}
						showImage=StaticValue.allTriangleImage.get(imageType);
					}
					if(type == 2){
						//说明是食人花
						//判断上线和下限
						if(isleftOrup&&this.y==this.upmax){
							isleftOrup=false;
						}
						if(!isleftOrup && this.y == this.downmax){
							isleftOrup=true;
						}
						//判断方向
						if(isleftOrup){
							//向上
							y-=2;
						}else{
							y+=2;
						}
						//走起来的感觉
						if(imageType ==0){
							imageType=1;
						}else{
						    imageType=0;
						}
						showImage=StaticValue.allFlowerImage.get(imageType);
					}
					
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
		
		
		
         
         
         
         
         
         
}
