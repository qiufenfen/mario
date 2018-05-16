package com.wepull.mario;

import java.awt.image.BufferedImage;

public class Mario   implements  Runnable{
	private int x, y;
	private String status;
	private BufferedImage showImage;
	//定义分数和命数
	private int score,life;
    //定义横向的速度
	private  int  xmove;
	//持有当前的场景
	BackGround  bg;
	//定义存活的变量
	boolean  live;
	//定义游戏结束的变量
	boolean  isOK;
	public Mario(int x, int y) {
		this.x = x;
		this.y = y;
		status = "right-standing";
		showImage = StaticValue.allMarioImage.get(0);
		live=true;
		//实现线程
		new Thread(this).start();
	}
	//定义上升的时间和纵向的坐标
	private  int  uptime;
	private  int  ymove;
	//死亡的方法
	public  void  dead(){
		life--;
		if(life==0){
		   live=false;
		}else{
			//场景和所有的障碍物以及敌人需要重新的绘制
			this.reset();
			//将所有都剖坏的东西从新的装入到集合中
			//两个for
			bg.reset();
			for(int i=0;i<this.bg.getAllObstruction().size();i++){
				 Obstruction obs=this.bg.getAllObstruction().get(i);
			     obs.reset();
			}
			for(int i=0;i<this.bg.getAllEnemy().size();i++){
				Enemy e=this.bg.getAllEnemy().get(i);
				e.reset();
			}
		}
	}
	//绘制的方法
	public  void reset(){
		showImage=StaticValue.allMarioImage.get(0);
		x=0;
		y=480;
	}
	//上升
	public  void  jump(){
		if(status.indexOf("jumping")==-1){
			  //判断是朝左的
			if(status.indexOf("left")!=-1){
				status="left-jumping";
			}else{
				status="right-jumping";
			}
		}
		uptime=3*60/5;
		ymove=-5;
	}
	//下降
	public  void  down(){
		  //判断是朝左的
		if(status.indexOf("left")!=-1){
			status="left-jumping";
		}else{
			status="right-jumping";
		}
		ymove=5;
	}
	
	public  void  leftmove(){
		status="left-moving";
		xmove=-5;
	}
	
	public  void  leftstop(){
		status="left-standing";
		xmove=0;
	}
	public  void  rightmove(){
		status="right-moving";
		xmove=5;
	}
	public  void  rightstop(){
		status="right-stoping";
		xmove=0;
	}
	private  int  moving=0;//朝一个方向走的次数
	public void run() {
		 while(true){
			 //如果是最后一个场景
			 if(this.bg.isFlag() && this.x >=520){
				 this.bg.setOver(true);
				 //表示旗帜完成下降
				 if(bg.isDown()){
						   if(y<480){
							   y+=5;
						   }
						   if(x>=780){
							   //游戏通关
							   this.isOK=true;
						   }
						   x+=5;
						   status="right-moving";
					  
				 }else{
					 //mario由系统自动的调用
					   if(this.y<=420){
						   y+=5;
					   }
					   if(this.y>420){
						   y=420;
						   //表示在砖块上面
						   status="right-standing";
					   }
				 }
			}else{
			 //定义标记
			 boolean  canleft=true;//可以左
			 boolean  canright=true;//可以右
			 boolean  onload=false;//不在障碍物上面
			 
			 //处理死亡
			 //得到所有的敌人
			 for(int i=0;i<this.bg.getAllEnemy().size();i++){
				  Enemy  e=this.bg.getAllEnemy().get(i);
				  //判断横向的
				  if(x>e.getX()-60 && x<e.getX()+60 && y>e.getY()-40 && y<e.getY()+40){
					     this.dead();
				  }
				  //判断纵向
				  if(y>e.getY()-60&& y<e.getY()+60&& x>e.getX()-60&&x<e.getX()+60){
					    //判断敌人的类型
					  if(e.getType() == 1){
						   //是普通的敌人
						  e.dead();
						  //希望自己上升一点空间
						  uptime=10;
						  ymove=-5;
					  }
					  if(e.getType() == 2){
						  //食人花
						  this.dead();
					  }
				  }
				  
			 }
			 
			 //得到所有的障碍物
			for(int i=0;i< this.bg.getAllObstruction().size();i++){
				Obstruction  obs =this.bg.getAllObstruction().get(i);
			    //不能向右走
				if(x+60 == obs.getX() && y>obs.getY()-40 && y<obs.getY()+40){
					canright=false;
				}
				//不能向左走
				if(x-60 == obs.getX() && y>obs.getY()-40 && y<obs.getY()+40){
					canleft=false;
				}
			    //在障碍物上
				if(y+60==obs.getY()&& x>obs.getX()-60&&x<obs.getX()+60){
					onload=true;
				}
				//顶着障碍物
				if(y-60==obs.getY()&& x>obs.getX()-40&&x<obs.getX()+40){
				     //障碍物是砖块
					 if(obs.getType() == 0){
						 bg.allObstruction.remove(obs);
						 bg.removedObstruction.add(obs);
					 }
					 //障碍物是？
					 if(obs.getType() == 4){
						 obs.setType(2);
						 obs.setImage();
					 }
					
					//不能继续上升
					uptime=0;
				}
			}
			//判断是否在障碍物上
			if(onload &&uptime ==0){
				    //判断向左
				   if(status.indexOf("left")!=-1){
					   if(xmove!=0){
						   status="left-moving";
					   }else{
						   status="left-standing";
					   }
				   }else{
					   if(xmove!=0){
						   status="right-moving";
					   }else{
						   status="right-standing";
					   }
					  
				   }
				
				MyFrame.flag=true;
			}else{
				//不在障碍物上面
				if(uptime!=0){
					 //上升
					uptime--;
				}else{
					//下降
					this.down();
				}
				MyFrame.flag=false;
				y+=ymove;
				//判断Y
				if(y>600){
					this.dead();
				}
			}
			
			
			
			//判断是否可以改变x坐标
			if(canleft && xmove <0 || canright && xmove >0){
				x+=xmove;
				//解决出界的问题
				if(x<=0){
					x=0;
				}
			}
			}
			 //线程+切换图片在外面
			 
			 //定义取得切换图片的索引
			 int  index=0;
			 //判断向左
			 if(status.indexOf("left")!=-1){
				 index+=5;
			}
			 //移动
			 if(status.indexOf("moving")!=-1){
				 index+=moving;
				 moving++;
				 if(moving == 4){
					 moving=0;
				 }
			 }
			 if(status.indexOf("jumping")!=-1 && status.indexOf("left")!=-1){
				 showImage=StaticValue.allMarioImage.get(9);
			 }else if(status.indexOf("jumping")!=-1 && status.indexOf("right")!=-1){
				 showImage=StaticValue.allMarioImage.get(4);
			 }else{
				 showImage=StaticValue.allMarioImage.get(index);
			 }
			 
			 try {
				Thread.sleep(20);
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
	public void setBg(BackGround bg) {
		this.bg = bg;
	}
	public boolean isLive() {
		return live;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isOK() {
		return isOK;
	}

	

}
