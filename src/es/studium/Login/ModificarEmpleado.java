package es.studium.Login;

import java.awt.Button;
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

public class ModificarEmpleado implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Empleado");
	Label lblEmpleado = new Label("Elegir Empleado");
	List listEmpleados = new List(8, false);
	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Frame ventanaEdicion = new Frame("Editando Empleado");
	Label lblIdEmpleado = new Label("idEmpleado:");
	Label lblNombreEmpleado = new Label("Nombre:");
	Label lblDireccionEmpleado = new Label("Direccion:");
	Label lblTelefonoEmpleado = new Label("Telefono:");
	TextField txtId = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtDireccion = new TextField(20);
	TextField txtTelefono = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar2 = new Button("Cancelar");

	Dialog dlgMensajeModificacionEmpleado = new Dialog(ventanaEdicion, 
			"Confirmación", true);
	Label lblMensaje = new Label("Modificación de Empleado Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificarEmpleado()
	{
		ventana.setLayout(new FlowLayout());
		// Listeners
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventanaEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar2.addActionListener(this);
		dlgMensajeModificacionEmpleado.addWindowListener(this);

		ventana.add(lblEmpleado);
		// Conectar
		bd = new BaseDatos();
		connection = bd.conectar();
		// Hacer un SELECT * FROM Empleados
		sentencia = "SELECT * FROM Empleados";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			listEmpleados.removeAll();
			while(rs.next())
			{
				listEmpleados.add(rs.getInt("idEmpleado")
						+"-"+rs.getString("nombreEmpleado")
						+"-"+rs.getString("direccionEmpleado")
						+"-"+rs.getString("telefonoEmpleado"));
			}
		}
		catch (SQLException sqle)
		{

		}
		ventana.add(listEmpleados);
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
			ventanaEdicion.setLayout(new FlowLayout());

			// Capturar los datos del elemento elegido del List
			String[] valores = listEmpleados.getSelectedItem().split("-");
			ventanaEdicion.add(lblIdEmpleado);
			txtId.setEnabled(false);
			txtId.setText(valores[0]);
			ventanaEdicion.add(txtId);
			ventanaEdicion.add(lblNombreEmpleado);
			txtNombre.setText(valores[1]);
			ventanaEdicion.add(txtNombre);
			ventanaEdicion.add(lblDireccionEmpleado);
			txtDireccion.setText(valores[2]);
			ventanaEdicion.add(txtDireccion);
			ventanaEdicion.add(lblTelefonoEmpleado);
			txtTelefono.setText(valores[3]);
			ventanaEdicion.add(txtTelefono);
			ventanaEdicion.add(btnAceptar);
			ventanaEdicion.add(btnCancelar2);

			ventanaEdicion.setSize(200,300);
			ventanaEdicion.setResizable(false);
			ventanaEdicion.setLocationRelativeTo(null);

			ventanaEdicion.setVisible(true);
		}
		else if(evento.getSource().equals(btnAceptar))
		{
			// Conectar
			bd = new BaseDatos();
			connection = bd.conectar();
			// Hacer un SELECT * FROM Empleados
			sentencia = "UPDATE empleados SET nombreEmpleado='"
					+txtNombre.getText()+"', direccionEmpleado='"
					+txtDireccion.getText()+"', telefonoEmpleado='"
					+txtTelefono.getText()+"' WHERE idEmpleado="
					+txtId.getText();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				statement.executeUpdate(sentencia);
				lblMensaje.setText("Actualización de Empleado Correcta");
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error");
			}
			finally
			{
				dlgMensajeModificacionEmpleado.setLayout(new FlowLayout());

				dlgMensajeModificacionEmpleado.setSize(150,100);
				dlgMensajeModificacionEmpleado.setResizable(false);
				dlgMensajeModificacionEmpleado.setLocationRelativeTo(null);
				dlgMensajeModificacionEmpleado.add(lblMensaje);
				dlgMensajeModificacionEmpleado.setVisible(true);
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
}
