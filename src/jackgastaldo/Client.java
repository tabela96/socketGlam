package jackgastaldo;

import java.net.Socket;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class Client {

	protected Shell shell;
	private Socket s;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Client window = new Client();
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
		shell.setSize(310, 91);
		shell.setText("SWT Application");
		
		Label lblNome = new Label(shell, SWT.NONE);
		lblNome.setBounds(10, 20, 55, 15);
		lblNome.setText("Nome:");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(59, 17, 124, 21);
		
		Button btnConferma = new Button(shell, SWT.NONE);
		btnConferma.setBounds(209, 15, 75, 25);
		btnConferma.setText("CONFERMA");

	}

}
