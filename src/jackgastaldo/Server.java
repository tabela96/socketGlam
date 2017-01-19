package jackgastaldo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;

public class Server {

	protected Shell shlIscrizioni;
	private ServerSocket ss;
	private ArrayList<String> nomi = new ArrayList<String>();
	private Connection cn;
	private Statement st;
	private ResultSet rs;
	private String sql;
	private boolean ciao = false;

	/**
	 * Launch the application.
	 * 
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
	public void open() throws SQLException {
		Display display = Display.getDefault();
		createContents();
		shlIscrizioni.open();
		shlIscrizioni.layout();
		avvia();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/glam?user=root&password="); // ERRORE
		while (!shlIscrizioni.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlIscrizioni = new Shell();
		shlIscrizioni.setSize(418, 404);
		shlIscrizioni.setText("Iscrizioni");

		List list = new List(shlIscrizioni, SWT.BORDER);
		list.setBounds(10, 10, 269, 310);

		Button btnSalva = new Button(shlIscrizioni, SWT.NONE);
		btnSalva.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nome = "";
				Socket s;
				try {
					s = ss.accept();
					InputStreamReader isr = new InputStreamReader(s.getInputStream());
					BufferedReader in = new BufferedReader(isr);
					nome = in.readLine();
					for (int i = 0; i < nomi.size(); i++) {
						System.out.println("nome: " + nomi.get(i) + " == " + nome);
						if (nomi.get(i).equals(nome)) {
							ciao = true;
						}
					}
					if (ciao == false) {
						sql = "";
						sql = "INSERT INTO iscrizioni (nome, dataOra) VALUES ('" + nome + "', NOW())";
						try {
							st = cn.createStatement();
							ciao = st.execute(sql);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						System.out.println(sql);
						JOptionPane.showMessageDialog(null, "" + nome + " inserito con successo",
								"Inserimento completato", JOptionPane.INFORMATION_MESSAGE);
						s.close();
					} else {
						JOptionPane.showMessageDialog(null, "Il nome esiste già.", "ATTENZIONE",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}
		});
		btnSalva.setBounds(193, 331, 86, 25);
		btnSalva.setText("Salva nel DB");

		DateTime filtroData = new DateTime(shlIscrizioni, SWT.BORDER);
		filtroData.setBounds(299, 31, 80, 24);
		filtroData.setVisible(false);

		Button btnFiltraPerData = new Button(shlIscrizioni, SWT.CHECK);
		btnFiltraPerData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnFiltraPerData.getSelection() == false) {
					filtroData.setVisible(false);
				} else {
					filtroData.setVisible(true);
				}
			}
		});
		btnFiltraPerData.setBounds(299, 10, 93, 16);
		btnFiltraPerData.setText("Filtra per data");
		Button btnCaricaIscritti = new Button(shlIscrizioni, SWT.NONE);
		btnCaricaIscritti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				list.removeAll();
				if (btnFiltraPerData.getSelection() == false) {
					sql = "SELECT nome FROM iscrizioni;";
					try {
						st = cn.createStatement();
						rs = st.executeQuery(sql);

						while (rs.next() == true) {
							nomi.add(rs.getString("nome"));
							list.add(rs.getString("nome"));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(sql);
				} else {
					String confrontoData = ""; //data del database
					String dataFiltro="";	//data del server
					Date data;
					sql = "SELECT dataOra FROM iscrizioni;";
					try {
						st = cn.createStatement();
						rs = st.executeQuery(sql);
						while (rs.next() == true) {
							confrontoData = rs.getString("dataOra");
							//System.out.println(confrontoData);
						}
						SimpleDateFormat formato=new SimpleDateFormat("yyyy-MMM-dd");
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnCaricaIscritti.setBounds(10, 331, 86, 25);
		btnCaricaIscritti.setText("Carica iscritti");

	}

	private void avvia() {
		try {
			ss = new ServerSocket(9999);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
