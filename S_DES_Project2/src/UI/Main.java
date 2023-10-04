package UI;

public class Main {
	public static void main(String []args)
	{
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e) {
        	System.out.println(e);
        }
		ui win=new ui();
		win.displayWindow();
		PoliceListen police=new PoliceListen();
		PoliceListenB policeB=new PoliceListenB();
		ClearAllListen clPolice=new ClearAllListen();
		ClearAllListen clPoliceB=new ClearAllListen();
		win.setMyCommandListener(police);
		win.setMyCommandListenerB(policeB);
		win.setMyCommandListenerClear(clPolice);
		win.setMyCommandListenerClearB(clPoliceB);

	}
}
