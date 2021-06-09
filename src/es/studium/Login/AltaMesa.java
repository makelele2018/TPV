package es.studium.Login;


import java.awt.Button;
import java.awt.Choice;
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

public class AltaMesa implements ActionListener, WindowListener
{
	Frame frmAltaMesa = new Frame("Alta de Mesa");
	Label lblNumeroMesa = new Label("Numero Mesa:");
	TextField txtNumeroMesa = new TextField(20);
	Label lblCamarero = new Label("Camarero:");
	Choice cholistaCamarero = new Choice();
	Label lblCliente = new Label("Cliente:");
	Choice cholistaCliente = new Choice();
	Button btnAltaMesa = new Button("Alta");
	Button btnCancelarMesa = new Button("Cancelar");

	Dialog dlgConfirmarAltaMesa = new Dialog(frmAltaMesa, "Operacion Correcta", true);
	Label lblMensajeAltaMesa = new Label("Alta Correcta");
	
	Dialog dlgErrorSeleccionar = new Dialog(frmAltaMesa, "Error", true);
	Label lblMensajeErrorSeleccionar = new Label("Faltan Datos");
	
	Dialog dlgErrorAltaMesa = new Dialog(frmAltaMesa, "Error", true);
	Label lblMensajeErrorAltaMesa = new Label("Error al insertar");
	
	

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaMesa()
	{
		frmAltaMesa.setLayout(new FlowLayout());
		frmAltaMesa.add(lblNumeroMesa);
		txtNumeroMesa.setText("");
		frmAltaMesa.add(txtNumeroMesa);
		frmAltaMesa.add(lblCamarero);
		listadoCamarero(cholistaCamarero);
		frmAltaMesa.add(cholistaCamarero);
		listadoCliente(cholistaCliente);
		frmAltaMesa.add(lblCliente);
		frmAltaMesa.add(cholistaCliente);
		txtNumeroMesa.setText("");
		btnAltaMesa.addActionListener(this);
		frmAltaMesa.add(btnAltaMesa);
		btnCancelarMesa.addActionListener(this);
		frmAltaMesa.add(btnCancelarMesa);
		frmAltaMesa.setSize(240,250);
		frmAltaMesa.setResizable(false);
		frmAltaMesa.setLocationRelativeTo(null);
		frmAltaMesa.addWindowListener(this);
		dlgConfirmarAltaMesa.addWindowListener(this);
		dlgErrorAltaMesa.addWindowListener(this);
		dlgErrorSeleccionar.addWindowListener(this);

		txtNumeroMesa.requestFocus();
		frmAltaMesa.setVisible(true);
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
		if(frmAltaMesa.isActive())
		{
			frmAltaMesa.setVisible(false);
			btnAltaMesa.removeActionListener(this);
			btnCancelarMesa.removeActionListener(this);
			frmAltaMesa.removeWindowListener(this);
		}
		else if(dlgConfirmarAltaMesa.isActive())
		{
			
			dlgConfirmarAltaMesa.setVisible(false);
			dlgConfirmarAltaMesa.removeWindowListener(this);;
		}
		else if(dlgErrorSeleccionar.isActive())
		{
			
			dlgErrorSeleccionar.setVisible(false);
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
		if(evento.getSource().equals(btnAltaMesa))
		{
			if(txtNumeroMesa.getText().length()!=0) 
			{
				insertarDatosMesa(txtNumeroMesa, cholistaCamarero, cholistaCliente);
			}else 
			{
				creacionDialogoNotificacion(dlgErrorSeleccionar, lblMensajeErrorSeleccionar);
				dlgErrorSeleccionar.setVisible(true);
			}
			
			
		}
	}

	public void listadoCamarero(Choice listaCamarero) {
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Empleados
		sentencia = "SELECT * FROM empleados";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			listaCamarero.removeAll();
			while(rs.next())
			{
				listaCamarero.add(rs.getInt("idEmpleado")
						+"-"+rs.getString("nombreEmpleado")
						+"-"+rs.getString("direccionEmpleado")
						+"-"+rs.getString("telefonoEmpleado"));
			}
		}
		catch (SQLException sqle)
		{

		}
	}
	public void listadoCliente(Choice listaCliente) {
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
			listaCliente.removeAll();
			while(rs.next())
			{
				listaCliente.add(rs.getInt("idCliente")
						+"-"+rs.getString("nombreCliente")
						+"-"+rs.getString("apellidosCliente")
						+"-"+rs.getString("direccionCliente"));
			}
		}
		catch (SQLException sqle)
		{

		}


	}

	public void insertarDatosMesa(TextField mesa, Choice listaCamarero, Choice listaCliente) 
	{
		bd = new BaseDatos();
		connection = bd.conectar();

		try
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sentencia = "INSERT INTO servido VALUES (null, "+mesa.getText()+","+listaCamarero.getSelectedItem().split("-")[0]+" ,"+listaCliente.getSelectedItem().split("-")[0]+" );";
			statement.executeUpdate(sentencia);
			creacionDialogoNotificacion(dlgConfirmarAltaMesa, lblMensajeAltaMesa);
			dlgConfirmarAltaMesa.setVisible(true);
		}
		catch (SQLException e1)
		{
			creacionDialogoNotificacion(dlgErrorAltaMesa, lblMensajeErrorAltaMesa);
			dlgErrorAltaMesa.setVisible(true);
		}
		finally 
		{
			bd.desconectar(connection);
		}

	}
	
	// ============================================= DIALOGO NOTIFICACION ==========================================================
		public void creacionDialogoNotificacion(Dialog dialogo, Label lbl) 
		{
			dialogo.setSize(230, 100);
			dialogo.setLayout(new FlowLayout());
			dialogo.add(lbl);
			dialogo.setLocationRelativeTo(null);
		}

}