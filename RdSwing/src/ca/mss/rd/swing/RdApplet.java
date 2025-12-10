package ca.mss.rd.swing;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ca.mss.rd.util.UtilReflection;

abstract public class RdApplet extends JApplet {

	final static public String module = RdApplet.class.getName();
	final static public long serialVersionUID = module.hashCode();

	abstract protected String getAppClazz();
	abstract protected String getAppTitle();
	
	protected Dimension getPrefferedSize(){
		return null;
	}
	
	protected boolean isMaximizedWhenOpen(){
		return true;
	}

	public RdApplet() throws HeadlessException {
		super();
	}
	
	@Override
	public void init() {
		super.init();

		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);	

		RdAppPanel app = (RdAppPanel )UtilReflection.instantiateObject(getAppClazz());
		app.setMenuBar(menu);
		app.init();
		
		getContentPane().add(app);
	}
	

	abstract public void exit();
	
	public void standalone() {

		init();

		JFrame frame = new JFrame(getAppTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Catch window closing event
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
				System.exit(0);
			}

		});

		// Add content
		frame.getContentPane().add(this);

		// Set minimized frame size
		frame.pack(); 
		
		if( getPrefferedSize() != null )
			frame.setSize(getPrefferedSize());

		// Maximize frame
		if( isMaximizedWhenOpen() )
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		frame.setVisible(true);
	}

	
	final static String[] prefferedLF = new String[]{"Nimbus", "CDE/Motif", "Windows", "Windows Classic", "Metal"};
	
	static {

		Map<String, LookAndFeelInfo> availableLF = new HashMap<String, LookAndFeelInfo>();
		
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			availableLF.put(info.getName(), info);
	    }

		for(String name: prefferedLF){
			if( availableLF.containsKey(name) ){
				try {
					UIManager.setLookAndFeel(availableLF.get(name).getClassName());
				} catch (Exception e) {
					System.err.println("Available look and feels are ["+availableLF.toString()+"]");
				}
				break;
			}
		}
	}
}
