package es.studium.Login;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MiSoftware implements WindowListener, ActionListener
{
	// Ventana Principal
	Frame ventana = new Frame("TPV");
	
	BaseDatos bd;
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	public static String nombreUsuario;

	MenuBar mnBar = new MenuBar();

	Menu mnuClientes = new Menu("Clientes");
	MenuItem mniAltaCliente = new MenuItem("Alta");
	MenuItem mniBajaCliente = new MenuItem("Baja");
	MenuItem mniModificacionCliente = new MenuItem("Modificación");
	MenuItem mniConsultaCliente = new MenuItem("Consulta");

	Menu mnuEmpleados = new Menu("Empleados");
	MenuItem mniAltaEmpleado = new MenuItem("Alta");
	MenuItem mniBajaEmpleado = new MenuItem("Baja");
	MenuItem mniModificacionEmpleado = new MenuItem("Modificación");
	MenuItem mniConsultaEmpleado = new MenuItem("Consulta");

	Menu mnuProducto = new Menu("Productos");
	MenuItem mniAltaProducto = new MenuItem("Alta");
	MenuItem mniBajaProducto = new MenuItem("Baja");
	MenuItem mniModificacionProducto = new MenuItem("Modificación");
	MenuItem mniConsultaProducto= new MenuItem("Consulta");

	Menu mnuMesa = new Menu("Mesas");
	MenuItem mniAltaMesa = new MenuItem("Alta");
	MenuItem mniBajaMesa = new MenuItem("Baja");
	MenuItem mniModificacionMesa = new MenuItem("Modificación");
	MenuItem mniConsultaMesa = new MenuItem("Consulta");
	
	Menu mnuAyuda = new Menu("Ayuda");
	MenuItem mniAyuda = new MenuItem("Ayuda");
	
	

	public MiSoftware(int tipo)
	{
		
		ventana.setLayout(new FlowLayout());
		mniAltaCliente.addActionListener(this);
		mnuClientes.add(mniAltaCliente);
		if(tipo==0) // ¿Es administrador?
		{
			mniBajaCliente.addActionListener(this);
			mnuClientes.add(mniBajaCliente);
			mniModificacionCliente.addActionListener(this);
			mnuClientes.add(mniModificacionCliente);
			mniConsultaCliente.addActionListener(this);
			mnuClientes.add(mniConsultaCliente);
		}
		mnBar.add(mnuClientes);

		mniAltaEmpleado.addActionListener(this);
		mnuEmpleados.add(mniAltaEmpleado);
		if(tipo==0)
		{
			mniBajaEmpleado.addActionListener(this);
			mnuEmpleados.add(mniBajaEmpleado);
			mniModificacionEmpleado.addActionListener(this);
			mnuEmpleados.add(mniModificacionEmpleado);
			mniConsultaEmpleado.addActionListener(this);
			mnuEmpleados.add(mniConsultaEmpleado);
		}
		mnBar.add(mnuEmpleados);

		mniAltaProducto.addActionListener(this);
		mnuProducto.add(mniAltaProducto);
		if(tipo==0) // ¿Es administrador?
		{
			mnuProducto.add(mniBajaProducto);
			mniBajaProducto.addActionListener(this);
			mnuProducto.add(mniModificacionProducto);
			mniModificacionProducto.addActionListener(this);
			mniConsultaProducto.addActionListener(this);
			mnuProducto.add(mniConsultaProducto);
		}
		mnBar.add(mnuProducto);

		mniAltaMesa.addActionListener(this);
		mnuMesa.add(mniAltaMesa);
		if(tipo==0) // ¿Es administrador?
		{
			mnuMesa.add(mniBajaMesa);
			mniBajaMesa.addActionListener(this);
			mnuMesa.add(mniModificacionMesa);
			mniModificacionMesa.addActionListener(this);
			mniAltaMesa.addActionListener(this);
			mnuMesa.add(mniAltaMesa);
			mniConsultaMesa.addActionListener(this);
			mnuMesa.add(mniConsultaMesa);
		}
		mnBar.add(mnuMesa);
		
		mniAyuda.addActionListener(this);
		mnuAyuda.add(mniAyuda);
		mnBar.add(mnuAyuda);

		ventana.setMenuBar(mnBar);

		ventana.setSize(400,200);
		ventana.setResizable(true);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void actionPerformed(ActionEvent evento)
	{
		// Clientes
		if(evento.getSource().equals(mniAltaCliente))
		{
			new AltaCliente();
		}
		else if(evento.getSource().equals(mniConsultaCliente))
		{
			new ConsultaCliente();
		}
		else if(evento.getSource().equals(mniBajaCliente))
		{
			new BajaCliente();	
		}
		else if(evento.getSource().equals(mniModificacionCliente))
		{
			new ModificarCliente();
		}
		// Empleados
		else if(evento.getSource().equals(mniAltaEmpleado))
		{
			new AltaEmpleado();
		}
		else if(evento.getSource().equals(mniConsultaEmpleado))
		{
			new ConsultaEmpleado();
		}
		else if(evento.getSource().equals(mniBajaEmpleado))
		{
			new BajaEmpleado();	
		}
		else if(evento.getSource().equals(mniModificacionEmpleado))
		{
			new ModificarEmpleado();
		}
		else if(evento.getSource().equals(mniAltaProducto))
		{
			new AltaProducto();
		}
		else if(evento.getSource().equals(mniConsultaProducto))
		{
			new ConsultaProducto();
		}
		else if(evento.getSource().equals(mniAltaMesa))
		{
			new AltaMesa();
		}
		else if(evento.getSource().equals(mniConsultaMesa))
		{
			new ConsultaMesa();
		}
		else if(evento.getSource().equals(mniBajaProducto))
		{
			new BajaProducto();
		}
		else if(evento.getSource().equals(mniBajaMesa))
		{
			new BajaMesa();
		}
		else if(evento.getSource().equals(mniModificacionProducto))
		{
			new ModificarProducto();
		}
		else if(evento.getSource().equals(mniModificacionMesa))
		{
			new ModificarMesa();
		}
		else if(evento.getSource().equals(mniAyuda))
		{
			Ayuda.ejecutarAyuda();
		}
		
	
	}
	

	
	

}
