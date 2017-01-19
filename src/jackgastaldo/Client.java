package jackgastaldo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Client {

	protected Shell shlClient;
	private Socket s;
	private Text txtNome;


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
		shlClient.open();
		shlClient.layout();
		avvia();
		while (!shlClient.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlClient = new Shell();
		shlClient.setSize(310, 91);
		shlClient.setText("Client");
		
		Label lblNome = new Label(shlClient, SWT.NONE);
		lblNome.setBounds(10, 20, 36, 15);
		lblNome.setText("Nome:");
		
		txtNome = new Text(shlClient, SWT.BORDER);
		txtNome.setBounds(59, 17, 124, 21);
		
		Button btnConferma = new Button(shlClient, SWT.NONE);
		btnConferma.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nome="";
				if(txtNome.getText()==""){
					JOptionPane.showMessageDialog(null, "Inserisci il nome!", "Errore!", JOptionPane.ERROR_MESSAGE);
				}else{
					nome=txtNome.getText();
					try {
						PrintWriter out=new PrintWriter(s.getOutputStream(), true);
						out.println(nome);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnConferma.setBounds(209, 15, 75, 25);
		btnConferma.setText("CONFERMA");

	}
	
	private void avvia(){
		try {
			s=new Socket("localhost", 9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
