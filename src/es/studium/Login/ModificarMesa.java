package es.studium.Login;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificarMesa implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Mesa");
	Label lblMesa = new Label("Elegir Mesa");
	List listMesa = new List(8, false);
	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Frame ventanaEdicion = new Frame("Editando Mesa");
	Label lblIdMesa = new Label("idMesa:");
	Label lblNumeroMesa = new Label("Empleado Mesa:");
	Label lblEmpleadoMesa = new Label("Empleado:");
	Label lblClienteMesa = new Label("Cliente:");
	TextField txtId = new TextField(20);
	TextField txtMesa = new TextField(20);
	Choice listaEmpleados = new Choice();
	Choice listaClientes = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar2 = new Button("Cancelar");

	Dialog dlgMensajeModificacionMesa = new Dialog(ventanaEdicion, 
			"Confirmación", true);
	Label lblMensaje = new Label("Modificación de Mesa Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificarMesa()
	{
		ventana.setLayout(new FlowLayout());
		// Listeners
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventanaEdicion.addWindowListener(this);
		listadoCliente(listaClientes);

		listadoCamarero(listaEmpleados);
		btnAceptar.addActionListener(this);
		btnCancelar2.addActionListener(this);
		dlgMensajeModificacionMesa.addWindowListener(this);

		ventana.add(lblMesa);
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Empleados
		sentencia = "select idServido, numeroMesa, nombreEmpleado, nombreCliente, apellidosCliente\r\n"
				+ "from servido\r\n"
				+ "join clientes on clientes.idCliente = servido.idClienteFK\r\n"
				+ "join empleados on empleados.idEmpleado = servido.idEmpleadoFK;";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			FicheroLog.guardar(Login.nombreUsuario, sentencia);
			rs = statement.executeQuery(sentencia);
			listMesa.removeAll();
			while(rs.next())
			{
				listMesa.add(rs.getInt("idServido")
						+"-"+rs.getString("numeroMesa")
						+"-"+rs.getString("nombreEmpleado")
						+"-"+rs.getString("nombreCliente")
						+"-"+rs.getString("apellidosCliente"));
			}
		}
		catch (SQLException sqle)
		{

		}
		ventana.add(listMesa);
		ventana.add(btnEditar);
		ventana.add(btnCancelar);
		ventana.setSize(250,250);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		// TODO Auto-generated method stub
		if(evento.getSource().equals(btnCancelar))
		{
			ventana.setVisible(false);
		}
		else if(evento.getSource().equals(btnCancelar2))
		{
			ventana.setVisible(false);
			ventanaEdicion.setVisible(false);
		}
		else if(evento.getSource().equals(btnEditar))
		{
			String[] valores = obtenerDatosMesa(listMesa.getSelectedItem().split("-")[0]).split("-");
			ventanaEdicion(valores);

		}
		else if(evento.getSource().equals(btnAceptar))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un SELECT * FROM 
			sentencia = "UPDATE servido SET numeroMesa='"
					+txtMesa.getText()+"', idEmpleadoFK='"
					+listaEmpleados.getSelectedItem().split("-")[0]+"', idClienteFK='"
					+listaClientes.getSelectedItem().split("-")[0]
					+"' WHERE idServido=" +txtId.getText();
			System.out.println(sentencia);
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				FicheroLog.guardar(Login.nombreUsuario, sentencia);
				statement.executeUpdate(sentencia);
				lblMensaje.setText("Actualización de Mesa Correcta");
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error");
			}
			finally
			{
				dlgMensajeModificacionMesa.setLayout(new FlowLayout());

				dlgMensajeModificacionMesa.setSize(150,100);
				dlgMensajeModificacionMesa.setResizable(false);
				dlgMensajeModificacionMesa.setLocationRelativeTo(null);
				dlgMensajeModificacionMesa.add(lblMensaje);
				dlgMensajeModificacionMesa.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{}

	@Override
	public void windowClosed(WindowEvent arg0)
	{}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		ventana.setVisible(false);
		ventanaEdicion.setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{}

	@Override
	public void windowIconified(WindowEvent arg0)
	{}

	@Override
	public void windowOpened(WindowEvent arg0)
	{}

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

	public String obtenerDatosMesa(String id) 
	{
		bd= new BaseDatos();
		connection = bd.conectar();
		String valores = "";
		try 
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM servido WHERE idServido = "+id+";";
			rs = statement.executeQuery(sentencia);
			while (rs.next()) 
			{
				valores = rs.getInt("idServido")+"-"+rs.getInt("numeroMesa")+"-"+rs.getInt("idEmpleadoFk")+"-"+rs.getInt("idClienteFK");
			}
		}
		catch (SQLException e) 
		{
			valores = "";	
		}
		finally 
		{
			bd.desconectar(connection);
		}
		return valores;
	}
	
	public String obtenerCliente(String id) {
		bd = new BaseDatos();
		connection = bd.conectar();
		String datos= "";
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM clientes WHERE idCliente = "+id+" ";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos =rs.getInt("idCliente")
						+"-"+rs.getString("nombreCliente")
						+"-"+rs.getString("apellidosCliente")
							+"-"+rs.getString("direccionCliente");
			}
		}
		catch (SQLException sqle)
		{

		}
		
		return datos;
		
		
	}
	
	public String obtenerEmpleado(String id) {
		bd = new BaseDatos();
		connection = bd.conectar();
		String datos= "";
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM Empleados WHERE idEmpleado = "+id+" ";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos =rs.getInt("idEmpleado")
						+"-"+rs.getString("nombreEmpleado")
						+"-"+rs.getString("direccionEmpleado")
							+"-"+rs.getString("telefonoEmpleado");
			}
		}
		catch (SQLException sqle)
		{

		}
		
		return datos;
		
		
	}

	public void ventanaEdicion(String[] valores)
	{
		ventanaEdicion.setSize(250, 270);
		ventanaEdicion.setLayout(new FlowLayout());
		ventanaEdicion.add(lblIdMesa);
		txtId.setText(valores[0]);
		ventanaEdicion.add(txtId);
		ventanaEdicion.add(lblMesa);
		txtMesa.setText(valores[1]);
		ventanaEdicion.add(txtMesa);
		ventanaEdicion.add(lblEmpleadoMesa);
		listadoCliente(listaClientes);
		listadoCamarero(listaEmpleados);
		String Empleado = obtenerEmpleado(valores[2]);
		listaEmpleados.select(Empleado);
		String cliente = obtenerCliente(valores[3]);
		listaClientes.select(cliente);
		ventanaEdicion.add(listaEmpleados);
		ventanaEdicion.add(lblClienteMesa);
		ventanaEdicion.add(listaClientes);
		ventanaEdicion.add(btnCancelar);
		ventanaEdicion.add(btnAceptar);
		ventanaEdicion.setVisible(true);
		ventanaEdicion.setLocationRelativeTo(null);

	}

}
