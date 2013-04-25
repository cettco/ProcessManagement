/*
 * Name:张仕奇
 * File:MyMainPanel.java
 * Function: 创建整体的电梯panel
 */
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MyMainPanel extends JPanel// 用于控制管理五部电梯
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8062336976624968892L;//serialVersionUID 字段
	
	MyElevatorPanel[] panel; //表示五部电梯的数组
	public MyMainPanel() 
	{
		setLayout(new GridLayout(1, 5)); //设置布局管理器，采用 GridLayout的布局管理（1行5列）

		panel = new MyElevatorPanel[5]; //初始化五部电梯
		panel[0] = new MyElevatorPanel("1"); //每部电梯加上显示标识
		panel[1] = new MyElevatorPanel("2");
		panel[2] = new MyElevatorPanel("3");
		panel[3] = new MyElevatorPanel("4");
		panel[4] = new MyElevatorPanel("5");

		add(panel[0]); //把五部电梯的显示加入MainPanel
		add(panel[1]);
		add(panel[2]);
		add(panel[3]);
		add(panel[4]);
	}
	//获取最合适的电梯接收梯外请求，此函数返回电梯索引
	private int getSuitableElevator(int destFloor,int direction)
	{
		int []distance = new int[]{21,21,21,21,21};//将距离初始为21，以防出错
		int minDistance = 21;
		int suitableElevator = -1;
		if(direction==1)//请求方向向上
		{
			for(int i=0;i<panel.length;i++)
			{
				if(panel[i].runningStatus==-1)//若电梯正在向下运行，忽略请求
					distance[i] = 21;
				else if(panel[i].runningStatus == 1)
				{
					if(panel[i].currentFloor>=destFloor)//若电梯向上运行，但所在层数高于请求层数，忽略请求
					{
						distance[i] = 21;
					}
					else
						distance[i] = Math.abs(panel[i].currentFloor-destFloor);
				}
				else
					distance[i] = Math.abs(panel[i].currentFloor-destFloor);
			}
		}
		else if (direction==-1)//请求方向向下
		{
			for(int i=0;i<panel.length;i++)
			{
				if(panel[i].runningStatus==1) //若电梯正在向上运行，忽略请求
					distance[i] = 21;
				else if(panel[i].runningStatus == -1)
				{
					if(panel[i].currentFloor<=destFloor)//若电梯向下运行，但所在层数低于请求层数，忽略请求
					{
						distance[i] =panel[i].currentFloor - destFloor;
					}
					else
						distance[i] = Math.abs(panel[i].currentFloor-destFloor);
				}
				else
					distance[i] = Math.abs(panel[i].currentFloor-destFloor);
			}
		}
		//若所有电梯都在向同一个方向运行
		if((panel[0].runningStatus==1&&panel[1].runningStatus==1
				&&panel[2].runningStatus==1&&panel[3].runningStatus==1&&panel[4].runningStatus==1)||
			(panel[0].runningStatus==-1&&panel[1].runningStatus==-1
				&&panel[2].runningStatus==-1&&panel[3].runningStatus==-1&&panel[4].runningStatus==-1))
		{
			for(int i=0;i<panel.length;i++)
			{
				distance[i] = Math.abs(panel[i].currentFloor-destFloor);
			}
		}
		//计算最小距离
		for(int i=0;i<5;i++)
		{
			if(minDistance>distance[i])
			{
				minDistance = distance[i];
				suitableElevator = i;
			}
		}
		if(minDistance == 21)
			return -1;
		else
			return suitableElevator;
	}
	//此方法将电梯移动到destFloor相应层数，direction为1表示向上，为-1表示向下
	public void moveTo(int destFloor,int direction)
	{
		if(getSuitableElevator(destFloor,direction)!=-1)
		{
			//得到接受请求的电梯的索引
			int suitableElevator = getSuitableElevator(destFloor,direction);
			if(panel[suitableElevator].runningStatus == 0)//电梯静止
			{
				panel[suitableElevator].stopList[destFloor-1]=true;
				int distance = destFloor - panel[suitableElevator].currentFloor;
				panel[suitableElevator].distance = distance;
				synchronized (panel[suitableElevator].thread) 
				{
					panel[suitableElevator].thread.notify();//唤醒进程
				}
			}
			else//电梯状态为运动，直接修改暂停列表即可
			{
				panel[suitableElevator].stopList[destFloor-1]=true;
			}
		}
		else
		{
			System.out.println("NoMoveError!");
		}
	}
}
