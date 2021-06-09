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

public class AltaProducto implements ActionListener, WindowListener
{
	Frame frmAltaProducto = new Frame("Alta de Producto");
	Label lblNombreProducto = new Label("Nombre:");
	TextField txtNombreProducto = new TextField(20);
	Label lblPrecioProducto = new Label("Precio:");
	TextField txtPrecioProducto = new TextField(20);
	Button btnAltaProducto = new Button("Alta");
	Button btnCancelarProducto = new Button("Cancelar");

	Dialog dlgConfirmarAltaProducto = new Dialog(frmAltaProducto, "Alta Producto", true);
	Label lblMensajeAltaProducto = new Label("Alta de Producto Correcta");

	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaProducto()
	{
		frmAltaProducto.setLayout(new FlowLayout());
		frmAltaProducto.add(lblNombreProducto);
		txtNombreProducto.setText("");
		frmAltaProducto.add(txtNombreProducto);
		frmAltaProducto.add(lblPrecioProducto);
		txtPrecioProducto.setText("");
		frmAltaProducto.add(txtPrecioProducto);
		btnAltaProducto.addActionListener(this);
		frmAltaProducto.add(btnAltaProducto);
		btnCancelarProducto.addActionListener(this);
		frmAltaProducto.add(btnCancelarProducto);

		frmAltaProducto.setSize(240,250);
		frmAltaProducto.setResizable(false);
		frmAltaProducto.setLocationRelativeTo(null);
		frmAltaProducto.addWindowListener(this);
		txtNombreProducto.requestFocus();
		frmAltaProducto.setVisible(true);
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
		if(frmAltaProducto.isActive())
		{
			frmAltaProducto.setVisible(false);
		}
		else if(dlgConfirmarAltaProducto.isActive())
		{
			txtNombreProducto.setText("");
			txtPrecioProducto.setText("");
			txtNombreProducto.requestFocus();
			dlgConfirmarAltaProducto.setVisible(false);
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
		if(evento.getSource().equals(btnAltaProducto))
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
				if(((txtNombreProducto.getText().length())!=0)
						&&((txtPrecioProducto.getText().length())!=0))
				{
					sentencia = "INSERT INTO producto VALUES (null, '"
							+ txtNombreProducto.getText()
							+ "', '" +txtPrecioProducto.getText()+ "')";
					
							
					FicheroLog.guardar(Login.nombreUsuario, sentencia); 
					statement.executeUpdate(sentencia);
					lblMensajeAltaProducto.setText("Alta de Producto Correcta");
				}
				else
				{
					lblMensajeAltaProducto.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaProducto.setText("Error en ALTA");
			}
			finally
			{
				dlgConfirmarAltaProducto.setLayout(new FlowLayout());
				dlgConfirmarAltaProducto.addWindowListener(this);
				dlgConfirmarAltaProducto.setSize(150,100);
				dlgConfirmarAltaProducto.setResizable(false);
				dlgConfirmarAltaProducto.setLocationRelativeTo(null);
				dlgConfirmarAltaProducto.add(lblMensajeAltaProducto);
				dlgConfirmarAltaProducto.setVisible(true);
				bd.desconectar(connection);
			}
		}
	}
}