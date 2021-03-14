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
		// TODO Auto-generated method stub

	}
}
