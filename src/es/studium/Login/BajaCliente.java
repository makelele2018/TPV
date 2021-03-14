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

public class BajaCliente implements ActionListener, WindowListener
{
	// Ventana de Borrado de Cliente
	Frame frmBajaCliente = new Frame("Baja de Cliente");
	Label lblMensajeBajaCliente = new Label("Seleccionar el cliente:");
	Choice choClientes = new Choice();
	Button btnBorrarCliente = new Button("Borrar");
	Dialog dlgSeguroCliente = new Dialog(frmBajaCliente, "¿Seguro?", true);
	Label lblSeguroCliente = new Label("¿Está seguro de borrar?");
	Button btnSiSeguroCliente = new Button("Sí");
	Button btnNoSeguroCliente = new Button("No");
	Dialog dlgConfirmacionBajaCliente = new Dialog(frmBajaCliente, "Baja Cliente", true);
	Label lblConfirmacionBajaCliente = new Label("Baja de cliente correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public BajaCliente()
	{
		frmBajaCliente.setLayout(new FlowLayout());
		frmBajaCliente.add(lblMensajeBajaCliente);
		// Rellenar el Choice
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM clientes";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choClientes.removeAll();
			while(rs.next())
			{
				choClientes.add(rs.getInt("idCliente")
						+"-"+rs.getString("nombreCliente")
						+"-"+rs.getString("apellidosCliente")
							+"-"+rs.getString("direccionCliente"));
			}
		}
		catch (SQLException sqle)
		{

		}
		frmBajaCliente.add(choClientes);
		btnBorrarCliente.addActionListener(this);
		frmBajaCliente.add(btnBorrarCliente);

		frmBajaCliente.setSize(250,140);
		frmBajaCliente.setResizable(false);
		frmBajaCliente.setLocationRelativeTo(null);
		frmBajaCliente.addWindowListener(this);
		frmBajaCliente.setVisible(true);
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
		if(frmBajaCliente.isActive())
		{
			frmBajaCliente.setVisible(false);
		}
		else if(dlgSeguroCliente.isActive())
		{
			dlgSeguroCliente.setVisible(false);
		}
		else if(dlgConfirmacionBajaCliente.isActive())
		{
			btnSiSeguroCliente.removeActionListener(this);
			btnNoSeguroCliente.removeActionListener(this);
			btnBorrarCliente.removeActionListener(this);
			dlgConfirmacionBajaCliente.setVisible(false);
			dlgSeguroCliente.setVisible(false);
			frmBajaCliente.setVisible(false);
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
		if(evento.getSource().equals(btnBorrarCliente))
		{
			dlgSeguroCliente.setLayout(new FlowLayout());
			dlgSeguroCliente.addWindowListener(this);
			dlgSeguroCliente.setSize(150,100);
			dlgSeguroCliente.setResizable(false);
			dlgSeguroCliente.setLocationRelativeTo(null);
			dlgSeguroCliente.add(lblSeguroCliente);
			btnSiSeguroCliente.addActionListener(this);
			dlgSeguroCliente.add(btnSiSeguroCliente);
			btnNoSeguroCliente.addActionListener(this);
			dlgSeguroCliente.add(btnNoSeguroCliente);
			dlgSeguroCliente.setVisible(true);
		}
		else if(evento.getSource().equals(btnNoSeguroCliente))
		{
			dlgSeguroCliente.setVisible(false);
		}
		else if(evento.getSource().equals(btnSiSeguroCliente))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un DELETE FROM clientes WHERE idCliente = X
			String[] elegido = choClientes.getSelectedItem().split("-");
			sentencia = "DELETE FROM clientes WHERE idCliente = "+elegido[0];
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				statement.executeUpdate(sentencia);
				lblConfirmacionBajaCliente.setText("Baja de Cliente Correcta");
			}
			catch (SQLException sqle)
			{
				lblConfirmacionBajaCliente.setText("Error en Baja");
			}
			finally
			{
				dlgConfirmacionBajaCliente.setLayout(new FlowLayout());
				dlgConfirmacionBajaCliente.addWindowListener(this);
				dlgConfirmacionBajaCliente.setSize(150,100);
				dlgConfirmacionBajaCliente.setResizable(false);
				dlgConfirmacionBajaCliente.setLocationRelativeTo(null);
				dlgConfirmacionBajaCliente.add(lblConfirmacionBajaCliente);
				dlgConfirmacionBajaCliente.setVisible(true);
			}
		}
	}	
}
