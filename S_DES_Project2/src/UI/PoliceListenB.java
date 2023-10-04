package UI;

import UI.MyCommandListener;
import functionalClass.Decrypt;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class PoliceListenB implements MyCommandListener {
	JTextField keyB,ciphertext,ciphertextASCII;
	JTextArea outputB;
	
	public void setJTextField1(JTextField text) {
		keyB=text;
	}
	public void setJTextField2(JTextField text) {
		ciphertext=text;
	}
	public void setJTextField3(JTextField text) {
		ciphertextASCII=text;
	}
	public void setJTextArea(JTextArea area) {
		outputB=area;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String str1=keyB.getText();
		String str2=ciphertext.getText();//将密文输入转为数组
		int[] plainBinary=StringToBinary(str2);
		String str3=ciphertextASCII.getText();
		int[] plaintext = Decrypt.decryptData(plainBinary, str1);
		outputB.append("数组类型8bits解密输出结果: " + Arrays.toString(plaintext) + "\n");

        //处理String类型的明文 ASCII 码输入
        String plaintextASCII = Decrypt.decryptASCII(str3, str1);
        outputB.append("ASCII 码解密输出结果: " + plaintextASCII + "\n");
        
	}
	
	public static int[] StringToBinary(String str) {
        int[] binary = new int[8];
        for (int i = 0; i < 8; i++) {
    		Character ch = str.charAt(i);
    		binary[i] = Integer.parseInt(ch.toString());
    		}

        return binary;
    }



}
