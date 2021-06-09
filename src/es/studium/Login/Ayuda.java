package es.studium.Login;

import java.io.IOException;

public class Ayuda {
	
	public static void ejecutarAyuda() 
	{
		try
		{
			Runtime.getRuntime().exec("hh.exe C:\\Users\\ALBERTO\\OneDrive\\Escritorio\\html help\\AyudaGestion.chm");
		}
		catch (IOException e)
		{
			e.getMessage();
		}
	}

}
