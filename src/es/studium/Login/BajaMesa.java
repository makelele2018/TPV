package es.studium.Login;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BajaMesa implements ActionListener, WindowListener
{
	// Ventana de Borrado de Empleado
	Frame frmBajaMesa = new Frame("Baja de Producto");
	Label lblMensajeBajaMesa = new Label("Seleccionar el Mesa:");
	Choice choMesa = new Choice();
	Button btnBorrarMesa = new Button("Borrar");
	Dialog dlgSeguroMesa = new Dialog(frmBajaMesa, "¿Seguro?", true);
	Label lblSeguroMesa = new Label("¿Está seguro de borrar?");
	Button btnSiSeguroMesa = new Button("Sí");
	Button btnNoSeguroMesa = new Button("No");
	Dialog dlgConfirmacionBajaMesa = new Dialog(frmBajaMesa, "Baja Mesa", true);
	Label lblConfirmacionBajaMesa = new Label("Baja de Producto correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public BajaMesa()
	{
		frmBajaMesa.setLayout(new FlowLayout());
		frmBajaMesa.add(lblMensajeBajaMesa);
		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Empleados
		sentencia = "SELECT * FROM servido";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			choMesa.removeAll();
			while(rs.next())
			{
				choMesa.add(rs.getInt("idServido")
						+"-"+rs.getString("numeroMesa")
						+"-"+rs.getString("idEmpleadoFK")
						+"-"+rs.getString("idClienteFK"));
			}
		}
		catch (SQLException sqle)
		{

		}
		frmBajaMesa.add(choMesa);
		btnBorrarMesa.addActionListener(this);
		frmBajaMesa.add(btnBorrarMesa);

		frmBajaMesa.setSize(250,140);
		frmBajaMesa.setResizable(false);
		frmBajaMesa.setLocationRelativeTo(null);
		frmBajaMesa.addWindowListener(this);
		frmBajaMesa.setVisible(true);
	}
	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		// TODO Auto-generated method stub
		if(frmBajaMesa.isActive())
		{
			frmBajaMesa.setVisible(false);
		}
		else if(dlgSeguroMesa.isActive())
		{
			dlgSeguroMesa.setVisible(false);
		}
		else if(dlgConfirmacionBajaMesa.isActive())
		{
			btnSiSeguroMesa.removeActionListener(this);
			btnNoSeguroMesa.removeActionListener(this);
			btnBorrarMesa.removeActionListener(this);
			dlgConfirmacionBajaMesa.setVisible(false);
			dlgSeguroMesa.setVisible(false);
			frmBajaMesa.setVisible(false);
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
	public void actionPerformed(ActionEvent evento)
	{
		// TODO Auto-generated method stub
		if(evento.getSource().equals(btnBorrarMesa))
		{
			dlgSeguroMesa.setLayout(new FlowLayout());
			dlgSeguroMesa.addWindowListener(this);
			dlgSeguroMesa.setSize(150,100);
			dlgSeguroMesa.setResizable(false);
			dlgSeguroMesa.setLocationRelativeTo(null);
			dlgSeguroMesa.add(lblSeguroMesa);
			btnSiSeguroMesa.addActionListener(this);
			dlgSeguroMesa.add(btnSiSeguroMesa);
			btnNoSeguroMesa.addActionListener(this);
			dlgSeguroMesa.add(btnNoSeguroMesa);
			dlgSeguroMesa.setVisible(true);
		}
		else if(evento.getSource().equals(btnNoSeguroMesa))
		{
			dlgSeguroMesa.setVisible(false);
		}
		else if(evento.getSource().equals(btnSiSeguroMesa))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un DELETE FROM Empleados WHERE idEmpleado = X
			String[] elegido = choMesa.getSelectedItem().split("-");
			sentencia = "DELETE FROM servido WHERE idservido = "+elegido[0];
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(sentencia);
				lblConfirmacionBajaMesa.setText("Baja de Producto Correcta");
			}
			catch (SQLException sqle)
			{
				lblConfirmacionBajaMesa.setText("Error en Baja");
			}
			finally
			{
				dlgConfirmacionBajaMesa.setLayout(new FlowLayout());
				dlgConfirmacionBajaMesa.addWindowListener(this);
				dlgConfirmacionBajaMesa.setSize(150,100);
				dlgConfirmacionBajaMesa.setResizable(false);
				dlgConfirmacionBajaMesa.setLocationRelativeTo(null);
				dlgConfirmacionBajaMesa.add(lblConfirmacionBajaMesa);
				dlgConfirmacionBajaMesa.setVisible(true);
			}
		}
	}	
}