package es.studium.Login;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		// TODO Auto-generated method stub

	}
}
