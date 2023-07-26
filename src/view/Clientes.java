package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import util.Validador;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Clientes extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textID;
	private JTextField textNome;
	private JTextField textFone;
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JTextField textEmail;
	private JTextField textCEP;
	private JTextField textEndereco;
	private JTextField textNum;
	private JTextField textComp;
	private JTextField textBairro;
	private JTextField textCidade;
	private JComboBox cboUF;
	private JList lista;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scroll.setVisible(false);
			}
		});
		setTitle("Impressora 3D - Clientes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/impressora fundo.png")));
		setBounds(100, 100, 571, 446);
		getContentPane().setLayout(null);

		scroll = new JScrollPane();
		scroll.setVisible(false);
		scroll.setBounds(73, 75, 169, 55);
		getContentPane().add(scroll);
		
				lista = new JList();
				scroll.setViewportView(lista);
				lista.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarLista();
					}
				});

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(40, 34, 23, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setBounds(28, 59, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setBounds(28, 87, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Email:");
		lblNewLabel_3.setBounds(28, 116, 46, 14);
		getContentPane().add(lblNewLabel_3);

		textID = new JTextField();
		textID.setEditable(false);
		textID.setBounds(73, 31, 86, 20);
		getContentPane().add(textID);
		textID.setColumns(10);

		textNome = new JTextField();
		textNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		textNome.setBounds(74, 56, 168, 20);
		getContentPane().add(textNome);
		textNome.setColumns(10);
		textNome.setDocument(new Validador(30));

		textFone = new JTextField();
		textFone.setBounds(74, 84, 133, 20);
		getContentPane().add(textFone);
		textFone.setColumns(10);
		textFone.setDocument(new Validador(11));

		btnCreate = new JButton("");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Clientes.class.getResource("/img/create.png")));
		btnCreate.setBounds(28, 289, 64, 64);
		getContentPane().add(btnCreate);

		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/clear.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(252, 82, 48, 48);
		getContentPane().add(btnLimpar);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCliente();
			}
		});
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Clientes.class.getResource("/img/lupa.png")));
		btnBuscar.setBounds(252, 34, 48, 48);
		getContentPane().add(btnBuscar);
		getRootPane().setDefaultButton(btnBuscar);

		btnDelete = new JButton("");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		btnDelete.setIcon(new ImageIcon(Clientes.class.getResource("/img/delete.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(86, 289, 64, 64);
		getContentPane().add(btnDelete);

		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCliente();

			}
		});
		btnUpdate.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBounds(144, 289, 64, 64);
		getContentPane().add(btnUpdate);

		textEmail = new JTextField();
		textEmail.setBounds(73, 112, 169, 20);
		getContentPane().add(textEmail);
		textEmail.setColumns(10);
		textEmail.setDocument(new Validador(20));

		textCEP = new JTextField();
		textCEP.setBounds(73, 145, 147, 20);
		getContentPane().add(textCEP);
		textCEP.setColumns(10);
		textCEP.setDocument(new Validador(8));

		textEndereco = new JTextField();
		textEndereco.setBounds(73, 176, 147, 20);
		getContentPane().add(textEndereco);
		textEndereco.setColumns(10);
		textEndereco.setDocument(new Validador(50));

		textNum = new JTextField();
		textNum.setBounds(73, 207, 57, 20);
		getContentPane().add(textNum);
		textNum.setColumns(10);
		textNum.setDocument(new Validador(5));

		textComp = new JTextField();
		textComp.setBounds(95, 238, 147, 20);
		getContentPane().add(textComp);
		textComp.setColumns(10);
		textComp.setDocument(new Validador(30));

		JButton btnCEP = new JButton("Buscar CEP");
		btnCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCEP.setBounds(230, 143, 102, 23);
		getContentPane().add(btnCEP);

		textBairro = new JTextField();
		textBairro.setBounds(300, 176, 86, 20);
		getContentPane().add(textBairro);
		textBairro.setColumns(10);
		textBairro.setDocument(new Validador(20));

		textCidade = new JTextField();
		textCidade.setBounds(300, 207, 86, 20);
		getContentPane().add(textCidade);
		textCidade.setColumns(10);
		textCidade.setDocument(new Validador(20));

		cboUF = new JComboBox();
		cboUF.setBounds(300, 239, 46, 22);
		getContentPane().add(cboUF);
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

		JLabel lblNewLabel_4 = new JLabel("CEP:");
		lblNewLabel_4.setBounds(28, 148, 35, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Endereço:");
		lblNewLabel_5.setBounds(6, 179, 57, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Número:");
		lblNewLabel_6.setBounds(16, 210, 58, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Complemento:");
		lblNewLabel_7.setBounds(6, 243, 86, 14);
		getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Bairro:");
		lblNewLabel_8.setBounds(254, 179, 46, 14);
		getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Cidade:");
		lblNewLabel_9.setBounds(244, 210, 46, 14);
		getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("UF:");
		lblNewLabel_10.setBounds(265, 241, 35, 14);
		getContentPane().add(lblNewLabel_10);

	}

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String cep = textCEP.getText();
		String resultado = null;
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					textCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					textBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("0")) {
						JOptionPane.showMessageDialog(null, "CEP inválido");
					}

				}

			}

			textEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}

	private void adicionarCliente() {
		// System.out.println("teste do botao adicionar contato");

		// validação de campos obrogatórios
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			textNome.requestFocus();
		} else if (textFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o telefone do cliente");
			textFone.requestFocus();
		} else if (textEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o email do cliente");
			textEmail.requestFocus();
		} else if (textCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o CEP do cliente");
			textCEP.requestFocus();
		} else if (textEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Endereço do cliente");
			textEndereco.requestFocus();
		} else if (textNum.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Numero do endereço do cliente");
			textNum.requestFocus();
		} else if (textBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Bairro do endereço do cliente");
			textBairro.requestFocus();
		} else if (textCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite a Cidade do endereço do cliente");
			textCidade.requestFocus();
		} else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite o Estado do endereço do cliente");
			cboUF.requestFocus();

		} else {
			// lógica principal
			// a linha abaixo cria uma variavel
			String create = "insert into Clientes(nome,fone,email,cep,endereco,numeroendereco,complemento,bairro,cidade,uf) values (?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceção
			try {
				// abrir conexão
				con = dao.conectar();

				// uso da class para executar a instrução sql e replicar no banco
				pst = con.prepareStatement(create);

				// setar os parametros (?,?,?) de acordo com os preenchimentos das caixas de
				// texto
				pst.setString(1, textNome.getText());
				pst.setString(2, textFone.getText());
				pst.setString(3, textEmail.getText());
				pst.setString(4, textCEP.getText());
				pst.setString(5, textEndereco.getText());
				pst.setString(6, textNum.getText());
				pst.setString(7, textComp.getText());
				pst.setString(8, textBairro.getText());
				pst.setString(9, textCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "cliente adicionado com sucesso");

				// limpar campos
				limparCampos();

				// fechar conexão
				con.close();
			}

			catch (java.sql.SQLIntegrityConstraintViolationException e3) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n Telefone duplicado, tente outro");
				textFone.setText(null);
				textFone.requestFocus();

				//////// TROCa
			} catch (Exception e2) {
				System.out.println(e2);
			}

			/*
			 * //TROCAAAAAAAAAAAAAAAAAAA
			 * 
			 * JOptionPane.showMessageDialog(null,
			 * "ERRO!!!\n Email duplicado, tente outro"); textEmail.setText(null);
			 * textEmail.requestFocus(); } catch (Exception e4) { System.out.println(e4); }
			 */

		}
	} // fim do metodo adicionar contato

	private void buscarCliente() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			textNome.requestFocus(); // setar o cursor na caixa de texto
		} else {

			// System.out.println("teste do botão buscar");
			String read = "select * from Clientes where nome = ?";
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a execução da query
				pst = con.prepareStatement(read);

				// pegar o conteudo da caixa de texto e substituir o parâmetro
				pst.setString(1, textNome.getText());

				// uso do resultset para obter os dados do contato
				rs = pst.executeQuery();

				// se existir um contato cadastrado
				if (rs.next()) {
					// preencher as caixar de texto com numero e email
					textID.setText(rs.getString(1)); // 1 = I
					textFone.setText(rs.getString(3)); // 3 = FONE
					textEmail.setText(rs.getString(4)); // 4 = EMAIL
					textCEP.setText(rs.getString(5));
					textEndereco.setText(rs.getString(6));
					textNum.setText(rs.getString(7));
					textComp.setText(rs.getString(8));
					textBairro.setText(rs.getString(9));
					textCidade.setText(rs.getString(10));
					cboUF.setSelectedItem(rs.getString(11));
					// desativar o botão adicionat
					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Cliente não encontrado");
					scroll.setVisible(false);
					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);
				}
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		lista.setModel(modelo);
		String readlista = "select * from Clientes where nome like '" + textNome.getText() + "%'" + "order by nome";

		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();

			//if (rs.next()) {
				while(rs.next()) {
					
				
				scroll.setVisible(true);
				modelo.addElement(rs.getString(2));

				if (textNome.getText().isEmpty()) {
					scroll.setVisible(false);
				}

			}
				//} else {
				//	scroll.setVisible(false);
				//	btnCreate.setEnabled(true);
				//}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarLista() {

		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do Cliente");
			textNome.requestFocus(); // setar o cursor na caixa de texto
		} else {
			int linha = lista.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from Clientes where nome like '" + textNome.getText() + "%'"
						+ "order by nome limit " + linha + " , 1";

				try {
					// abrir conexão
					con = dao.conectar();
					// preparar a execução da query
					pst = con.prepareStatement(read2);
					// uso do resultset para obter os dados do contato
					rs = pst.executeQuery();

					// se existir um contato cadastrado
					if (rs.next()) {
						// preencher as caixar de texto com numero e email
						textID.setText(rs.getString(1)); // 1 = ID
						textNome.setText(rs.getString(2));
						textFone.setText(rs.getString(3)); // 3 = FONE
						textEmail.setText(rs.getString(4));
						textCEP.setText(rs.getString(5));
						textEndereco.setText(rs.getString(6));
						textNum.setText(rs.getString(7));
						textComp.setText(rs.getString(8));
						textBairro.setText(rs.getString(9));
						textCidade.setText(rs.getString(10));
						cboUF.setSelectedItem(rs.getString(11));
						
       

						// desativar o botão adicionat
						scroll.setVisible(false);
						btnCreate.setEnabled(false);
						btnBuscar.setEnabled(false);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);

					} else {
						JOptionPane.showMessageDialog(null, "Cliente não encontrado");

						btnCreate.setEnabled(true);
						btnBuscar.setEnabled(false);
					}
					// fechar conexão
					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	// metodo para editar tudo menos a senha
	private void editarCliente() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			textNome.requestFocus();
		} else if (textFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o telefone do cliente");
			textFone.requestFocus();
		} else if (textEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o email do cliente");
			textEmail.requestFocus();
		} else if (textCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o CEP do cliente");
			textCEP.requestFocus();
		} else if (textEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Endereço do cliente");
			textEndereco.requestFocus();
		} else if (textNum.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Numero do endereço do cliente");
			textNum.requestFocus();
		} else if (textBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Bairro do endereço do cliente");
			textBairro.requestFocus();
		} else if (textCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite a Cidade do endereço do cliente");
			textCidade.requestFocus();
		} else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite o Estado do endereço do cliente");
			cboUF.requestFocus();

		} else {
			// lógica principal
			// criando uma variavel do tipo string
			String update = "update Clientes set nome = ?, fone = ?, email = ?, cep = ?, endereco = ?, numeroendereco = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? where idcli = ?";

			// tratamento de excessões
			try {
				con = dao.conectar();

				// preparar a query(substituir ????)
				pst = con.prepareStatement(update);
				pst.setString(1, textNome.getText());
				pst.setString(2, textFone.getText());
				pst.setString(3, textEmail.getText());
				pst.setString(4, textCEP.getText());
				pst.setString(5, textEndereco.getText());
				pst.setString(6, textNum.getText());
				pst.setString(7, textComp.getText());
				pst.setString(8, textBairro.getText());
				pst.setString(9, textCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, textID.getText());

				// executar update
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente editado com sucesso");
				con.close();
				// limpar campos
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n telefone duplicado, tente outro");
				textFone.setText(null);
				textFone.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}

		}
	}

	/**
	 * Método excluir cliente
	 */
	private void excluirCliente() {
		// System.out.println("teste do botão excluir");

		// confirmação
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão deste Cliente?", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// logica pricipal
			String delete = "delete from Clientes where idcli = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente excluido");
				limparCampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void limparCampos() {
		textID.setText(null);
		textNome.setText(null);
		textFone.setText(null);
		textEmail.setText(null);
		textCEP.setText(null);
		textEndereco.setText(null);
		textNum.setText(null);
		textBairro.setText(null);
		textCidade.setText(null);
		cboUF.setSelectedItem(null);
		textComp.setText(null);

		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);

	}
}
