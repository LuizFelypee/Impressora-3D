package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import javax.swing.JCheckBox;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class Usuarios extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JComboBox cboPerfil;
	private JCheckBox chkSenha;
	private JList<String> listaUsuarios;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setTitle("Usuários");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 596, 369);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(81, 138, 420, 89);
		getContentPane().add(scrollPane);
		
				listaUsuarios = new JList();
				scrollPane.setViewportView(listaUsuarios);
				listaUsuarios.setBorder(null);
				listaUsuarios.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarUsuarioLista();
					}
				});

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(36, 38, 46, 14);
		getContentPane().add(lblNewLabel);

		txtID = new JTextField();
		txtID.setBounds(81, 35, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(36, 123, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(36, 80, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setBounds(36, 166, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtNome = new JTextField();
		txtNome.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBounds(81, 117, 420, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		txtLogin = new JTextField();
		txtLogin.setBounds(80, 77, 162, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		JButton btnCreate = new JButton("");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/create.png")));
		btnCreate.setToolTipText("Adicionar");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnCreate.setBounds(36, 267, 89, 52);
		getContentPane().add(btnCreate);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(81, 163, 218, 20);
		getContentPane().add(txtSenha);

		JButton btnBuscar = new JButton("");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/lupa.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		btnBuscar.setBounds(252, 59, 89, 39);
		getContentPane().add(btnBuscar);

		JButton btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ler o status do checkbox, escolhendo o método de acordo com a seleção da
				// caixa
				if (chkSenha.isSelected()) {
					editarUsuarioSenha();
				} else {
					editarUsuario();
				}
			}
		});
		btnEditar.setBounds(118, 267, 89, 52);
		getContentPane().add(btnEditar);

		JButton btnExcluir = new JButton("");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setBounds(217, 267, 89, 52);
		getContentPane().add(btnExcluir);

		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setBounds(338, 166, 46, 14);
		getContentPane().add(lblNewLabel_4);

		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(385, 162, 116, 22);
		getContentPane().add(cboPerfil);

		chkSenha = new JCheckBox("Alterar a senha");
		chkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSenha.setEditable(true);
				txtSenha.setText(null);
				txtSenha.requestFocus();
				txtSenha.setBackground(Color.YELLOW);
			}
		});
		chkSenha.setVisible(false);
		chkSenha.setBounds(36, 209, 185, 23);
		getContentPane().add(chkSenha);

		JButton btnLimpar = new JButton("");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/clear.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(390, 267, 89, 52);
		getContentPane().add(btnLimpar);

	}// fim do construtor

	/**
	 * Método para adicionar um novo usuário
	 */
	private void adicionarUsuario() {
		// validação do combobox
		// if(cboPerfil.getSelectedItem().equal("") )
		// System.out.println("teste do botão adicionar");
		String create = "insert into usuarios(nome,login,senha,perfil) values(?,?,md5(?),?)";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(create);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, txtSenha.getText());
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
			con.close();
			// tratamento de exceção em caso de duplicação do login
		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está sendo utilizado.");
			txtLogin.setText(null);
			txtLogin.requestFocus();
		} catch (Exception e2) {
			System.out.println(e2);
		}
	}// fim do método novoUsuario

	/**
	 * Método para buscar um usuário pelo login
	 */
	private void buscarUsuario() {
		// System.out.println("teste do botão buscar");
		String read = "select * from usuarios where login = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtSenha.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));
				// mostrar o checkbox (troca de senha)
				chkSenha.setVisible(true);
				// desabilitar a caixa de senha
				txtSenha.setEditable(false);
			} else {
				System.out.println("usuário não cadastrado");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método buscarUsuario

	/**
	 * Método para listar os usuários (pesquisa avançada)
	 */
	private void listarUsuarios() {
		// System.out.println("teste");
		// criar um objeto -> lista (vetor dinâmico) para exibir a
		// lista de usuários do banco na pesquisa avançada
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios.setModel(modelo);
		// CRUD Read
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			// enquanto existir usuários que começam com a(s) letra(s)
			while (rs.next()) {
				// exibir a lista
				scrollPane.setVisible(true);
				// exibir os usuários na lista na lista
				modelo.addElement(rs.getString(2));
				// Dica Luan (UX - esconder lista - semelhante google)
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para buscar um usuário da lista
	 */
	private void buscarUsuarioLista() {
		// selecionar a linha da lista ("indice do vetor")
		int linha = listaUsuarios.getSelectedIndex();
		// se a linha(índice) da lista for selecionada
		if (linha >= 0) {
			System.out.println(linha);
			// limit - instrução sql que limita o número de registros selecionados da tabela
			// (índice,numéro de registros) Ex (1,1)
			String read2 = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					// mostrar o checkbox (troca de senha)
					chkSenha.setVisible(true);
					// desabilitar a caixa de senha
					txtSenha.setEditable(false);
				} else {
					System.out.println("usuário não cadastrado");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			//se não existir um índice(usuário) na lista
			scrollPane.setVisible(false);
		}
	}// fim do método buscarUsuario

	/**
	 * Método para editar os dados do usuário e senha
	 */
	private void editarUsuarioSenha() {
		// System.out.println("teste do botão editar");
		String update = "update usuarios set nome=?,login=?,senha=md5(?),perfil=? where iduser=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, txtSenha.getText());
			pst.setString(4, cboPerfil.getSelectedItem().toString());
			pst.setString(5, txtID.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Dados do usuário editados com sucesso");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método editarUsuarioSenha

	/**
	 * Método para editar os dados do usuário exceto a senha
	 */
	private void editarUsuario() {
		// System.out.println("teste do botão editar");
		String update = "update usuarios set nome=?,login=?,perfil=? where iduser=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtLogin.getText());
			pst.setString(3, cboPerfil.getSelectedItem().toString());
			pst.setString(4, txtID.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Dados do usuário editados com sucesso");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do método editarUsuario

	/**
	 * Método usado para excluir um usuário
	 */
	private void excluirUsuario() {
		// System.out.println("teste do botão excluir");
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?", "ATENÇÃO!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where iduser=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário excluído");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do método excluirUsuario

	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		txtSenha.setEditable(true);
		cboPerfil.setSelectedItem("");
		scrollPane.setVisible(false);
		chkSenha.setVisible(false);
	}
}// fim do código
