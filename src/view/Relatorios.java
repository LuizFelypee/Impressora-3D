package view;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Relatorios extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pst;
	
	private JButton btnrelClientes;
	private JComboBox cboOS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
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
	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/impressora 3D.png")));
		setTitle("Impressora 3D - Relatorios");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		btnrelClientes = new JButton("Clientes");
		btnrelClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnrelClientes.setBounds(34, 42, 89, 23);
		getContentPane().add(btnrelClientes);
		
		cboOS = new JComboBox();
		cboOS.setModel(new DefaultComboBoxModel(new String[] {"", "AGUARDANDO APROVAÇÃO DO CLIENTE", "AGUARDANDO PEÇAS", "AGUARDANDO DIAGNOSTICO"}));
		cboOS.setBounds(34, 123, 365, 22);
		getContentPane().add(cboOS);
		
		JButton btnPendente = new JButton(" OS Pendente");
		btnPendente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioPendente();
			}
		});
		btnPendente.setBounds(148, 42, 124, 23);
		getContentPane().add(btnPendente);
		
		JButton btnPecas = new JButton("Peças");
		btnPecas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioPecas();
			}
		});
		btnPecas.setBounds(310, 42, 114, 23);
		getContentPane().add(btnPecas);

	} //fim do construtor
	
	private void relatorioClientes() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Clientes.pdf"));
			document.open();
			Date datarel = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(datarel)));
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));
			
			String readClientes = "select nome,fone,email from Clientes order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(3);
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("Clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private void relatorioPendente() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		
		if(cboOS.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "selecione o Status da ordem de serviço");
			cboOS.requestFocus();
		}
		else if(cboOS.getSelectedItem().equals("AGUARDANDO DIAGNOSTICO")){
				
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Aguardando_diagnostico.pdf"));
			document.open();
			Date datarel = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(datarel)));
			document.add(new Paragraph("Ordens de serviço sem Técnico:"));
			document.add(new Paragraph(" "));
			
			String readTecnico = "select servicos.os,servicos.modelo,servicos.marca,servicos.defeito,date_format(servicos.dataOS,'%d/%m/%Y') from servicos where statusOS = 'AGUARDANDO DIAGNOSTICO'  order by dataOS";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readTecnico);
				rs = pst.executeQuery();
				
				PdfPTable tabela1 = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("ID da ordem de serviço")); 
				PdfPCell col2 = new PdfPCell(new Paragraph("Impressora - Modelo"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Impressora - Marca"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Data"));
				tabela1.addCell(col1);
				tabela1.addCell(col2);
				tabela1.addCell(col3);
				tabela1.addCell(col4);
				tabela1.addCell(col5);
				while (rs.next()) {
					tabela1.addCell(rs.getString(1));
					tabela1.addCell(rs.getString(2));
					tabela1.addCell(rs.getString(3));
					tabela1.addCell(rs.getString(4));
					tabela1.addCell(rs.getString(5));
				}
				document.add(tabela1);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("Aguardando_diagnostico.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
		//Aguardando peças -----------------------------------------------------------------------------
		else if(cboOS.getSelectedItem().equals("AGUARDANDO PEÇAS")){
			try {
				PdfWriter.getInstance(document, new FileOutputStream("Aguardando_pecas.pdf"));
				document.open();
				Date datarel = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(formatador.format(datarel)));
				document.add(new Paragraph("Ordens de serviço com peças faltando:"));
				document.add(new Paragraph(" "));
				
				String readTecnico = "select servicos.os,servicos.modelo,servicos.marca,servicos.defeito,servicos.diagnostico,tecnicos.nome,date_format(servicos.dataOS,'%d/%m/%Y') from servicos inner join tecnicos on servicos.idtec = tecnicos.idtec  where statusOS = 'AGUARDANDO PEÇAS'  order by dataOS";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(readTecnico);
					rs = pst.executeQuery();
					
					PdfPTable tabela1 = new PdfPTable(7);
					PdfPCell col1 = new PdfPCell(new Paragraph("ID da ordem de serviço"));
					PdfPCell col2 = new PdfPCell(new Paragraph("Impressora - Modelo"));
					PdfPCell col3 = new PdfPCell(new Paragraph("Impressora - Marca"));
					PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
					PdfPCell col5 = new PdfPCell(new Paragraph("Diagnóstico"));
					PdfPCell col6 = new PdfPCell(new Paragraph("Técnico"));
					PdfPCell col7 = new PdfPCell(new Paragraph("Data"));
					tabela1.addCell(col1);
					tabela1.addCell(col2);
					tabela1.addCell(col3);
					tabela1.addCell(col4);
					tabela1.addCell(col5);
					tabela1.addCell(col6);
					tabela1.addCell(col7);
					while (rs.next()) {
						tabela1.addCell(rs.getString(1));
						tabela1.addCell(rs.getString(2));
						tabela1.addCell(rs.getString(3));
						tabela1.addCell(rs.getString(4));
						tabela1.addCell(rs.getString(5));
						tabela1.addCell(rs.getString(6));
						tabela1.addCell(rs.getString(7));
					}
					document.add(tabela1);
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			document.close();
			try {
				Desktop.getDesktop().open(new File("Aguardando_pecas.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
		
		//aguardando APROVAÇÃO
		else if(cboOS.getSelectedItem().equals("AGUARDANDO APROVAÇÃO DO CLIENTE")){
			try {
				PdfWriter.getInstance(document, new FileOutputStream("Aguardando_aprovacao_do_cliente.pdf"));
				document.open();
				Date datarel = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(formatador.format(datarel)));
				document.add(new Paragraph("Ordens de serviço AGUARDANDO APROVAÇÃO DO CLIENTE :"));
				document.add(new Paragraph(" "));
				
				String readTecnico = "select servicos.os,servicos.marca,servicos.modelo,servicos.defeito,servicos.diagnostico,servicos.valor,date_format(servicos.dataOS,'%d/%m/%Y'),Clientes.nome,Clientes.fone from servicos inner join Clientes on servicos.idcli = Clientes.idcli  where statusOS = 'AGUARDANDO APROVAÇÃO DO CLIENTE'  order by dataOS";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(readTecnico);
					rs = pst.executeQuery();
					
					PdfPTable tabela1 = new PdfPTable(9);
					PdfPCell col1 = new PdfPCell(new Paragraph("ID da ordem de serviço"));
					PdfPCell col2 = new PdfPCell(new Paragraph("Impressora - Marca"));
					PdfPCell col3 = new PdfPCell(new Paragraph("Impressora - Modelo"));
					PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
					PdfPCell col5 = new PdfPCell(new Paragraph("Diagnóstico"));
					PdfPCell col6 = new PdfPCell(new Paragraph("Valor"));
					PdfPCell col7 = new PdfPCell(new Paragraph("Data"));
					PdfPCell col8 = new PdfPCell(new Paragraph("Nome do cliente"));
					PdfPCell col9 = new PdfPCell(new Paragraph("Telefone do cliente"));
					tabela1.addCell(col1);
					tabela1.addCell(col2);
					tabela1.addCell(col3);
					tabela1.addCell(col4);
					tabela1.addCell(col5);
					tabela1.addCell(col6);
					tabela1.addCell(col7);
					tabela1.addCell(col8);
					tabela1.addCell(col9);
					while (rs.next()) {
						tabela1.addCell(rs.getString(1));
						tabela1.addCell(rs.getString(2));
						tabela1.addCell(rs.getString(3));
						tabela1.addCell(rs.getString(4));
						tabela1.addCell(rs.getString(5));
						tabela1.addCell(rs.getString(6));
						tabela1.addCell(rs.getString(7));
						tabela1.addCell(rs.getString(8));
						tabela1.addCell(rs.getString(9));
					}
					document.add(tabela1);
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			document.close();
			try {
				Desktop.getDesktop().open(new File("Aguardando_aprovacao_do_cliente.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
			
		}
	
	private void relatorioPecas() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Pecas.pdf"));
			document.open();
			Date datarel = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(datarel)));
			document.add(new Paragraph("Peças com estoque baixo:"));
			document.add(new Paragraph(" "));
			
			String readClientes = "select pecas.peca, pecas.idpecas, pecas.estoque, pecas.estoquemin, fornecedores.nome, fornecedores.fone  from pecas inner join fornecedores on pecas.idfor = fornecedores.idfor where estoquemin >= estoque order by peca";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(6);
				PdfPCell col1 = new PdfPCell(new Paragraph("Peça"));
				PdfPCell col2 = new PdfPCell(new Paragraph("ID da peça"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Estoque"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Estoque minimo"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Fornecedor"));
				PdfPCell col6 = new PdfPCell(new Paragraph("telefone do fornecedor"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				
				
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					
					
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("Pecas.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
} //fim do codigo
