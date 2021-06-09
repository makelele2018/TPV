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

public class ConsultaProducto implements ActionListener, WindowListener
{
	// Ventana Consulta de Empleados
	Frame frmConsultaProducto = new Frame("Consulta Productos");
	TextArea listadoProducto = new TextArea(4, 50);
	Button btnPdfProducto = new Button("PDF");
	
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ConsultaProducto()
	{
		frmConsultaProducto.setLayout(new FlowLayout());
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Empleados
		sentencia = "SELECT * FROM producto;";
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
			listadoProducto.selectAll();
			listadoProducto.setText("");
			listadoProducto.append("id\tNombre\t\tPrecio\t\n");
			while(rs.next())
			{
				listadoProducto.append(rs.getInt("idProducto")
						+"\t"+rs.getString("nombreProducto")
						+"\t\t"+rs.getString("precioProducto")
						+"\n");
			}
		}
		catch (SQLException sqle)
		{
			listadoProducto.setText("Se ha producido un error en la consulta");
		}
		finally
		{

		}
		listadoProducto.setEditable(false);
		frmConsultaProducto.add(listadoProducto);
		frmConsultaProducto.add(btnPdfProducto);
		btnPdfProducto.addActionListener(this);

		frmConsultaProducto.setSize(400,150);
		frmConsultaProducto.setResizable(false);
		frmConsultaProducto.setLocationRelativeTo(null);
		frmConsultaProducto.addWindowListener(this);
		frmConsultaProducto.setVisible(true);
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
		if(frmConsultaProducto.isActive())
		{
			frmConsultaProducto.setVisible(false);
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
		if(e.getSource().equals(btnPdfProducto)){

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
			sentencia = "SELECT * FROM producto;";
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			while (rs.next()) 
			{
				datos.add(rs.getString("IdProducto"));
				datos.add(rs.getString("nombreProducto"));
				datos.add(rs.getString("precioProducto"));
				
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
		Paragraph parrafo = new Paragraph("Listado Productos", FontFactory.getFont("arial", 22, Font.BOLD));
		try
		{
			FileOutputStream ficheroPDF = new FileOutputStream("productos.pdf");
			PdfWriter.getInstance(documento, ficheroPDF).setInitialLeading(20);
			documento.open();
			PdfPTable tabla = new PdfPTable(3);
			tabla.addCell("IdProducto");
			tabla.addCell("Nombre");
			tabla.addCell("Precio");
			
			

			for (int i =0; i<datos.size(); i++) 
			{
				tabla.addCell(datos.get(i));
			}
			parrafo.setAlignment(Element.ALIGN_CENTER);
			documento.add(parrafo);
			documento.add(new Paragraph("\n"));
			documento.add(tabla);
			documento.close();

			FicheroLog.guardar(Login.nombreUsuario, "Generacion PDF Productos");

			File path = new File("productos.pdf");
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