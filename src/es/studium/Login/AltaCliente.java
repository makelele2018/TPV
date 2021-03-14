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

public class AltaCliente implements ActionListener, WindowListener
{
	Frame frmAltaCliente = new Frame("Alta de Cliente");
	Label lblNombreCliente = new Label("Nombre:");
	TextField txtNombreCliente = new TextField(20);
	Label lblApellidoCliente = new Label("Apellidos:");
	TextField txtApellidoCliente = new TextField(20);
	Label lblDireccionCliente = new Label("Direccion:");
	TextField txtDireccionCliente = new TextField(20);
	Button btnAltaCliente = new Button("Alta");
	Button btnCancelarAltaCliente = new Button("Cancelar");

	Dialog dlgConfirmarAltaCliente = new Dialog(frmAltaCliente, "Alta Cliente", true);
	Label lblMensajeAltaCliente = new Label("Alta de Cliente Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaCliente()
	{
		frmAltaCliente.setLayout(new FlowLayout());
		frmAltaCliente.add(lblNombreCliente);
		txtNombreCliente.setText("");
		frmAltaCliente.add(txtNombreCliente);
		frmAltaCliente.add(lblApellidoCliente);
		txtApellidoCliente.setText("");
		frmAltaCliente.add(txtApellidoCliente);
		frmAltaCliente.add(lblDireccionCliente);
		txtDireccionCliente.setText("");
		frmAltaCliente.add(txtDireccionCliente);
		btnAltaCliente.addActionListener(this);
		frmAltaCliente.add(btnAltaCliente);
		btnCancelarAltaCliente.addActionListener(this);
		frmAltaCliente.add(btnCancelarAltaCliente);

		frmAltaCliente.setSize(240,250);
		frmAltaCliente.setResizable(false);
		frmAltaCliente.setLocationRelativeTo(null);
		frmAltaCliente.addWindowListener(this);
		txtNombreCliente.requestFocus();
		frmAltaCliente.setVisible(true);
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
		if(frmAltaCliente.isActive())
		{
			frmAltaCliente.setVisible(false);
		}
		else if(dlgConfirmarAltaCliente.isActive())
		{
			txtNombreCliente.setText("");
			txtApellidoCliente.setText("");
			txtDireccionCliente.setText("");
			txtNombreCliente.requestFocus();
			dlgConfirmarAltaCliente.setVisible(false);
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
		if(evento.getSource().equals(btnAltaCliente))
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
				if(((txtNombreCliente.getText().length())!=0)
						&&((txtApellidoCliente.getText().length())!=0))
				{
					sentencia = "INSERT INTO clientes VALUES (null, '"
							+ txtNombreCliente.getText()
							+ "', '" +txtApellidoCliente.getText() 
							+ "', '" +txtDireccionCliente.getText()+ "')";
					 
					statement.executeUpdate(sentencia);
					lblMensajeAltaCliente.setText("Alta de Cliente Correcta");
				}
				else
				{
					lblMensajeAltaCliente.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaCliente.setText("Error en ALTA");
			}
			finally
			{
				dlgConfirmarAltaCliente.setLayout(new FlowLayout());
				dlgConfirmarAltaCliente.addWindowListener(this);
				dlgConfirmarAltaCliente.setSize(150,100);
				dlgConfirmarAltaCliente.setResizable(false);
				dlgConfirmarAltaCliente.setLocationRelativeTo(null);
				dlgConfirmarAltaCliente.add(lblMensajeAltaCliente);
				dlgConfirmarAltaCliente.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}
}
