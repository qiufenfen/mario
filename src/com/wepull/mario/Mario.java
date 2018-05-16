package com.wepull.mario;

import java.awt.image.BufferedImage;

public class Mario   implements  Runnable{
	private int x, y;
	private String status;
	private BufferedImage showImage;
	//�������������
	private int score,life;
    //���������ٶ�
	private  int  xmove;
	//���е�ǰ�ĳ���
	BackGround  bg;
	//������ı���
	boolean  live;
	//������Ϸ�����ı���
	boolean  isOK;
	public Mario(int x, int y) {
		this.x = x;
		this.y = y;
		status = "right-standing";
		showImage = StaticValue.allMarioImage.get(0);
		live=true;
		//ʵ���߳�
		new Thread(this).start();
	}
	//����������ʱ������������
	private  int  uptime;
	private  int  ymove;
	//�����ķ���
	public  void  dead(){
		life--;
		if(life==0){
		   live=false;
		}else{
			//���������е��ϰ����Լ�������Ҫ���µĻ���
			this.reset();
			//�����ж��ʻ��Ķ������µ�װ�뵽������
			//����for
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
	//���Ƶķ���
	public  void reset(){
		showImage=StaticValue.allMarioImage.get(0);
		x=0;
		y=480;
	}
	//����
	public  void  jump(){
		if(status.indexOf("jumping")==-1){
			  //�ж��ǳ����
			if(status.indexOf("left")!=-1){
				status="left-jumping";
			}else{
				status="right-jumping";
			}
		}
		uptime=3*60/5;
		ymove=-5;
	}
	//�½�
	public  void  down(){
		  //�ж��ǳ����
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
	private  int  moving=0;//��һ�������ߵĴ���
	public void run() {
		 while(true){
			 //��������һ������
			 if(this.bg.isFlag() && this.x >=520){
				 this.bg.setOver(true);
				 //��ʾ��������½�
				 if(bg.isDown()){
						   if(y<480){
							   y+=5;
						   }
						   if(x>=780){
							   //��Ϸͨ��
							   this.isOK=true;
						   }
						   x+=5;
						   status="right-moving";
					  
				 }else{
					 //mario��ϵͳ�Զ��ĵ���
					   if(this.y<=420){
						   y+=5;
					   }
					   if(this.y>420){
						   y=420;
						   //��ʾ��ש������
						   status="right-standing";
					   }
				 }
			}else{
			 //������
			 boolean  canleft=true;//������
			 boolean  canright=true;//������
			 boolean  onload=false;//�����ϰ�������
			 
			 //��������
			 //�õ����еĵ���
			 for(int i=0;i<this.bg.getAllEnemy().size();i++){
				  Enemy  e=this.bg.getAllEnemy().get(i);
				  //�жϺ����
				  if(x>e.getX()-60 && x<e.getX()+60 && y>e.getY()-40 && y<e.getY()+40){
					     this.dead();
				  }
				  //�ж�����
				  if(y>e.getY()-60&& y<e.getY()+60&& x>e.getX()-60&&x<e.getX()+60){
					    //�жϵ��˵�����
					  if(e.getType() == 1){
						   //����ͨ�ĵ���
						  e.dead();
						  //ϣ���Լ�����һ��ռ�
						  uptime=10;
						  ymove=-5;
					  }
					  if(e.getType() == 2){
						  //ʳ�˻�
						  this.dead();
					  }
				  }
				  
			 }
			 
			 //�õ����е��ϰ���
			for(int i=0;i< this.bg.getAllObstruction().size();i++){
				Obstruction  obs =this.bg.getAllObstruction().get(i);
			    //����������
				if(x+60 == obs.getX() && y>obs.getY()-40 && y<obs.getY()+40){
					canright=false;
				}
				//����������
				if(x-60 == obs.getX() && y>obs.getY()-40 && y<obs.getY()+40){
					canleft=false;
				}
			    //���ϰ�����
				if(y+60==obs.getY()&& x>obs.getX()-60&&x<obs.getX()+60){
					onload=true;
				}
				//�����ϰ���
				if(y-60==obs.getY()&& x>obs.getX()-40&&x<obs.getX()+40){
				     //�ϰ�����ש��
					 if(obs.getType() == 0){
						 bg.allObstruction.remove(obs);
						 bg.removedObstruction.add(obs);
					 }
					 //�ϰ����ǣ�
					 if(obs.getType() == 4){
						 obs.setType(2);
						 obs.setImage();
					 }
					
					//���ܼ�������
					uptime=0;
				}
			}
			//�ж��Ƿ����ϰ�����
			if(onload &&uptime ==0){
				    //�ж�����
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
				//�����ϰ�������
				if(uptime!=0){
					 //����
					uptime--;
				}else{
					//�½�
					this.down();
				}
				MyFrame.flag=false;
				y+=ymove;
				//�ж�Y
				if(y>600){
					this.dead();
				}
			}
			
			
			
			//�ж��Ƿ���Ըı�x����
			if(canleft && xmove <0 || canright && xmove >0){
				x+=xmove;
				//������������
				if(x<=0){
					x=0;
				}
			}
			}
			 //�߳�+�л�ͼƬ������
			 
			 //����ȡ���л�ͼƬ������
			 int  index=0;
			 //�ж�����
			 if(status.indexOf("left")!=-1){
				 index+=5;
			}
			 //�ƶ�
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
