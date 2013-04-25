/*
 * Name:������
 * File:ControlPanel.java
 * Function: �ⲿ���ư�ť��panel
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ControlPanel extends JPanel
{
	/**
	 *
	 */
	private static final long serialVersionUID = -4400080910959164826L;//serialVersionUID �ֶ�
	// ControlPanel��Ĺ��죬�������ʾ���Ĳ���
	JComboBox currentFloorCombo; //ѡ��¥��������б��
	MyMainPanel mainPanel; //��ʾ�岿���ݵ������panel�����ã�������Ϣ����
	//���캯��
	public ControlPanel(MyMainPanel mainPanel) 
	{
		this.mainPanel = mainPanel;
		Border b = BorderFactory.createEtchedBorder(); //�߿�
		setBorder(b); //Ϊ�����ʾ�����ϱ߿�
		addComboBox(); //�˷������ڼ��������б��
		addButton();//�˷������ڼ��������ť�����ϡ����°�ť
	}
	//�˷������ڼ��������ť�����ϡ����°�ť
	private void addButton() 
	{
		JButton upButton = new JButton("UP");
		JButton downButton = new JButton("DOWN");
		// ΪupButton��ť�ṩ��������
		ActionListener UpListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//�������б���У������ڵ����⣬��һ���а�ť����
				String InputStr = (String) currentFloorCombo.getSelectedItem();

				int destFloor = checkInput(InputStr);
				if (destFloor != -1) 
				{
					mainPanel.moveTo(destFloor,1);
				}
			}
		};
		upButton.addActionListener(UpListener);
		// ΪDownButton��ť�ṩ��������
		ActionListener DownListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//�������б���У������ڵ����⣬��һ���а�ť����
				String InputStr = (String) currentFloorCombo.getSelectedItem();

				int destFloor = checkInput(InputStr);
				if (destFloor != -1) 
				{
					mainPanel.moveTo(destFloor,-1);
				}
			}
		};
		downButton.addActionListener(DownListener);
		//	��panel�м��밴ť
		add(upButton);
		add(downButton);
	}
	//�˷������ڼ��������б��
	private void addComboBox() 
	{
		currentFloorCombo = new JComboBox();
		currentFloorCombo.setEditable(true);
		for (int i = 1; i <= 20; ++i) 
		{
			currentFloorCombo.addItem("" + i);
			//�������б�����1 �� 20 ģ���ڲ�ͬ��¥�㰴�¡����ϡ��������¡���ť
		}
		add(currentFloorCombo); //	��panel�м��������б��
	}
	// �˺����Ƕ�����ֵ���м���, ������Ҫ�������ֵ����������
	private int checkInput(String InputStr)
	{
		String str = InputStr.trim();
		if(str!=null)
		{
			int inputNumber = Integer.parseInt(str);//ת��������
			if(inputNumber<1||inputNumber>20)
			{
				System.out.println("InputError!");
				currentFloorCombo.setSelectedItem(new String(""));
				return -1;
			}
			else
			{
				return inputNumber;
			}
		}
		else
		{
			System.out.println("NoInputError!");
			currentFloorCombo.setSelectedItem(new String(""));
			return -1;
		}
	}
}
