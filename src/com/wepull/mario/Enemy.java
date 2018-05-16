package com.wepull.mario;

import java.awt.image.BufferedImage;

/**
 * @author wolves
 */
public class Enemy   implements  Runnable{
         private  int  x,y;
         private  int  type;//������ʲô����
         private  boolean isleftOrup=true;//ʳ�˻������ϵ�.��ͨ�ĵ�����Ĭ���������ƶ�
         private  int   upmax,downmax;
         private  BufferedImage  showImage;
         private  int startX,startY;
         private Thread  t=new Thread(this);
         BackGround  bg;
         //��ͨ���˵Ĺ�����
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
			//ʵ�ּ���
			t.start();
			//����
			t.suspend();
		}
		
		//ʳ�˻��Ĺ�����
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
			//ʵ�ּ���
			t.start();
			//����
			t.suspend();
		}
		//ж�ع���
		public  void move(){
            t.resume();			
		}
		//���Ƶķ���
		public void reset(){
			x=startX;
			y=startY;
		    showImage=StaticValue.allTriangleImage.get(0);
		}
		//��������
		public  void  dead(){
            showImage=StaticValue.allTriangleImage.get(2);
            this.bg.getAllEnemy().remove(this);
            this.bg.removedEnemy.add(this);
		}
       //����һ�����
		private  int  imageType=0;
		public void run() {
			while(true){
				try {
					//�ж��Ƿ�������������
					 boolean  canleft=true;//������
					 boolean  canright=true;//������
					 //�õ����е��ϰ���
					for(int i=0;i< this.bg.getAllObstruction().size();i++){
						Obstruction  obs =this.bg.getAllObstruction().get(i);
						// java.lang.NullPointerException
						//����������������ͨ�ĵ��˵�ʱ��ͻ���ֿ�ָ��
						//���Ƕ�ִ�м�����ż��û�л��ߴ�ӡ���Ҳû��
						//����������
						if(x+60 == obs.getX() && y>obs.getY()-60 && y<obs.getY()+60){
							canright=false;
						}
						//����������
						if(x-60 == obs.getX() && y>obs.getY()-60 && y<obs.getY()+60){
							canleft=false;
						}
					}
					
					//�жϵ��˵�����
					if(type ==1){
						//���������ߵ��ǲ���������  �͸ı䷽��
						if(isleftOrup&&!canleft|| x==0){
							 isleftOrup=false;
						}
						if(!isleftOrup&&!canright|| x==840){
							 isleftOrup=true;
						}
						//�жϷ���
						if(isleftOrup){
							//����
							x-=2;
						}else{
							x+=2;
						}
						 //˵������ͨ�ĵ���
						//�������ĸо�
						if(imageType ==0){
							imageType=1;
						}else{
						    imageType=0;
						}
						showImage=StaticValue.allTriangleImage.get(imageType);
					}
					if(type == 2){
						//˵����ʳ�˻�
						//�ж����ߺ�����
						if(isleftOrup&&this.y==this.upmax){
							isleftOrup=false;
						}
						if(!isleftOrup && this.y == this.downmax){
							isleftOrup=true;
						}
						//�жϷ���
						if(isleftOrup){
							//����
							y-=2;
						}else{
							y+=2;
						}
						//�������ĸо�
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
