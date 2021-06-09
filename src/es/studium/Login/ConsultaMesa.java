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

public class ConsultaMesa implements ActionListener, WindowListener
{
	// Ventana Consulta de Clientes
	Frame frmConsultaMesa = new Frame("Consulta Mesa");
	TextArea listadoMesa = new TextArea(4, 30);
	Button btnPdfMesa = new Button("PDF");
	
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ConsultaMesa()
	{
		frmConsultaMesa.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM servido";
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
			listadoMesa.selectAll();
			listadoMesa.setText("");
			listadoMesa.append("id\tNumeroMesa\tEmpleado\tCliente\n");
			while(rs.next())
			{
				listadoMesa.append(rs.getInt("idServido")
						+"\t"+rs.getString("numeroMesa")
						+"\t"+rs.getString("idEmpleadoFK")
						+"\t"+rs.getString("idClienteFK")
						+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoMesa.setText("Se ha producido un error en la consulta");
		}
		finally
		{

		}
		listadoMesa.setEditable(false);
		frmConsultaMesa.add(listadoMesa);
		frmConsultaMesa.add(btnPdfMesa);
		btnPdfMesa.addActionListener(this);

		frmConsultaMesa.setSize(250,160);
		frmConsultaMesa.setResizable(false);
		frmConsultaMesa.setLocationRelativeTo(null);
		frmConsultaMesa.addWindowListener(this);
		frmConsultaMesa.setVisible(true);
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
		if(frmConsultaMesa.isActive())
		{
			frmConsultaMesa.setVisible(false);
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
		if(e.getSource().equals(btnPdfMesa)){

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
			sentencia = "SELECT * FROM servido;";
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			while (rs.next()) 
			{
				datos.add(rs.getString("IdServido"));
				datos.add(rs.getString("numeroMesa"));
				datos.add(rs.getString("idEmpleadoFK"));
				datos.add(rs.getString("idClienteFK"));
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
		Paragraph parrafo = new Paragraph("Listado Servicios", FontFactory.getFont("arial", 22, Font.BOLD));
		try
		{
			FileOutputStream ficheroPDF = new FileOutputStream("servido.pdf");
			PdfWriter.getInstance(documento, ficheroPDF).setInitialLeading(20);
			documento.open();
			PdfPTable tabla = new PdfPTable(4);
			tabla.addCell("IdServido");
			tabla.addCell("Mesa");
			tabla.addCell("Empleado");
			tabla.addCell("Cliente");
			

			for (int i =0; i<datos.size(); i++) 
			{
				tabla.addCell(datos.get(i));
			}
			parrafo.setAlignment(Element.ALIGN_CENTER);
			documento.add(parrafo);
			documento.add(new Paragraph("\n"));
			documento.add(tabla);
			documento.close();

			FicheroLog.guardar(Login.nombreUsuario, "Generacion PDF Empleados");

			File path = new File("servido.pdf");
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