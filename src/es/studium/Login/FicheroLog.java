package es.studium.Login;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FicheroLog 
{
	
	public static void guardar(String usuario, String mensaje) 
	{
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try 
		{
			FileWriter fw = new FileWriter("fichero.log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);
			salida.print("["+formato.format(fechaActual)+"]");
			salida.print("["+usuario+"]");
			salida.print("["+mensaje+"]");
			salida.print("\n");
			salida.close();
			bw.close();
			fw.close();
		}
		catch (Exception e) 
		{
			e.getMessage();
		}
	}

}