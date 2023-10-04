package UI;

import UI.MyCommandListener;
import functionalClass.Encrypt;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class PoliceListen implements MyCommandListener {
	JTextField keyA,plaintext,plaintextASCII;
	JTextArea outputA;
	
	public void setJTextField1(JTextField text) {
		keyA=text;
	}
	public void setJTextField2(JTextField text) {
		plaintext=text;
	}
	public void setJTextField3(JTextField text) {
		plaintextASCII=text;
	}
	public void setJTextArea(JTextArea area) {
		outputA=area;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String str1=keyA.getText();
		String str2=plaintext.getText();//将明文输入转为数组
		int[] plainBinary=StringToBinary(str2);
		String str3=plaintextASCII.getText();
		
		int[] ciphertext = Encrypt.encryptData(plainBinary, str1);
		outputA.append("数组类型8bits加密输出结果: " + Arrays.toString(ciphertext) + "\n");

        //处理String类型的明文 ASCII 码输入
        String ciphertextASCII = Encrypt.encryptASCII(str3, str1);
        outputA.append("ASCII 码加密输出结果: " + ciphertextASCII + "\n");
        
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
