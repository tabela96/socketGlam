package jackgastaldo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;

import java.io.IOException;
import java.net.ServerSocket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class Server {

	protected Shell shell;
	private ServerSocket ss;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server window = new Server();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		partenza();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(307, 404);
		shell.setText("SWT Application");
		
		List list = new List(shell, SWT.BORDER);
		list.setBounds(10, 10, 269, 310);
		
		Button btnRefresh = new Button(shell, SWT.NONE);
		btnRefresh.setBounds(107, 331, 75, 25);
		btnRefresh.setText("REFRESH");

	}
	
	private void partenza(){
		try {
			ss=new ServerSocket(9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
