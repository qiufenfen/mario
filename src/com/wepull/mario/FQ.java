package com.wepull.mario;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FQ {
	
	
	//程序的入口，必须要主方法程序才能执行
	public static void main(String[] args) throws Exception {
		
		//步骤1：准备快递
		//快递中的内容
		String info="1:100:用户名:主机名:32:下午准备干吗？";
		int weight=info.getBytes().length;
		InetAddress address=InetAddress.getByName("127.0.0.1");
		int tel=2425;
		DatagramPacket 快递=new DatagramPacket(info.getBytes(),weight,address,tel);
		
		//步骤2：准备快递员
		DatagramSocket 快递员=new DatagramSocket();
		
		//步骤3：快递员将包裹发送出去
		快递员.send(快递);
		
	}

}
