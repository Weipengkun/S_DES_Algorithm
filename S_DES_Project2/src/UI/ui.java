package UI;

import javax.swing.*;
import java.awt.*;

public class ui extends JFrame{
	JFrame f;
	JPanel jpA,jpB;
	JLabel jlabA1,jlabA2,jlabA3,jlabB1,jlabB2,jlabB3;

	JTabbedPane jtbp;
	JTextField keyA,keyB,plaintext,ciphertext,plaintextASCII,ciphertextASCII;
	JTextArea outputA,outputB;
	JButton butA1,butA2,butB1,butB2;
	MyCommandListener listener,listenerB,clearlistener;
	
	public ui()
	{
		f=new JFrame("S-DES");
		jpA=new JPanel();//面板1(加密面板)
		jpB=new JPanel();//面板2(解密面板)
		
		jlabA1=new JLabel("请输入10位二进制密钥，如：0000000000");
		jlabA1.setFont(new Font("黑体",  1,  15));
		jlabB1=new JLabel("请输入10位二进制密钥，如：0000000000");
		jlabB1.setFont(new Font("黑体",  1,  15));
		
		keyA = new JTextField(10);
		keyB = new JTextField(10);
		
		jlabA2=new JLabel("请输入8位二进制明文，如：11111111");
		jlabA2.setFont(new Font("黑体",  1,  15));
		jlabB2=new JLabel("请输入8位二进制密文，如：11111111");
		jlabB2.setFont(new Font("黑体",  1,  15));
		
		plaintext = new JTextField(16);
		ciphertext = new JTextField(16);
		
		jlabA3=new JLabel("请输入ASII编码字符串明文，如：abc");
		jlabA3.setFont(new Font("黑体",  1,  15));
		jlabB3=new JLabel("请输入ASII编码字符串密文，如：abc");
		jlabB3.setFont(new Font("黑体",  1,  15));
		
		plaintextASCII = new JTextField(16);
		ciphertextASCII = new JTextField(16);
		
		butA1=new JButton("确认生成密文");
		butA1 .setFont(new  java.awt.Font("微软雅黑",  1,  15));
		butA1.setBackground(new Color(160,160,160));
		butA2=new JButton("全部重置");
		butA2 .setFont(new  java.awt.Font("微软雅黑",  1,  15));
		butA2.setBackground(new Color(160,160,160));
		butB1=new JButton("确认生成明文");
		butB1 .setFont(new  java.awt.Font("微软雅黑",  1,  15));
		butB1.setBackground(new Color(160,160,160));
		butB2=new JButton("全部重置");
		butB2 .setFont(new  java.awt.Font("微软雅黑",  1,  15));
		butB2.setBackground(new Color(160,160,160));
		
		
		outputA=new JTextArea(10,37);
		outputB=new JTextArea(10,37);
		
		jtbp=new JTabbedPane();//采用默认的选项卡面板
	}
	
	public void displayWindow()
	{
//		GridLayout g = new GridLayout(3, 1);
//		jpA.setLayout(g);//设置为FlowLayout布局管理器 
//		jpB.setLayout(g);
		 
		//加密页
		//两按钮放入nBox
        Box hBoxA = Box.createHorizontalBox();
        hBoxA.add(butA1);
        hBoxA.add(Box.createHorizontalStrut(170));
        hBoxA.add(butA2);
        //vBox
        Box vBoxA = Box.createVerticalBox();
        vBoxA.add(Box.createVerticalStrut(10));
        vBoxA.add(jlabA1);
        vBoxA.add(keyA);
        vBoxA.add(Box.createVerticalStrut(15));
        vBoxA.add(jlabA2);
        vBoxA.add(plaintext);
        vBoxA.add(Box.createVerticalStrut(15));
        vBoxA.add(jlabA3);
        vBoxA.add(plaintextASCII);
        vBoxA.add(Box.createVerticalStrut(15));
//        vBoxA.add(hBoxA);
//        vBoxA.add(Box.createVerticalStrut(15));
//        vBoxA.add(outputA);
//        Box vBox = Box.createVerticalBox();
//        vBox.add(vBoxA);
//        vBox.add(hBoxA);
//        vBox.add(outputA);
//        jpA.add(vBox);
        jpA.add(vBoxA);
        jpA.add(hBoxA);
        jpA.add(outputA);
        
      //加密页
  		//两按钮放入hBox
          Box hBoxB = Box.createHorizontalBox();
          hBoxB.add(butB1);
          hBoxB.add(Box.createHorizontalStrut(170));
          hBoxB.add(butB2);
          //整体放入vBox
          Box vBoxB = Box.createVerticalBox();
          vBoxB.add(Box.createVerticalStrut(10));
          vBoxB.add(jlabB1);
          vBoxB.add(keyB);
          vBoxB.add(Box.createVerticalStrut(15));
          vBoxB.add(jlabB2);
          vBoxB.add(ciphertext);
          vBoxB.add(Box.createVerticalStrut(15));
          vBoxB.add(jlabB3);
          vBoxB.add(ciphertextASCII);
          vBoxB.add(Box.createVerticalStrut(15));
//          vBoxB.add(hBoxB);
//          vBoxB.add(Box.createVerticalStrut(15));
//          vBoxB.add(outputB);
        
		jpB.add(vBoxB);
		jpB.add(hBoxB);
		jpB.add(outputB);

		jtbp.addTab("加密", jpA);//添加选项卡进选项卡面板
		jtbp.addTab("解密", jpB);
		jtbp.setFont(new  java.awt.Font("黑体",  1,  12));
		
		
		f.setContentPane(jtbp);
		f.setSize(450, 500);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public void setMyCommandListener(MyCommandListener listener) {
		this.listener=listener;
		listener.setJTextField1(keyA);
		listener.setJTextField2(plaintext);
		listener.setJTextField3(plaintextASCII);
		listener.setJTextArea(outputA);
		butA1.addActionListener(listener);
		
	}
	public void setMyCommandListenerB(MyCommandListener listener) {
		listenerB=listener;
		listener.setJTextField1(keyB);
		listener.setJTextField2(ciphertext);
		listener.setJTextField3(ciphertextASCII);
		listener.setJTextArea(outputB);
		butB1.addActionListener(listener);
		
	}
	public void setMyCommandListenerClear(MyCommandListener listener) {
		clearlistener=listener;
		listener.setJTextField1(keyA);
		listener.setJTextField2(plaintext);
		listener.setJTextField3(plaintextASCII);
		listener.setJTextArea(outputA);
		keyA.addActionListener(listener);
		plaintext.addActionListener(listener);
		plaintextASCII.addActionListener(listener);
		butA2.addActionListener(listener);
		
	}
	public void setMyCommandListenerClearB(MyCommandListener listener) {
		clearlistener=listener;
		listener.setJTextField1(keyB);
		listener.setJTextField2(ciphertext);
		listener.setJTextField3(ciphertextASCII);
		listener.setJTextArea(outputB);
		keyB.addActionListener(listener);
		ciphertext.addActionListener(listener);
		ciphertextASCII.addActionListener(listener);
		butB2.addActionListener(listener);
		
	}
}

