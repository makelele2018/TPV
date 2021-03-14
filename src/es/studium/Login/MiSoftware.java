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

public class MiSoftware implements WindowListener, ActionListener
{
	// Ventana Principal
	Frame ventana = new Frame("TPV");

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

	Menu mnuProyectos = new Menu("Productos");
	MenuItem mniAltaProyecto = new MenuItem("Alta");
	//MenuItem mniBajaProyecto = new MenuItem("Baja");
	//MenuItem mniModificacionProyecto = new MenuItem("Modificación");
	MenuItem mniConsultaProyecto= new MenuItem("Consulta");

	Menu mnuAsignaciones = new Menu("Mesas");
	MenuItem mniAltaAsignacion = new MenuItem("Alta");
	//MenuItem mniBajaAsignacion = new MenuItem("Baja");
	//MenuItem mniModificacionAsignacion = new MenuItem("Modificación");
	MenuItem mniConsultaAsignacion = new MenuItem("Consulta");

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

		mniAltaProyecto.addActionListener(this);
		mnuProyectos.add(mniAltaProyecto);
		if(tipo==0) // ¿Es administrador?
		{
			//mnuProyectos.add(mniBajaProyecto);
			//mnuProyectos.add(mniModificacionProyecto);
			mniConsultaProyecto.addActionListener(this);
			mnuProyectos.add(mniConsultaProyecto);
		}
		mnBar.add(mnuProyectos);

		mniAltaAsignacion.addActionListener(this);
		mnuAsignaciones.add(mniAltaAsignacion);
		if(tipo==0) // ¿Es administrador?
		{
			//mnuAsignaciones.add(mniBajaAsignacion);
			//mnuAsignaciones.add(mniModificacionAsignacion);
			mniConsultaAsignacion.addActionListener(this);
			mnuAsignaciones.add(mniConsultaAsignacion);
		}
		mnBar.add(mnuAsignaciones);

		ventana.setMenuBar(mnBar);

		ventana.setSize(320,160);
		ventana.setResizable(false);
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
		
	
	}}
