package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import model.DAO;
import util.Validador;

public class Servico extends JDialog {

	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JTextField txtID;
	private JTextField txtIDcli;
	private JTextField txtValor;
	private JTextField txtDefeito;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtDiagnostico;
	private JComboBox cboStatus;
	private JButton btnBuscarOS;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnLimpar;
	private JTextField txtNome;
	private JList listaUsuarios;
	private JScrollPane scrollPane;
	private JDateChooser txtDataSaida;
	private JDateChooser txtDataEntrada;
	private JTextField txtIDtec;
	private JTextField txtNome1;
	private JScrollPane scrollPane1;
	private JList listaUsuarios1;
	private JTextField txtUser;

	public String usuario;
	private JButton btnOS;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servico dialog = new Servico();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Servico() {
		addWindowListener(new WindowAdapter() {
			
			public void windowActivated(WindowEvent e) {
				
				txtUser.setText(usuario);
			}
		});
		setTitle("Impressora 3D - Ordem de Serviço");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servico.class.getResource("/img/impressora 3D.png")));
		setBounds(100, 100, 845, 615);
		getContentPane().setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"T\u00C9CNICO", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(553, 11, 195, 135);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		scrollPane1 = new JScrollPane();
		scrollPane1.setVisible(false);
		scrollPane1.setBounds(10, 64, 175, 60);
		panel_2.add(scrollPane1);

		listaUsuarios1 = new JList();
		listaUsuarios1.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				buscarUsuariosLista();
			}
		});
		scrollPane1.setViewportView(listaUsuarios1);

		txtIDtec = new JTextField();
		txtIDtec.setEditable(false);
		txtIDtec.setColumns(10);
		txtIDtec.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"ID T\u00C9CNICO", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		txtIDtec.setBounds(10, 78, 175, 40);
		panel_2.add(txtIDtec);

		txtNome1 = new JTextField();
		txtNome1.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome1.setColumns(10);
		txtNome1.setBorder(new TitledBorder(null, "NOME", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtNome1.setBounds(10, 24, 175, 43);
		panel_2.add(txtNome1);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "CLIENTE",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(348, 11, 195, 135);
		getContentPane().add(panel);
		panel.setLayout(null);

		
		setLocationRelativeTo(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(10, 64, 175, 60);
		panel.add(scrollPane);

		listaUsuarios = new JList();
		listaUsuarios.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e) {
				buscarClientesLista();
			}
		});
		scrollPane.setViewportView(listaUsuarios);

		txtIDcli = new JTextField();
		txtIDcli.setEditable(false);
		txtIDcli.setBorder(new TitledBorder(null, "ID CLIENTE", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtIDcli.setBounds(10, 78, 175, 40);
		panel.add(txtIDcli);
		txtIDcli.setColumns(10);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBorder(new TitledBorder(null, "NOME", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtNome.setBounds(10, 24, 175, 43);
		panel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBorder(new TitledBorder(null, "OS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtID.setBounds(131, 11, 111, 48);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("DEFEITO");
		lblNewLabel_2.setBounds(10, 298, 64, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("DIAGNÓSTICO");
		lblNewLabel_3.setBounds(10, 406, 93, 14);
		getContentPane().add(lblNewLabel_3);

		cboStatus = new JComboBox();
		cboStatus.setModel(new DefaultComboBoxModel(new String[] {"", "PRONTO", "AGUARDANDO APROVAÇÃO DO CLIENTE", "ORDEM NÃO APROVADA PELO CLIENTE", "AGUARDANDO PEÇAS", "AGUARDANDO DIAGNOSTICO"}));
		cboStatus.setBounds(131, 336, 335, 22);
		getContentPane().add(cboStatus);

		JLabel lblNewLabel_5 = new JLabel("VALOR");
		lblNewLabel_5.setBounds(559, 459, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setText("0");
		txtValor.setBounds(637, 457, 111, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		txtDefeito = new JTextField();
		txtDefeito.setBounds(131, 287, 617, 38);
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);
		txtDefeito.setDocument(new Validador(200));

		txtDiagnostico = new JTextField();
		txtDiagnostico.setBounds(131, 382, 617, 64);
		getContentPane().add(txtDiagnostico);
		txtDiagnostico.setColumns(10);
		txtDiagnostico.setDocument(new Validador(200));

		JLabel lblNewLabel_11 = new JLabel("DATA ENTRADA");
		lblNewLabel_11.setBounds(10, 99, 91, 14);
		getContentPane().add(lblNewLabel_11);

		btnCreate = new JButton("");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setToolTipText("Adicionar OS");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarOS();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Servico.class.getResource("/img/create.png")));
		btnCreate.setBounds(75, 501, 64, 64);
		getContentPane().add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setToolTipText("Atualizar OS");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();
			}
		});
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setIcon(new ImageIcon(Servico.class.getResource("/img/update.png")));
		btnUpdate.setBounds(139, 501, 64, 64);
		getContentPane().add(btnUpdate);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setIcon(new ImageIcon(Servico.class.getResource("/img/clear.png")));
		btnLimpar.setBounds(336, 517, 48, 48);
		getContentPane().add(btnLimpar);

		btnDelete = new JButton("");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setToolTipText("Deletar OS");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setIcon(new ImageIcon(Servico.class.getResource("/img/delete.png")));
		btnDelete.setBounds(197, 501, 64, 64);
		getContentPane().add(btnDelete);

		JLabel lblNewLabel_12 = new JLabel("DATA SAIDA");
		lblNewLabel_12.setBounds(10, 460, 79, 13);
		getContentPane().add(lblNewLabel_12);

		btnBuscarOS = new JButton("");
		btnBuscarOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarOS.setContentAreaFilled(false);
		btnBuscarOS.setBorderPainted(false);
		btnBuscarOS.setToolTipText("Buscar OS");
		btnBuscarOS.setIcon(new ImageIcon(Servico.class.getResource("/img/lupa.png")));
		btnBuscarOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnBuscarOS.setBounds(252, 11, 64, 64);
		getContentPane().add(btnBuscarOS);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "APARELHO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 172, 400, 104);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("MARCA");
		lblNewLabel_6.setBounds(45, 25, 46, 14);
		panel_1.add(lblNewLabel_6);

		txtMarca = new JTextField();
		txtMarca.setBounds(123, 23, 248, 20);
		panel_1.add(txtMarca);
		txtMarca.setColumns(10);
		txtMarca.setDocument(new Validador(15));

		JLabel lblNewLabel_7 = new JLabel("MODELO");
		lblNewLabel_7.setBounds(45, 50, 74, 14);
		panel_1.add(lblNewLabel_7);

		txtModelo = new JTextField();
		txtModelo.setBounds(123, 54, 248, 20);
		panel_1.add(txtModelo);
		txtModelo.setColumns(10);
		txtModelo.setDocument(new Validador(20));

		JLabel lblNewLabel = new JLabel("GARANTIA DE 3 MESSES\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblNewLabel.setBounds(280, 459, 139, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("STATUS");
		lblNewLabel_1.setBounds(10, 341, 64, 13);
		getContentPane().add(lblNewLabel_1);

		txtDataEntrada = new JDateChooser();
		txtDataEntrada.setEnabled(false);
		txtDataEntrada.setBounds(131, 99, 138, 20);
		getContentPane().add(txtDataEntrada);

		txtDataSaida = new JDateChooser();
		txtDataSaida.setBounds(131, 453, 138, 20);
		getContentPane().add(txtDataSaida);

		txtUser = new JTextField();
		txtUser.setEditable(false);
		txtUser.setBounds(131, 130, 185, 20);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("USUÁRIO");
		lblNewLabel_4.setBounds(10, 132, 64, 14);
		getContentPane().add(lblNewLabel_4);

		btnOS = new JButton("");
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setContentAreaFilled(false);
		btnOS.setBorderPainted(false);
		btnOS.setIcon(new ImageIcon(Servico.class.getResource("/img/impressora OS.png")));
		btnOS.setToolTipText("Imprimir OS");
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(686, 501, 89, 64);
		getContentPane().add(btnOS);

	}

	
	private void buscarOS() {

		String numOS = JOptionPane.showInputDialog("Número da OS");

		
		String read = "select * from servicos where os = ? ";
		try {
			
			con = dao.conectar();
		
			pst = con.prepareStatement(read);
			
			pst.setString(1, numOS);
			
			rs = pst.executeQuery();
			if (rs.next()) {
				

				txtID.setText(rs.getString(1));
				txtIDcli.setText(rs.getString(2));
				txtDefeito.setText(rs.getString(3));
				txtDiagnostico.setText(rs.getString(4));
				cboStatus.setSelectedItem(rs.getString(5));
				txtValor.setText(rs.getString(6));
				
				txtMarca.setText(rs.getString(9));
				txtModelo.setText(rs.getString(10));

				txtIDtec.setText(rs.getString(11));
				txtUser.setText(rs.getString(12));

				
				String setarData = rs.getString(7);
				
				System.out.println(setarData);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				txtDataEntrada.setDate(dataFormatada);

				
				String setarDataSaida = rs.getString(8);
				if (setarDataSaida == null) {
					
				} else {
					
					
					Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataSaida);
					txtDataSaida.setDate(dataFormatada2);
				}

				
				btnCreate.setEnabled(false);
				
				btnDelete.setEnabled(true);
				btnUpdate.setEnabled(true);

				
				btnBuscarOS.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "Ordem de Serviço Inexistente");
				
				btnCreate.setEnabled(true);
				
				btnBuscarOS.setEnabled(false);
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	private void listarClientes() {
		
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios.setModel(modelo);
		
		String readLista = "select * from Clientes where nome like '" + txtNome.getText() + "%'" + " order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery(readLista);
			
			
			while (rs.next()) {
				
				scrollPane.setVisible(true);

				
				modelo.addElement(rs.getString(2));
				
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	private void buscarClientesLista() {

		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Cliente");
			txtNome.requestFocus();
		} else {
			
			int linha = listaUsuarios.getSelectedIndex();
			if (linha >= 0) {
				
				String read2 = "select * from Clientes where nome like '" + txtNome.getText() + "%'"
						+ "order by nome limit " + (linha) + " , 1";
				try {
					
					con = dao.conectar();
					
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();
					if (rs.next()) {
						scrollPane.setVisible(false);
						txtIDcli.setText(rs.getString(1));
						txtNome.setText(rs.getString(2));
					} else {
						JOptionPane.showMessageDialog(null, "Cliente Inexistente");
						btnCreate.setEnabled(true);
						
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				scrollPane.setVisible(false);
			}
		}
	}

	private void listarUsuarios() {
		
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios1.setModel(modelo);
		
		String readLista = "select * from tecnicos where nome like '" + txtNome1.getText() + "%'" + " order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery(readLista);
			
			while (rs.next()) {
				
				scrollPane1.setVisible(true);

				
				modelo.addElement(rs.getString(2));
				
				if (txtNome1.getText().isEmpty()) {
					scrollPane1.setVisible(false);
				}
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void buscarUsuariosLista() {

		
		if (txtNome1.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Usuario");
			txtNome1.requestFocus(); 
		} else {
			
			int linha = listaUsuarios1.getSelectedIndex();
			if (linha >= 0) {
				
				String read2 = "select * from tecnicos where nome like '" + txtNome1.getText() + "%'"
						+ "order by nome limit " + (linha) + " , 1";
				try {
					
					con = dao.conectar();
					
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();
					if (rs.next()) {
						scrollPane1.setVisible(false);
						txtIDtec.setText(rs.getString(1));
						txtNome1.setText(rs.getString(2));
					} else {
						JOptionPane.showMessageDialog(null, "Usuario Inexistente");
						btnCreate.setEnabled(true);
						
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				scrollPane1.setVisible(false);
			}
		}
	}
	
	private void adicionarOS() {
		

		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Cliente");
			txtNome.requestFocus();
			
		} else if (txtMarca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Marca da IMpressora");
			txtMarca.requestFocus();
		} else if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Modelo da Impressora");
			txtModelo.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito da Impressora");
			txtDefeito.requestFocus();
		} else if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Status da Impressora");
			cboStatus.requestFocus();
		} 

		 else {
			
			String create = "insert into servicos (idcli,defeito,diagnostico,statusOS,valor,dataOSsaida,marca,modelo,idtec,usuario) values (?,?,?,?,?,?,?,?,?,?)";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(create);
				
				pst.setString(1, txtIDcli.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtDiagnostico.getText());
				pst.setString(4, cboStatus.getSelectedItem().toString());
				pst.setString(5, txtValor.getText());

				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				
				if (txtDataSaida.getDate() == null) {
					pst.setString(6, null);
				} else {
					String dataFormatada = formatador.format(txtDataSaida.getDate());
					pst.setString(6, dataFormatada);
				}

				pst.setString(7, txtMarca.getText());
				pst.setString(8, txtModelo.getText());
				pst.setString(9, txtIDtec.getText());
				pst.setString(10, txtUser.getText());
				if (!cboStatus.getSelectedItem().equals("AGUARDANDO DIAGNOSTICO") && txtIDtec.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o Tecnico");
					txtIDtec.requestFocus();
				} else {
					pst.setString(10, txtIDtec.getText());
					if (txtIDtec.getText().equals("")) {
						pst.setString(10, null);
					} else {
						pst.setString(10, txtIDtec.getText());
					}

				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Ordem de Serviço Cadastrada com Sucesso");
				
				limparCampos();
				
				con.close();
				
				}
			}
				
			

			catch (Exception e2) {
				System.out.println(e2);

			}

		}

	}
	
	private void editarOS() {

		if (txtIDcli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Cliente");
			txtIDcli.requestFocus();
			
		} else if (txtMarca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Marca do Aparelho");
			txtMarca.requestFocus();
		} else if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Modelo do Aparelho");
			txtModelo.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito do Aparelho");
			txtDefeito.requestFocus();
		} else if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Status do Aparelho");
			cboStatus.requestFocus();


		} else {
			
			String update = "update servicos set defeito=?, diagnostico=?, statusOS=?, valor=? , dataOSsaida=? , marca=?, modelo=? , idtec=? , usuario=? where os= ?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(update);
				pst.setString(1, txtDefeito.getText());
				pst.setString(2, txtDiagnostico.getText());
				pst.setString(3, cboStatus.getSelectedItem().toString());
				pst.setString(4, txtValor.getText());
				
				pst.setString(6, txtMarca.getText());
				pst.setString(7, txtModelo.getText());
				pst.setString(8, txtIDtec.getText());
				pst.setString(9, txtUser.getText());
				pst.setString(10, txtID.getText());

				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				
				if (txtDataSaida.getDate() == null) {
					pst.setString(5, null);
				} else {
					String dataFormatada = formatador.format(txtDataSaida.getDate());
					pst.setString(5, dataFormatada);
				}



				 pst.setString(8, txtIDtec.getText());


				pst.setString(8, txtIDtec.getText());




				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Dados da Ordem de Serviço alterado com Sucesso");
				
				con.close();
				
				limparCampos();
			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Ordem de Serviço não atualizada. \nEste Numero de OS está sendo Utilizado.");

				txtID.setText(null);
				txtID.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	
	private void excluirOS() {
		
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta OS?", "ATENÇÃO!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from servicos where os=?";
			try {

				
				con = dao.conectar();
				
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				
				pst.executeUpdate();
				
				limparCampos();
				JOptionPane.showMessageDialog(null, "Ordem de Serviço excluído");
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	
	

	
	private void recuperarOS() {
		String readOS = "select max(os) from servicos";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readOS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(3));

			} else {
				JOptionPane.showMessageDialog(null, "OS inexistente");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	
	private void imprimirOS(){
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a OS que deseja imprimir");
			txtID.requestFocus();
		}
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Os.pdf"));
			document.open();
			String readOS = "select servicos.os,servicos.idcli,servicos.modelo,servicos.marca,servicos.defeito,servicos.diagnostico,servicos.statusOS,servicos.valor,tecnicos.nome,date_format(servicos.dataOS,'%d/%m/%Y'),date_format(servicos.dataOSsaida,'%d/%m/%Y'), Clientes.idcli, Clientes.nome, Clientes.fone, Clientes.email, Clientes.cep, Clientes.endereco, Clientes.numeroendereco, Clientes.complemento, Clientes.bairro, Clientes.cidade, Clientes.uf from servicos inner join tecnicos on servicos.idtec = tecnicos.idtec inner join Clientes on servicos.idcli = Clientes.idcli where OS = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtID.getText());
				rs = pst.executeQuery();
				
				if (rs.next()) {
					Paragraph ordem = new Paragraph ("Informações da OS");
					ordem.setAlignment(Element.ALIGN_CENTER);
					document.add(ordem);
					
					Paragraph linha = new Paragraph (" ");
					document.add(linha);
					
					Paragraph data = new Paragraph ("Data de entrada: " + rs.getString(10));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
					
					Paragraph os = new Paragraph ("Ordem de serviço: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_LEFT);
					document.add(os);
					
					Paragraph status = new Paragraph ("Status:  " + rs.getString(7));
					status.setAlignment(Element.ALIGN_LEFT);
					document.add(status);
					
					Paragraph usuario = new Paragraph ("Usuario:  " + rs.getString(2));
					usuario.setAlignment(Element.ALIGN_LEFT);
					document.add(usuario);
					
					Paragraph impressora3D = new Paragraph ("Modelo:  " + rs.getString(3));
					impressora3D.setAlignment(Element.ALIGN_LEFT);
					document.add(impressora3D);
					
					Paragraph marca = new Paragraph ("Marca:  " + rs.getString(4));
					marca.setAlignment(Element.ALIGN_LEFT);
					document.add(marca);
					
					Paragraph defeito = new Paragraph ("Defeito:  " + rs.getString(5));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);
					
					Paragraph diagnostico = new Paragraph ("Diagnostico:  " + rs.getString(6));
					diagnostico.setAlignment(Element.ALIGN_LEFT);
					document.add(diagnostico);
					
					Paragraph valor = new Paragraph ("Valor:  " + rs.getString(8));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
					
					Paragraph tecnico = new Paragraph ("Tecnico: " + rs.getString(9));
					tecnico.setAlignment(Element.ALIGN_LEFT);
					document.add(tecnico);
					
					Paragraph dataSaida = new Paragraph ("Data de saida: " + rs.getString(11));
					dataSaida.setAlignment(Element.ALIGN_LEFT);
					document.add(dataSaida);
					
					document.add(linha);
					
					Paragraph cliente = new Paragraph ("Informações do cliente");
					cliente.setAlignment(Element.ALIGN_CENTER);
					document.add(cliente);
					
					document.add(linha);
					
					Paragraph idcli = new Paragraph ("ID do cliente: " + rs.getString(12));
					idcli.setAlignment(Element.ALIGN_LEFT);
					document.add(idcli);
					
					Paragraph nome = new Paragraph ("Nome do cliente: " + rs.getString(13));
					nome.setAlignment(Element.ALIGN_LEFT);
					document.add(nome);
					
					Paragraph fone = new Paragraph ("Telefone: " + rs.getString(14));
					fone.setAlignment(Element.ALIGN_LEFT);
					document.add(fone);
					
					Paragraph email = new Paragraph ("E-mail: " + rs.getString(15));
					email.setAlignment(Element.ALIGN_LEFT);
					document.add(email);
					
					Paragraph cep = new Paragraph ("CEP: " + rs.getString(16));
					cep.setAlignment(Element.ALIGN_LEFT);
					document.add(cep);
					
					Paragraph rua = new Paragraph ("Endereço: " + rs.getString(17));
					rua.setAlignment(Element.ALIGN_LEFT);
					document.add(rua);
					
					Paragraph num = new Paragraph ("Número: " + rs.getString(18));
					num.setAlignment(Element.ALIGN_LEFT);
					document.add(num);
					
					Paragraph comp = new Paragraph ("Complemento: " + rs.getString(19));
					comp.setAlignment(Element.ALIGN_LEFT);
					document.add(comp);
					
					Paragraph bairro = new Paragraph ("Bairro: " + rs.getString(20));
					bairro.setAlignment(Element.ALIGN_LEFT);
					document.add(bairro);
					
					Paragraph cidade = new Paragraph ("Cidade: " + rs.getString(21));
					cidade.setAlignment(Element.ALIGN_LEFT);
					document.add(cidade);
					
					Paragraph uf = new Paragraph ("Estado: " + rs.getString(22));
					uf.setAlignment(Element.ALIGN_LEFT);
					document.add(uf);
					
					Image imagem = Image.getInstance(Servico.class.getResource("/img/logo impressora 3D.jpg"));
					imagem.scaleToFit(250,386);
					imagem.setAbsolutePosition(350, 550);
					document.add(imagem);
					
					
					Paragraph texto5 = new Paragraph("            Assinatura do Cliente                                             Assinatura do responsável pela OS");
					texto5.setAlignment(Element.ALIGN_LEFT);
					document.add(texto5);
					
					Paragraph texto6 = new Paragraph("");
					texto6.setAlignment(Element.ALIGN_LEFT);
					document.add(texto6);
					
					
					
					
					Paragraph texto7 = new Paragraph("_____________________________");
					texto7.setAlignment(Element.ALIGN_LEFT);
					document.add(texto7);
					
					
					
					
				}
				con.close();
				
				} catch (Exception e) {
				 System.out.println(e);
			}
		}catch (Exception e) {
		 System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("Os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}

		
	}

	
	
	
	
	
	
	
	
	
	

	
	private void limparCampos() {
		txtID.setText(null);
		txtIDcli.setText(null);
		txtIDtec.setText(null);
		txtDefeito.setText(null);
		txtDiagnostico.setText(null);
		cboStatus.setSelectedItem("");
		txtValor.setText("0");
		txtDataEntrada.setDate(null);
		txtDataSaida.setDate(null);
		txtMarca.setText(null);
		txtModelo.setText(null);
		// txtTecnico.setText(null);
		txtNome.setText(null);
		txtNome1.setText(null);

		btnBuscarOS.setEnabled(true);
		btnCreate.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		scrollPane.setVisible(false);

	}
}
