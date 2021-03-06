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

public class ConsultaEmpleado implements ActionListener, WindowListener
{
	// Ventana Consulta de Empleados
	Frame frmConsultaEmpleados = new Frame("Consulta Empleados");
	TextArea listadoEmpleados = new TextArea(4, 50);
	Button btnPdfEmpleados = new Button("PDF");
	
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ConsultaEmpleado()
	{
		frmConsultaEmpleados.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Empleados
		sentencia = "SELECT * FROM empleados;";
		// La informaci?n est? en ResultSet
		// Recorrer el RS y por cada registro,
		// meter una l?nea en el TextArea
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			listadoEmpleados.selectAll();
			listadoEmpleados.setText("");
			listadoEmpleados.append("id\tNombre\t\tDireccion\ttelefonoEmpleado\\n");
			while(rs.next())
			{
				listadoEmpleados.append(rs.getInt("idEmpleado")
						+"\t"+rs.getString("nombreEmpleado")
						+"\t\t"+rs.getString("direccionEmpleado")
						+"\t\t"+rs.getString("telefonoEmpleado")
						+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoEmpleados.setText("Se ha producido un error en la consulta");
		}
		finally
		{

		}
		listadoEmpleados.setEditable(false);
		frmConsultaEmpleados.add(listadoEmpleados);
		frmConsultaEmpleados.add(btnPdfEmpleados);
		btnPdfEmpleados.addActionListener(this);
		frmConsultaEmpleados.setSize(400,150);
		frmConsultaEmpleados.setResizable(false);
		frmConsultaEmpleados.setLocationRelativeTo(null);
		frmConsultaEmpleados.addWindowListener(this);
		frmConsultaEmpleados.setVisible(true);
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
		if(frmConsultaEmpleados.isActive())
		{
			frmConsultaEmpleados.setVisible(false);
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
		if(e.getSource().equals(btnPdfEmpleados)){

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
			sentencia = "SELECT * FROM empleados;";
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			while (rs.next()) 
			{
				datos.add(rs.getString("IdEmpleado"));
				datos.add(rs.getString("nombreEmpleado"));
				datos.add(rs.getString("direccionEmpleado"));
				datos.add(rs.getString("telefonoEmpleado"));
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
		Paragraph parrafo = new Paragraph("Listado Empleados", FontFactory.getFont("arial", 22, Font.BOLD));
		try
		{
			FileOutputStream ficheroPDF = new FileOutputStream("empleados.pdf");
			PdfWriter.getInstance(documento, ficheroPDF).setInitialLeading(20);
			documento.open();
			PdfPTable tabla = new PdfPTable(4);
			tabla.addCell("IdEmpleado");
			tabla.addCell("Nombre");
			tabla.addCell("Direccion");
			tabla.addCell("Telefono");
			

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

			File path = new File("empleados.pdf");
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
