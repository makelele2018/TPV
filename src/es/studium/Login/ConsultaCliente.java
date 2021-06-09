package es.studium.Login;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConsultaCliente implements ActionListener, WindowListener
{
	// Ventana Consulta de Clientes
	Frame frmConsultaClientes = new Frame("Consulta Clientes");
	TextArea listadoClientes = new TextArea(4, 30);
	Button btnPdfClientes = new Button("PDF");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ConsultaCliente()
	{
		frmConsultaClientes.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM clientes";
		// La información está en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una línea en el TextArea
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			listadoClientes.selectAll();
			listadoClientes.setText("");
			listadoClientes.append("id\tNombre\tApellidos\tDireccion\n");
			while(rs.next())
			{
				listadoClientes.append(rs.getInt("idCliente")
						+"\t"+rs.getString("nombreCliente")
						+"\t"+rs.getString("apellidosCliente")
						+"\t"+rs.getString("direccionCliente")
						+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoClientes.setText("Se ha producido un error en la consulta");
		}
		finally
		{

		}
		listadoClientes.setEditable(false);
		frmConsultaClientes.add(listadoClientes);
		btnPdfClientes.addActionListener(this);
		frmConsultaClientes.add(btnPdfClientes);

		frmConsultaClientes.setSize(250,160);
		frmConsultaClientes.setResizable(false);
		frmConsultaClientes.setLocationRelativeTo(null);
		frmConsultaClientes.addWindowListener(this);
		frmConsultaClientes.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{}
	@Override
	public void windowClosed(WindowEvent e)
	{}
	@Override
	public void windowClosing(WindowEvent e)
	{
		if(frmConsultaClientes.isActive())
		{
			frmConsultaClientes.setVisible(false);
		}
	}
	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(btnPdfClientes)){

			ArrayList<String> datos = new ArrayList<String>();

			datos = obtenerDatosParaExportar();
			exportarAPDF(datos);		
		}
	}
	public ArrayList<String> obtenerDatosParaExportar()
	{
		bd = new BaseDatos();
		connection = bd.conectar();
		ArrayList<String> datos = new ArrayList<>();

		try 
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM clientes;";
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			while (rs.next()) 
			{
				datos.add(rs.getString("IdCliente"));
				datos.add(rs.getString("nombreCliente"));
				datos.add(rs.getString("apellidosCliente"));
				datos.add(rs.getString("direccionCliente"));
			}

		}
		catch (SQLException e) 
		{
			e.getMessage();
		}
		bd.desconectar(connection);
		return datos;
	}


	public void exportarAPDF(ArrayList<String> datos) 
	{
		Document documento = new Document();
		Paragraph parrafo = new Paragraph("Listado Clientes", FontFactory.getFont("arial", 22, Font.BOLD));
		try
		{
			FileOutputStream ficheroPDF = new FileOutputStream("clientes.pdf");
			PdfWriter.getInstance(documento, ficheroPDF).setInitialLeading(20);
			documento.open();
			PdfPTable tabla = new PdfPTable(4);
			tabla.addCell("IdCliente");
			tabla.addCell("Nombre");
			tabla.addCell("Apellidos");
			tabla.addCell("Direccion");
			

			for (int i =0; i<datos.size(); i++) 
			{
				tabla.addCell(datos.get(i));
			}
			parrafo.setAlignment(Element.ALIGN_CENTER);
			documento.add(parrafo);
			documento.add(new Paragraph("\n"));
			documento.add(tabla);
			documento.close();

			FicheroLog.guardar(Login.nombreUsuario, "Generacion PDF clientes");

			File path = new File("clientes.pdf");
			Desktop.getDesktop().open(path);
		}
		catch (FileNotFoundException e) 
		{
			e.getMessage();
		}
		catch (DocumentException e) 
		{
			e.getMessage();
		}
		catch (IOException e) 
		{
			e.getMessage();
		}

	}
}
