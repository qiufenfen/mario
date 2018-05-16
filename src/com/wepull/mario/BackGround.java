package com.wepull.mario;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
         private  int  sort;
         private  boolean flag;
         private  BufferedImage  showImage;
         
         private  boolean isOver=false;//游戏结束的标志
         private  boolean isDown=false;//旗帜下降完毕
         List<Obstruction>  allObstruction=new ArrayList<Obstruction>();
         List<Obstruction>  removedObstruction=new ArrayList<Obstruction>();
         List<Enemy>        allEnemy=new ArrayList<Enemy>();
         List<Enemy>        removedEnemy=new ArrayList<Enemy>();
         public  BackGround(int sort,boolean flag){
        	 this.sort=sort;
        	 this.flag=flag;
             if(flag){
            	 showImage=StaticValue.endImage;
             }else{
            	 showImage=StaticValue.bgImage;
              }
             //处理第一个场景
            if(sort == 1){
            	for(int i=0;i<15;i++){
            		allObstruction.add(new Obstruction(60*i,540,9,this));
            	}
            	    allObstruction.add(new Obstruction(120,360,4,this));
            	    allObstruction.add(new Obstruction(300,360,0,this));
            	    allObstruction.add(new Obstruction(360,360,4,this));
            	    allObstruction.add(new Obstruction(420,360,0,this));
            	    allObstruction.add(new Obstruction(480,360,4,this));
            	    allObstruction.add(new Obstruction(540,360,0,this));
            	    allObstruction.add(new Obstruction(420,180,4,this));
            	    //绘制花盆
            	    allObstruction.add(new Obstruction(660,480,8,this));
            	    allObstruction.add(new Obstruction(720,480,7,this));
            	    allObstruction.add(new Obstruction(660,540,6,this));
            	    allObstruction.add(new Obstruction(720,540,5,this));
            	    //绘制普通的敌人
            	    allEnemy.add(new Enemy(600,480,1,true,this));
            	    //绘制食人花
            	    allEnemy.add(new Enemy(690,540,2,true,420,540,this));
            } 
            //处理第二个场景
            if(sort ==2){
            	//绘制地面
    			for(int i=0;i<15;i++){
    				//在10处加入一个陷阱
    				if(i!=10){
    					this.allObstruction.add(new Obstruction(i*60,540,9,this));
    				}
    			}
    			this.allObstruction.add(new Obstruction(60,540,6,this));
    			this.allObstruction.add(new Obstruction(120,540,5,this));
    			this.allObstruction.add(new Obstruction(60,480,6,this));
    			this.allObstruction.add(new Obstruction(120,480,5,this));
    			this.allObstruction.add(new Obstruction(60,420,8,this));
    			this.allObstruction.add(new Obstruction(120,420,7,this));
    			

    			this.allObstruction.add(new Obstruction(240,540,6,this));
    			this.allObstruction.add(new Obstruction(300,540,5,this));
    			this.allObstruction.add(new Obstruction(240,480,6,this));
    			this.allObstruction.add(new Obstruction(300,480,5,this));
    			this.allObstruction.add(new Obstruction(240,420,6,this));			
    			this.allObstruction.add(new Obstruction(300,420,5,this));
    			this.allObstruction.add(new Obstruction(240,360,8,this));
    			this.allObstruction.add(new Obstruction(300,360,7,this));	
            }
            //处理第三个场景
            if(sort ==3){
            	//绘制地面
    			for(int i=0;i<15;i++){
    					this.allObstruction.add(new Obstruction(i*60,540,9,this));
    				}
    			//绘制旗帜和砖块
    			this.allObstruction.add(new Obstruction(550,180,11,this));
    			this.allObstruction.add(new Obstruction(520,480,2,this));
            }
         }
        //绘制
        public  void  reset(){
        	this.allObstruction.addAll(removedObstruction);
        	this.allEnemy.addAll(removedEnemy);
        }
        //使得敌人动起来
        public  void enemyMove(){
        	for(int i=0;i<allEnemy.size();i++){
        		Enemy  e=allEnemy.get(i);
        		e.move();
        	}
        }
		public BufferedImage getShowImage() {
			return showImage;
		}

		public List<Obstruction> getAllObstruction() {
			return allObstruction;
		}

		public List<Enemy> getAllEnemy() {
			return allEnemy;
		}

		public int getSort() {
			return sort;
		}
		public boolean isFlag() {
			return flag;
		}
		public boolean isOver() {
			return isOver;
		}
		public void setOver(boolean isOver) {
			this.isOver = isOver;
		}
		public boolean isDown() {
			return isDown;
		}
		public void setDown(boolean isDown) {
			this.isDown = isDown;
		}
         
}
