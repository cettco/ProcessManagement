/*
 * Name:张仕奇
 * File:MyElevatorFrame.java
 * Function: 添加panel
 */
import java.awt.Container;

import javax.swing.JFrame;

public class MyElevatorFrame extends JFrame//主窗口
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -353028641983020353L;//serialVersionUID 字段
	public static final int DEF_WIDTH = 800;//默认窗口长
	public static final int DEF_HEIGHT= 600;//默认窗口宽

	//构造函数
	public MyElevatorFrame(String frameName)
	{
		super(frameName);
		setSize(DEF_WIDTH, DEF_HEIGHT); //设置窗口大小
		Container contentPane = getContentPane();
		MyPanel panel = new MyPanel(); //在主窗口下初始化一个主面板
		contentPane.add(panel); //将panel添加到容器中
	}
}