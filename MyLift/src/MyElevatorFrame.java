/*
 * Name:������
 * File:MyElevatorFrame.java
 * Function: ���panel
 */
import java.awt.Container;

import javax.swing.JFrame;

public class MyElevatorFrame extends JFrame//������
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -353028641983020353L;//serialVersionUID �ֶ�
	public static final int DEF_WIDTH = 800;//Ĭ�ϴ��ڳ�
	public static final int DEF_HEIGHT= 600;//Ĭ�ϴ��ڿ�

	//���캯��
	public MyElevatorFrame(String frameName)
	{
		super(frameName);
		setSize(DEF_WIDTH, DEF_HEIGHT); //���ô��ڴ�С
		Container contentPane = getContentPane();
		MyPanel panel = new MyPanel(); //���������³�ʼ��һ�������
		contentPane.add(panel); //��panel��ӵ�������
	}
}