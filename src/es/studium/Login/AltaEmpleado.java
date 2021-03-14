package es.studium.Login;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaEmpleado implements ActionListener, WindowListener
{
	Frame frmAltaEmpleado = new Frame("Alta de Empleado");
	Label lblNombreEmpleado = new Label("Nombre:");
	TextField txtNombreEmpleado = new TextField(20);
	Label lblApellidosEmpleado = new Label("Apellidos:");
	TextField txtDireccionEmpleado = new TextField(20);
	Label lblDireccionEmpleado = new Label("Direccion:");
	Label lblTelefonoEmpleado = new Label("Telefono:");
	TextField txtTelefonoEmpleado = new TextField(20);
	Button btnAltaEmpleado = new Button("Alta");
	Button btnCancelarAltaEmpleado = new Button("Cancelar");

	Dialog dlgConfirmarAltaEmpleado = new Dialog(frmAltaEmpleado, "Alta Empleado", true);
	Label lblMensajeAltaEmpleado = new Label("Alta de Empleado Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaEmpleado()
	{
		frmAltaEmpleado.setLayout(new FlowLayout());
		frmAltaEmpleado.add(lblNombreEmpleado);
		txtNombreEmpleado.setText("");
		frmAltaEmpleado.add(txtNombreEmpleado);
		frmAltaEmpleado.add(lblApellidosEmpleado);
		txtNombreEmpleado.setText("");
		frmAltaEmpleado.add(txtDireccionEmpleado);
		frmAltaEmpleado.add(lblDireccionEmpleado);
		txtDireccionEmpleado.setText("");
		frmAltaEmpleado.add(txtDireccionEmpleado);
		frmAltaEmpleado.add(lblTelefonoEmpleado);
		txtDireccionEmpleado.setText("");
		frmAltaEmpleado.add(txtTelefonoEmpleado);
		btnAltaEmpleado.addActionListener(this);
		frmAltaEmpleado.add(btnAltaEmpleado);
		btnCancelarAltaEmpleado.addActionListener(this);
		frmAltaEmpleado.add(btnCancelarAltaEmpleado);

		frmAltaEmpleado.setSize(250,290);
		frmAltaEmpleado.setResizable(false);
		frmAltaEmpleado.setLocationRelativeTo(null);
		frmAltaEmpleado.addWindowListener(this);
		txtNombreEmpleado.requestFocus();
		frmAltaEmpleado.setVisible(true);
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
		// TODO Auto-generated method stub
		if(frmAltaEmpleado.isActive())
		{
			frmAltaEmpleado.setVisible(false);
		}
		else if(dlgConfirmarAltaEmpleado.isActive())
		{
			txtNombreEmpleado.setText("");
			txtDireccionEmpleado.setText("");
			txtTelefonoEmpleado.setText("");
			txtNombreEmpleado.requestFocus();
			dlgConfirmarAltaEmpleado.setVisible(false);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e){}

	@Override
	public void windowDeiconified(WindowEvent e){}

	@Override
	public void windowIconified(WindowEvent e){}

	@Override
	public void windowOpened(WindowEvent e){}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAltaEmpleado))
		{
			bd = new BaseDatos();
			connection = bd.conectar();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				if(((txtNombreEmpleado.getText().length())!=0)
						&&((txtDireccionEmpleado.getText().length())!=0))
				{
					sentencia = "INSERT INTO empleados VALUES (null, '"
							+ txtNombreEmpleado.getText()
							 
							+ "', '" +txtDireccionEmpleado.getText()
							+ "', '" +txtTelefonoEmpleado.getText()+ "')";
					statement.executeUpdate(sentencia);
					lblMensajeAltaEmpleado.setText("Alta de Empleado Correcta");
				}
				else
				{
					lblMensajeAltaEmpleado.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaEmpleado.setText("Error en ALTA");
			}
			finally
			{
				dlgConfirmarAltaEmpleado.setLayout(new FlowLayout());
				dlgConfirmarAltaEmpleado.addWindowListener(this);
				dlgConfirmarAltaEmpleado.setSize(150,100);
				dlgConfirmarAltaEmpleado.setResizable(false);
				dlgConfirmarAltaEmpleado.setLocationRelativeTo(null);
				dlgConfirmarAltaEmpleado.add(lblMensajeAltaEmpleado);
				dlgConfirmarAltaEmpleado.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}
}
