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

public class BajaProducto implements ActionListener, WindowListener
{
	// Ventana de Borrado de Empleado
	Frame frmBajaProducto = new Frame("Baja de Producto");
	Label lblMensajeBajaProducto = new Label("Seleccionar el Producto:");
	Choice choProducto = new Choice();
	Button btnBorrarProducto = new Button("Borrar");
	Dialog dlgSeguroProducto = new Dialog(frmBajaProducto, "¿Seguro?", true);
	Label lblSeguroProducto = new Label("¿Está seguro de borrar?");
	Button btnSiSeguroProducto = new Button("Sí");
	Button btnNoSeguroProducto = new Button("No");
	Dialog dlgConfirmacionBajaProducto = new Dialog(frmBajaProducto, "Baja Empleado", true);
	Label lblConfirmacionBajaProducto = new Label("Baja de Producto correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public BajaProducto()
	{
		frmBajaProducto.setLayout(new FlowLayout());
		frmBajaProducto.add(lblMensajeBajaProducto);
		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Empleados
		sentencia = "SELECT * FROM producto";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			choProducto.removeAll();
			while(rs.next())
			{
				choProducto.add(rs.getInt("idProducto")
						+"-"+rs.getString("nombreProducto")
						+"-"+rs.getString("precioProducto"));
			}
		}
		catch (SQLException sqle)
		{

		}
		frmBajaProducto.add(choProducto);
		btnBorrarProducto.addActionListener(this);
		frmBajaProducto.add(btnBorrarProducto);

		frmBajaProducto.setSize(250,140);
		frmBajaProducto.setResizable(false);
		frmBajaProducto.setLocationRelativeTo(null);
		frmBajaProducto.addWindowListener(this);
		frmBajaProducto.setVisible(true);
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
		if(frmBajaProducto.isActive())
		{
			frmBajaProducto.setVisible(false);
		}
		else if(dlgSeguroProducto.isActive())
		{
			dlgSeguroProducto.setVisible(false);
		}
		else if(dlgConfirmacionBajaProducto.isActive())
		{
			btnSiSeguroProducto.removeActionListener(this);
			btnNoSeguroProducto.removeActionListener(this);
			btnBorrarProducto.removeActionListener(this);
			dlgConfirmacionBajaProducto.setVisible(false);
			dlgSeguroProducto.setVisible(false);
			frmBajaProducto.setVisible(false);
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
		if(evento.getSource().equals(btnBorrarProducto))
		{
			dlgSeguroProducto.setLayout(new FlowLayout());
			dlgSeguroProducto.addWindowListener(this);
			dlgSeguroProducto.setSize(150,100);
			dlgSeguroProducto.setResizable(false);
			dlgSeguroProducto.setLocationRelativeTo(null);
			dlgSeguroProducto.add(lblSeguroProducto);
			btnSiSeguroProducto.addActionListener(this);
			dlgSeguroProducto.add(btnSiSeguroProducto);
			btnNoSeguroProducto.addActionListener(this);
			dlgSeguroProducto.add(btnNoSeguroProducto);
			dlgSeguroProducto.setVisible(true);
		}
		else if(evento.getSource().equals(btnNoSeguroProducto))
		{
			dlgSeguroProducto.setVisible(false);
		}
		else if(evento.getSource().equals(btnSiSeguroProducto))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un DELETE FROM Empleados WHERE idEmpleado = X
			String[] elegido = choProducto.getSelectedItem().split("-");
			sentencia = "DELETE FROM producto WHERE idproducto = "+elegido[0];
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(sentencia);
				lblConfirmacionBajaProducto.setText("Baja de Producto Correcta");
			}
			catch (SQLException sqle)
			{
				lblConfirmacionBajaProducto.setText("Error en Baja");
			}
			finally
			{
				dlgConfirmacionBajaProducto.setLayout(new FlowLayout());
				dlgConfirmacionBajaProducto.addWindowListener(this);
				dlgConfirmacionBajaProducto.setSize(150,100);
				dlgConfirmacionBajaProducto.setResizable(false);
				dlgConfirmacionBajaProducto.setLocationRelativeTo(null);
				dlgConfirmacionBajaProducto.add(lblConfirmacionBajaProducto);
				dlgConfirmacionBajaProducto.setVisible(true);
			}
		}
	}	
}