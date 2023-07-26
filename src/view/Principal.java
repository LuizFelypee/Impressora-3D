package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;
	public JLabel lblUsuario; //public -> visível em outras classes
	public JButton btnUsuarios;
	public JButton btnRelatorio;
	public JPanel panelRodape;
	private JButton btnClientes;
	private JButton btnTecnicos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				setarData();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/impressora 3D.png")));
		setResizable(false);
		setTitle("Radical Motos ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelRodape = new JPanel();
		panelRodape.setBackground(SystemColor.desktop);
		panelRodape.setBounds(0, 353, 575, 38);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("");
		lblData.setBounds(10, 11, 325, 21);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblUsuarios = new JLabel("Usuario:");
		lblUsuarios.setForeground(Color.WHITE);
		lblUsuarios.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsuarios.setBounds(291, 5, 274, 27);
		panelRodape.add(lblUsuarios);
		
		lblUsuario = new JLabel("");
		lblUsuario.setBounds(364, 5, 211, 27);
		panelRodape.add(lblUsuario);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/impressora fundo.png")));
		lblLogo.setBounds(393, 178, 207, 181);
		contentPane.add(lblLogo);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setDefaultCapable(false);
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//evento clicar no botão
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/usuarios.png")));
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setBounds(21, 11, 118, 116);
		contentPane.add(btnUsuarios);
		
		JButton btnSobre = new JButton("");
		btnSobre.setDefaultCapable(false);
		btnSobre.setContentAreaFilled(false);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//as linhas abaixo fazem o link entre JFrame e JDialog
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
			
		});
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/info.png")));
		btnSobre.setBorderPainted(false);
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setBounds(486, 0, 89, 48);
		contentPane.add(btnSobre);
		
		btnClientes = new JButton("");
		btnClientes.setDefaultCapable(false);
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorderPainted(false);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//evento clicar no botão
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorderPainted(false);
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/cliente.png")));
		btnClientes.setToolTipText("Adicionar Cliente");
		btnClientes.setBounds(200, 28, 81, 81);
		contentPane.add(btnClientes);
		
		btnRelatorio = new JButton("");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
				
			}
		});
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.setBorderPainted(false);
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/relatorio.png")));
		btnRelatorio.setToolTipText("Relatório");
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setBounds(10, 171, 97, 89);
		contentPane.add(btnRelatorio);
		
		JButton btnServico = new JButton("");
		btnServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// evento de clicar no botão
				Servico servico = new Servico();
				servico.setVisible(true);
				servico.usuario = lblUsuario.getText();
			}
		});
		btnServico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServico.setIcon(new ImageIcon(Principal.class.getResource("/img/os.png")));
		btnServico.setContentAreaFilled(false);
		btnServico.setBorderPainted(false);
		btnServico.setToolTipText("Emitir OS");
		btnServico.setBounds(168, 171, 97, 89);
		contentPane.add(btnServico);
		
		btnTecnicos = 
				new JButton("");
		btnTecnicos.setDefaultCapable(false);
		btnTecnicos.setContentAreaFilled(false);
		btnTecnicos.setBorderPainted(false);
		btnTecnicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//evento clicar no botão
				Tecnicos tecnicos = new Tecnicos();
				tecnicos.setVisible(true);
			}
		});
		btnTecnicos.setToolTipText("Tecnicos");
		btnTecnicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTecnicos.setContentAreaFilled(false);
		btnTecnicos.setBorderPainted(false);
		btnTecnicos.setIcon(new ImageIcon(Principal.class.getResource("/img/tecnico.png")));
		btnTecnicos.setBounds(319, 28, 89, 74);
		contentPane.add(btnTecnicos);
	}// fim do construtor
	
	/**
	 * Método para setar data atual
	 */
	private void setarData() {
		//System.out.println("teste");
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
		
		
		
		
		
		
		
		
 
	}
}// fim do código
