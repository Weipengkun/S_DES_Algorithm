package UI;

import UI.MyCommandListener;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class ClearAllListen implements MyCommandListener {
	JTextField key,text,textASCII;
	JTextArea output;
	
	public void setJTextField1(JTextField text) {
		key=text;
	}
	public void setJTextField2(JTextField text) {
		this.text=text;
	}
	public void setJTextField3(JTextField text) {
		textASCII=text;
	}
	public void setJTextArea(JTextArea area) {
		output=area;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		key.setText("");
		text.setText("");
		textASCII.setText("");
		output.setText("");
        
	}

}
