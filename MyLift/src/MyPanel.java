/*
 * Name:������
 * File:MyPanel.java
 * Function: ������ӵ����ⲿ�������͵���panel
 */
import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MyPanel extends JPanel//����������棬���ڽ��沼��
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4865040593219679776L;//serialVersionUID �ֶ�
	public MyPanel() 
	{
		setLayout(new BorderLayout()); //���ò��ֹ�����������BorderLayout�Ĳ��ֹ���

		MyMainPanel myMainPanel = new MyMainPanel();//��ʼ�����ݴ���
		add(myMainPanel); //�����ݴ��ڼӵ���������
		validate();//�����º�������ʾ����
		ControlPanel controlPanel = new ControlPanel(myMainPanel);//�����ⷢ��������������Ŀ�����
		add(controlPanel, BorderLayout.NORTH); 
	}
}
