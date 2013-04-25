/*
 * Name:张仕奇
 * File:MyElevatorPanel.java
 * Function: 每部电梯的panel
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MyElevatorPanel extends JPanel implements Runnable//每部电梯的界面显示
{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1895203294318950534L;//serialVersionUID 字段
	String elevatorName;//电梯名称
	int runningStatus = 0;//电梯运行状态，1为向上，-1为向下，0为等待
	int lastRunnungStatus = 1;//之前的最近的电梯运行状态
	Thread thread; //	电梯运行的线程
	int currentFloor = 1;//当前电梯所在层数
	int distance = 21;//电梯移动距离
	public boolean []stopList = new boolean[20];//暂停列表
	JPanel panelElevator = new JPanel();//电梯布局
	JPanel panelInElevator = new JPanel();//电梯内部布局
	JPanel panelButtonsInElevator = new JPanel();//电梯内部按钮布局
	private JButton[] dispButton;//	用20个灰色按钮模拟大楼的各个楼层
	private JButton[] operatorButtons;//模拟在电梯内部的操作按钮 （1 到 20楼） 
	
	public MyElevatorPanel(String elevatorName)
	{
		//创建进程
		thread = new Thread(this);
		thread.setDaemon(true);//将该线程标记为守护线程或用户线程
		
		//初始化电梯暂停列表
		for(int i=0;i<stopList.length;i++)
		{
			stopList[i]=false;
		}
		
		//设置标签
		
		//设置电梯界面
		panelElevator.setLayout(new GridLayout(20, 1));
		dispButton = new JButton[20];
		for (int i = 0; i < 20; ++i) {
			dispButton[i] = new JButton(""+(20-i));
			dispButton[i].setBackground(Color.black); //	初始为灰色
			dispButton[i].setEnabled(false);//禁用按钮

			panelElevator.add(dispButton[i]);
		}
		dispButton[19].setBackground(Color.red); // 一楼在初始化时 颜色是红色，表示电梯在一楼


		//设置电梯内按钮
		panelButtonsInElevator.setLayout(new GridLayout(20, 1));
		operatorButtons = new JButton[22];//初始化按钮
		operatorButtons[0] = new JButton("open");
		panelButtonsInElevator.add(operatorButtons[0]);
		operatorButtons[1] = new JButton("close");
		panelButtonsInElevator.add(operatorButtons[1]);
		for (int i = 21; i >=2; i--) 
		{
			operatorButtons[i] = new JButton("" + (i - 1));
			panelButtonsInElevator.add(operatorButtons[i]);
		}
		addCtrlAction(operatorButtons[0]);
		operatorButtons[0].setBackground(Color.WHITE);
		addCtrlAction(operatorButtons[1]);
		operatorButtons[1].setBackground(Color.WHITE);
		for(int i=2;i<operatorButtons.length;i++)
		{
			operatorButtons[i].setBackground(Color.WHITE);
			addAction(operatorButtons[i]);
		}
		panelInElevator.add(panelButtonsInElevator,BorderLayout.CENTER);

		//设置外围布局
		this.elevatorName = elevatorName;
		Border boder = BorderFactory.createEtchedBorder(); //加边框
		Border title = BorderFactory.createTitledBorder(boder,elevatorName);
		setBorder(title); //加边框标题
		setLayout(new BorderLayout()); //设置布局管理器，采用 BorderLayout的布局管理
		
		add(panelElevator, BorderLayout.WEST);
		add(panelInElevator, BorderLayout.CENTER);
		
		thread.start(); //启动线程
	}
	
	//自定义电梯移动函数，move为true则向上一层，false则向下一层
	public void moveElevatorFloor(boolean move)
	{
		try
		{
			if(move)
			{
				if(currentFloor>=20)//若电梯在20层，则不能向上
				{
					System.out.println("LastFloorUpError!");
					return;
				}
				dispButton[20-currentFloor].setBackground(Color.black);
				dispButton[20-currentFloor-1].setBackground(Color.RED);
				currentFloor++;
				Thread.sleep(500);
				if(goingToStop())//访问暂停列表，若需要在次层停靠，则运行stop和openDoor函数
				{
					openDoor();
					stopList[currentFloor-1]=false;
				}
			}
			else
			{
				if(currentFloor<=1)//若电梯在1层，则不能向下
				{
					System.out.println("FirstFloorDownError!");
					return;
				}
				dispButton[20-currentFloor].setBackground(Color.LIGHT_GRAY);
				dispButton[20-currentFloor+1].setBackground(Color.RED);
				currentFloor--;
				Thread.sleep(500);
				if(goingToStop())//访问暂停列表，若需要在次层停靠，则运行stop和openDoor函数
				{
					openDoor();
					stopList[currentFloor-1]=false;
				}
			}
			lastRunnungStatus = runningStatus;//保留上次电梯运行状态以便处理运行时接受的请求
		}
		catch(InterruptedException e)
		{
			System.out.println("SleepError!");
		}
	}
	//开门程序
	void openDoor() 
	{
		dispButton[20-currentFloor].setBackground(Color.green);
		stopList[this.currentFloor-1]=false;
		try 
		{
			Thread.sleep(1000);//持续1秒
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		dispButton[20-currentFloor].setBackground(Color.RED);
		operatorButtons[currentFloor+1].setBackground(Color.WHITE);
	}
	//此方法用于检查电梯是否要在当前层停靠
	private boolean goingToStop()
	{
		if(stopList[currentFloor-1])
			return true;
		else
			return false;
	}
	//用于接收电梯内部数字按钮输入
	private void addAction(final JButton button) 
	{
		ActionListener aL = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int num = Integer.parseInt(button.getText().trim());//获取相应数字
				stopList[num-1]=true;
				button.setBackground(Color.orange);//修改为橘黄色表示按下
				synchronized (thread) 
				{
					thread.notify();//唤醒进程
				}
			}
		};
		button.addActionListener(aL);//将监听器增加到相应按钮中
	}
	//用于接收电梯内部开关门按钮输入
	private void addCtrlAction(final JButton button)
	{
		ActionListener aL = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String str = button.getText().trim();
				if(str == "open")
				{
					if(runningStatus == 0)//若有开门请求且电梯未运行，则开门
						distance = 0;//线程发现distance为0会调用openDoor函数
				}
				else
				{
					if(runningStatus == 0 )
					{
						try 
						{
							Thread.sleep(400);//开门程序已包含自动关门的内容，故关门只让线程休眠400毫秒
						} 
						catch (InterruptedException e1) 
						{
							e1.printStackTrace();
						}
					}
				}
				button.setBackground(Color.orange);
				try 
				{
					Thread.sleep(200);
				} 
				catch (InterruptedException e1) 
				{
					e1.printStackTrace();
				}
				button.setBackground(Color.WHITE);
				synchronized (thread) 
				{
					thread.notify();//唤醒线程
				}
			}
		};
		button.addActionListener(aL);//将监听器加入到按钮中
	}
	//本方法用于判断电梯是否有需要运行的请求，相应返回true和false
	private boolean goOnRunning()
	{
		int stopCount = 0;
		int max = 0,min = 21;

		if(distance<=-20||distance>=20)
		{
			for(int i=0;i<this.stopList.length;i++)//计算暂停列表中的暂停数
			{
				if(stopList[i]==true)
				{
					stopCount++;
					max = max>i+1 ? max:i+1;
					min = min<i+1 ? min:i+1;
				}
			}
		}
		if(stopCount>0)
		{
			if(lastRunnungStatus == 1)//计算运行距离，向上为正，向下为负
				distance = max - currentFloor;
			else
				distance = min - currentFloor;
		}
		if(distance == 21)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}
	public void run() //线程运行时调用的run方法
	{
		while (true)
		{
			try 
			{
				//给块中代码加锁，不允许同时超过一个线程对它进行访问
				synchronized(thread)
				{
					//导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法
					thread.wait();
				}
				//if(distance != 21)
				while(goOnRunning())//当有运行请求时持续运行
				{
					if(distance == 0)//在本层则直接开门
						openDoor();
					if(distance > 0)//向上
					{
						runningStatus = 1;
						for(int i=0;i<distance;i++)
						{
							moveElevatorFloor(true);
						}
					}
					else//向下
					{
						runningStatus = -1;
						for(int i=0;i<-distance;i++)
						{
							moveElevatorFloor(false);
						}
					}
					runningStatus = 0;//重置状态
					distance = 21;
				}
				Thread.sleep(100);//让线程休息以节省CPU
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
