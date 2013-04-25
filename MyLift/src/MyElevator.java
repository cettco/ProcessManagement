/*
 * Name:张仕奇
 * File:MyElevator.java
 * Function: 主函数
 */
import javax.swing.JFrame;

public class MyElevator 
{
	public static void main(String[] args) //主函数，用于初始化窗口
	{
		MyElevatorFrame myElevatorFrame = new MyElevatorFrame("Elevator");
		myElevatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myElevatorFrame.setVisible(true);
	}
}