/*
 * Name:张仕奇
 * File:MyPanel.java
 * Function: 用于添加电梯外部按键，和电梯panel
 */
import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MyPanel extends JPanel//程序的主界面，用于界面布局
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4865040593219679776L;//serialVersionUID 字段
	public MyPanel() 
	{
		setLayout(new BorderLayout()); //设置布局管理器，采用BorderLayout的布局管理

		MyMainPanel myMainPanel = new MyMainPanel();//初始化电梯窗口
		add(myMainPanel); //将电梯窗口加到主界面上
		validate();//将更新后的组件显示出来
		ControlPanel controlPanel = new ControlPanel(myMainPanel);//电梯外发出向上向下请求的控制区
		add(controlPanel, BorderLayout.NORTH); 
	}
}
