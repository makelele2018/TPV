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

public class ModificarProducto implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Empleado");
	Label lblProducto = new Label("Elegir Producto");
	List listProducto = new List(8, false);
	Button btnEditar = new Button("Editar");
	Button btnCancelar = new Button("Cancelar");

	Frame ventanaEdicion = new Frame("Editando Producto");
	Label lblIdProducto = new Label("idProducto:");
	Label lblNombreProducto = new Label("Nombre Producto:");
	Label lblprecioProducto = new Label("Precio Producto:");
	TextField txtId = new TextField(20);
	TextField txtProducto = new TextField(20);
	TextField txtprecioProducto = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar2 = new Button("Cancelar");

	Dialog dlgMensajeModificacionProducto = new Dialog(ventanaEdicion, 
			"Confirmación", true);
	Label lblMensaje = new Label("Modificación de Producto Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public ModificarProducto()
	{
		ventana.setLayout(new FlowLayout());
		// Listeners
		ventana.addWindowListener(this);
		btnEditar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventanaEdicion.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnCancelar2.addActionListener(this);
		dlgMensajeModificacionProducto.addWindowListener(this);

		ventana.add(lblProducto);
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
			listProducto.removeAll();
			while(rs.next())
			{
				listProducto.add(rs.getInt("idProducto")
						+"-"+rs.getString("nombreProducto")
						+"-"+rs.getString("precioProducto"));
			}
		}
		catch (SQLException sqle)
		{

		}
		ventana.add(listProducto);
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
			String[] valores = listProducto.getSelectedItem().split("-");
			ventanaEdicion.add(lblIdProducto);
			txtId.setEnabled(false);
			txtId.setText(valores[0]);
			ventanaEdicion.add(txtId);
			ventanaEdicion.add(lblNombreProducto);
			txtProducto.setText(valores[1]);
			ventanaEdicion.add(txtProducto);
			ventanaEdicion.add(lblNombreProducto);
			txtprecioProducto.setText(valores[2]);
			ventanaEdicion.add(txtprecioProducto);

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
			sentencia = "UPDATE producto SET nombreProducto='"
					+txtProducto.getText()+"', precioProducto='"
					+txtprecioProducto.getText()+"' WHERE idProducto="
					+txtId.getText();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				FicheroLog.guardar(Login.nombreUsuario, sentencia);
				statement.executeUpdate(sentencia);
				lblMensaje.setText("Actualización de Producto Correcta");
			}
			catch (SQLException sqle)
			{
				lblMensaje.setText("Error");
			}
			finally
			{
				dlgMensajeModificacionProducto.setLayout(new FlowLayout());

				dlgMensajeModificacionProducto.setSize(150,100);
				dlgMensajeModificacionProducto.setResizable(false);
				dlgMensajeModificacionProducto.setLocationRelativeTo(null);
				dlgMensajeModificacionProducto.add(lblMensaje);
				dlgMensajeModificacionProducto.setVisible(true);
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