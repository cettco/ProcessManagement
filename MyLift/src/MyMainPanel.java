/*
 * Name:������
 * File:MyMainPanel.java
 * Function: ��������ĵ���panel
 */
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MyMainPanel extends JPanel// ���ڿ��ƹ����岿����
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8062336976624968892L;//serialVersionUID �ֶ�
	
	MyElevatorPanel[] panel; //��ʾ�岿���ݵ�����
	public MyMainPanel() 
	{
		setLayout(new GridLayout(1, 5)); //���ò��ֹ����������� GridLayout�Ĳ��ֹ���1��5�У�

		panel = new MyElevatorPanel[5]; //��ʼ���岿����
		panel[0] = new MyElevatorPanel("1"); //ÿ�����ݼ�����ʾ��ʶ
		panel[1] = new MyElevatorPanel("2");
		panel[2] = new MyElevatorPanel("3");
		panel[3] = new MyElevatorPanel("4");
		panel[4] = new MyElevatorPanel("5");

		add(panel[0]); //���岿���ݵ���ʾ����MainPanel
		add(panel[1]);
		add(panel[2]);
		add(panel[3]);
		add(panel[4]);
	}
	//��ȡ����ʵĵ��ݽ����������󣬴˺������ص�������
	private int getSuitableElevator(int destFloor,int direction)
	{
		int []distance = new int[]{21,21,21,21,21};//�������ʼΪ21���Է�����
		int minDistance = 21;
		int suitableElevator = -1;
		if(direction==1)//����������
		{
			for(int i=0;i<panel.length;i++)
			{
				if(panel[i].runningStatus==-1)//�����������������У���������
					distance[i] = 21;
				else if(panel[i].runningStatus == 1)
				{
					if(panel[i].currentFloor>=destFloor)//�������������У������ڲ������������������������
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
		else if (direction==-1)//����������
		{
			for(int i=0;i<panel.length;i++)
			{
				if(panel[i].runningStatus==1) //�����������������У���������
					distance[i] = 21;
				else if(panel[i].runningStatus == -1)
				{
					if(panel[i].currentFloor<=destFloor)//�������������У������ڲ������������������������
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
		//�����е��ݶ�����ͬһ����������
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
		//������С����
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
	//�˷����������ƶ���destFloor��Ӧ������directionΪ1��ʾ���ϣ�Ϊ-1��ʾ����
	public void moveTo(int destFloor,int direction)
	{
		if(getSuitableElevator(destFloor,direction)!=-1)
		{
			//�õ���������ĵ��ݵ�����
			int suitableElevator = getSuitableElevator(destFloor,direction);
			if(panel[suitableElevator].runningStatus == 0)//���ݾ�ֹ
			{
				panel[suitableElevator].stopList[destFloor-1]=true;
				int distance = destFloor - panel[suitableElevator].currentFloor;
				panel[suitableElevator].distance = distance;
				synchronized (panel[suitableElevator].thread) 
				{
					panel[suitableElevator].thread.notify();//���ѽ���
				}
			}
			else//����״̬Ϊ�˶���ֱ���޸���ͣ�б���
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
