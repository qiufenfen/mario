package com.wepull.mario;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FQ {
	
	
	//�������ڣ�����Ҫ�������������ִ��
	public static void main(String[] args) throws Exception {
		
		//����1��׼�����
		//����е�����
		String info="1:100:�û���:������:32:����׼������";
		int weight=info.getBytes().length;
		InetAddress address=InetAddress.getByName("127.0.0.1");
		int tel=2425;
		DatagramPacket ���=new DatagramPacket(info.getBytes(),weight,address,tel);
		
		//����2��׼�����Ա
		DatagramSocket ���Ա=new DatagramSocket();
		
		//����3�����Ա���������ͳ�ȥ
		���Ա.send(���);
		
	}

}
