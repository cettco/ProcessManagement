/*
 * Name:张仕奇
 * File:ControlPanel.java
 * Function: 外部控制按钮的panel
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
	private static final long serialVersionUID = -4400080910959164826L;//serialVersionUID 字段
	// ControlPanel类的构造，对这个显示区的布局
	JComboBox currentFloorCombo; //选择楼层的下拉列表框
	MyMainPanel mainPanel; //显示五部电梯的区域的panel的引用，方便消息传递
	//构造函数
	public ControlPanel(MyMainPanel mainPanel) 
	{
		this.mainPanel = mainPanel;
		Border b = BorderFactory.createEtchedBorder(); //边框
		setBorder(b); //为这个显示区加上边框
		addComboBox(); //此方法用于加入下拉列表框
		addButton();//此方法用于加入操作按钮：向上、向下按钮
	}
	//此方法用于加入操作按钮：向上、向下按钮
	private void addButton() 
	{
		JButton upButton = new JButton("UP");
		JButton downButton = new JButton("DOWN");
		// 为upButton按钮提供监听器类
		ActionListener UpListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//从下拉列表框中，读出在电梯外，哪一层有按钮命令
				String InputStr = (String) currentFloorCombo.getSelectedItem();

				int destFloor = checkInput(InputStr);
				if (destFloor != -1) 
				{
					mainPanel.moveTo(destFloor,1);
				}
			}
		};
		upButton.addActionListener(UpListener);
		// 为DownButton按钮提供监听器类
		ActionListener DownListener = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//从下拉列表框中，读出在电梯外，哪一层有按钮命令
				String InputStr = (String) currentFloorCombo.getSelectedItem();

				int destFloor = checkInput(InputStr);
				if (destFloor != -1) 
				{
					mainPanel.moveTo(destFloor,-1);
				}
			}
		};
		downButton.addActionListener(DownListener);
		//	往panel中加入按钮
		add(upButton);
		add(downButton);
	}
	//此方法用于加入下拉列表框
	private void addComboBox() 
	{
		currentFloorCombo = new JComboBox();
		currentFloorCombo.setEditable(true);
		for (int i = 1; i <= 20; ++i) 
		{
			currentFloorCombo.addItem("" + i);
			//往下拉列表框加入1 到 20 模拟在不同的楼层按下“向上”、”向下“按钮
		}
		add(currentFloorCombo); //	往panel中加入下拉列表框
	}
	// 此函数是对输入值进行检验, 不满足要求的输入值将不被接受
	private int checkInput(String InputStr)
	{
		String str = InputStr.trim();
		if(str!=null)
		{
			int inputNumber = Integer.parseInt(str);//转换成数字
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
