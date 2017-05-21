import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}	
}

class MarcoServidor extends JFrame implements Runnable {
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread  mihilo= new Thread(this);
		
		mihilo.start();
	}
	
	private	JTextArea areatexto;

	@Override
	public void run() {
		//-----
		try {
			ServerSocket servidor = new ServerSocket(port);
			String nick,ip,mensaje;
			paqueteEnvio recibo;
			
			
			while(true){//bucle para que no se cierre la conexion
			Socket mio= servidor.accept();//aceptar las conexiones del puerto 999
			//recibir el outputstream del cliente
			ObjectInputStream pack_recibo= new ObjectInputStream(mio.getInputStream());
			
			recibo=(paqueteEnvio) pack_recibo.readObject();			
			nick=recibo.getNick();
			ip=recibo.getIp();
			mensaje=recibo.getMensaje();
			
			areatexto.append("\n"+nick+": "+mensaje+ " para: "+ip);
			
			Socket enviaDestino= new Socket(ip,port);
			ObjectOutputStream pack_reenvio = new ObjectOutputStream(enviaDestino.getOutputStream());
			
			pack_reenvio.writeObject(recibo);
			pack_reenvio.close();
			enviaDestino.close();
			
			
			mio.close();}
			
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
}
