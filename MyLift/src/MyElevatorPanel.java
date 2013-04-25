/*
 * Name:������
 * File:MyElevatorPanel.java
 * Function: ÿ�����ݵ�panel
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

public class MyElevatorPanel extends JPanel implements Runnable//ÿ�����ݵĽ�����ʾ
{

	/**
	 *  
	 */
	private static final long serialVersionUID = 1895203294318950534L;//serialVersionUID �ֶ�
	String elevatorName;//��������
	int runningStatus = 0;//��������״̬��1Ϊ���ϣ�-1Ϊ���£�0Ϊ�ȴ�
	int lastRunnungStatus = 1;//֮ǰ������ĵ�������״̬
	Thread thread; //	�������е��߳�
	int currentFloor = 1;//��ǰ�������ڲ���
	int distance = 21;//�����ƶ�����
	public boolean []stopList = new boolean[20];//��ͣ�б�
	JPanel panelElevator = new JPanel();//���ݲ���
	JPanel panelInElevator = new JPanel();//�����ڲ�����
	JPanel panelButtonsInElevator = new JPanel();//�����ڲ���ť����
	private JButton[] dispButton;//	��20����ɫ��ťģ���¥�ĸ���¥��
	private JButton[] operatorButtons;//ģ���ڵ����ڲ��Ĳ�����ť ��1 �� 20¥�� 
	
	public MyElevatorPanel(String elevatorName)
	{
		//��������
		thread = new Thread(this);
		thread.setDaemon(true);//�����̱߳��Ϊ�ػ��̻߳��û��߳�
		
		//��ʼ��������ͣ�б�
		for(int i=0;i<stopList.length;i++)
		{
			stopList[i]=false;
		}
		
		//���ñ�ǩ
		
		//���õ��ݽ���
		panelElevator.setLayout(new GridLayout(20, 1));
		dispButton = new JButton[20];
		for (int i = 0; i < 20; ++i) {
			dispButton[i] = new JButton(""+(20-i));
			dispButton[i].setBackground(Color.black); //	��ʼΪ��ɫ
			dispButton[i].setEnabled(false);//���ð�ť

			panelElevator.add(dispButton[i]);
		}
		dispButton[19].setBackground(Color.red); // һ¥�ڳ�ʼ��ʱ ��ɫ�Ǻ�ɫ����ʾ������һ¥


		//���õ����ڰ�ť
		panelButtonsInElevator.setLayout(new GridLayout(20, 1));
		operatorButtons = new JButton[22];//��ʼ����ť
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

		//������Χ����
		this.elevatorName = elevatorName;
		Border boder = BorderFactory.createEtchedBorder(); //�ӱ߿�
		Border title = BorderFactory.createTitledBorder(boder,elevatorName);
		setBorder(title); //�ӱ߿����
		setLayout(new BorderLayout()); //���ò��ֹ����������� BorderLayout�Ĳ��ֹ���
		
		add(panelElevator, BorderLayout.WEST);
		add(panelInElevator, BorderLayout.CENTER);
		
		thread.start(); //�����߳�
	}
	
	//�Զ�������ƶ�������moveΪtrue������һ�㣬false������һ��
	public void moveElevatorFloor(boolean move)
	{
		try
		{
			if(move)
			{
				if(currentFloor>=20)//��������20�㣬��������
				{
					System.out.println("LastFloorUpError!");
					return;
				}
				dispButton[20-currentFloor].setBackground(Color.black);
				dispButton[20-currentFloor-1].setBackground(Color.RED);
				currentFloor++;
				Thread.sleep(500);
				if(goingToStop())//������ͣ�б�����Ҫ�ڴβ�ͣ����������stop��openDoor����
				{
					openDoor();
					stopList[currentFloor-1]=false;
				}
			}
			else
			{
				if(currentFloor<=1)//��������1�㣬��������
				{
					System.out.println("FirstFloorDownError!");
					return;
				}
				dispButton[20-currentFloor].setBackground(Color.LIGHT_GRAY);
				dispButton[20-currentFloor+1].setBackground(Color.RED);
				currentFloor--;
				Thread.sleep(500);
				if(goingToStop())//������ͣ�б�����Ҫ�ڴβ�ͣ����������stop��openDoor����
				{
					openDoor();
					stopList[currentFloor-1]=false;
				}
			}
			lastRunnungStatus = runningStatus;//�����ϴε�������״̬�Ա㴦������ʱ���ܵ�����
		}
		catch(InterruptedException e)
		{
			System.out.println("SleepError!");
		}
	}
	//���ų���
	void openDoor() 
	{
		dispButton[20-currentFloor].setBackground(Color.green);
		stopList[this.currentFloor-1]=false;
		try 
		{
			Thread.sleep(1000);//����1��
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		dispButton[20-currentFloor].setBackground(Color.RED);
		operatorButtons[currentFloor+1].setBackground(Color.WHITE);
	}
	//�˷������ڼ������Ƿ�Ҫ�ڵ�ǰ��ͣ��
	private boolean goingToStop()
	{
		if(stopList[currentFloor-1])
			return true;
		else
			return false;
	}
	//���ڽ��յ����ڲ����ְ�ť����
	private void addAction(final JButton button) 
	{
		ActionListener aL = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int num = Integer.parseInt(button.getText().trim());//��ȡ��Ӧ����
				stopList[num-1]=true;
				button.setBackground(Color.orange);//�޸�Ϊ�ٻ�ɫ��ʾ����
				synchronized (thread) 
				{
					thread.notify();//���ѽ���
				}
			}
		};
		button.addActionListener(aL);//�����������ӵ���Ӧ��ť��
	}
	//���ڽ��յ����ڲ������Ű�ť����
	private void addCtrlAction(final JButton button)
	{
		ActionListener aL = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String str = button.getText().trim();
				if(str == "open")
				{
					if(runningStatus == 0)//���п��������ҵ���δ���У�����
						distance = 0;//�̷߳���distanceΪ0�����openDoor����
				}
				else
				{
					if(runningStatus == 0 )
					{
						try 
						{
							Thread.sleep(400);//���ų����Ѱ����Զ����ŵ����ݣ��ʹ���ֻ���߳�����400����
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
					thread.notify();//�����߳�
				}
			}
		};
		button.addActionListener(aL);//�����������뵽��ť��
	}
	//�����������жϵ����Ƿ�����Ҫ���е�������Ӧ����true��false
	private boolean goOnRunning()
	{
		int stopCount = 0;
		int max = 0,min = 21;

		if(distance<=-20||distance>=20)
		{
			for(int i=0;i<this.stopList.length;i++)//������ͣ�б��е���ͣ��
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
			if(lastRunnungStatus == 1)//�������о��룬����Ϊ��������Ϊ��
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
	public void run() //�߳�����ʱ���õ�run����
	{
		while (true)
		{
			try 
			{
				//�����д��������������ͬʱ����һ���̶߳������з���
				synchronized(thread)
				{
					//���µ�ǰ���̵߳ȴ���ֱ�������̵߳��ô˶���� notify() ������ notifyAll() ����
					thread.wait();
				}
				//if(distance != 21)
				while(goOnRunning())//������������ʱ��������
				{
					if(distance == 0)//�ڱ�����ֱ�ӿ���
						openDoor();
					if(distance > 0)//����
					{
						runningStatus = 1;
						for(int i=0;i<distance;i++)
						{
							moveElevatorFloor(true);
						}
					}
					else//����
					{
						runningStatus = -1;
						for(int i=0;i<-distance;i++)
						{
							moveElevatorFloor(false);
						}
					}
					runningStatus = 0;//����״̬
					distance = 21;
				}
				Thread.sleep(100);//���߳���Ϣ�Խ�ʡCPU
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
