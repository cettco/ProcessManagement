/*
 * Name:������
 * File:MyElevator.java
 * Function: ������
 */
import javax.swing.JFrame;

public class MyElevator 
{
	public static void main(String[] args) //�����������ڳ�ʼ������
	{
		MyElevatorFrame myElevatorFrame = new MyElevatorFrame("Elevator");
		myElevatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myElevatorFrame.setVisible(true);
	}
}