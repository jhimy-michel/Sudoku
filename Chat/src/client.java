import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.*;
import java.net.*;
public class client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){
		
		nick= new JTextField(5);
		add(nick);
		
		
		
		JLabel texto=new JLabel("Chat");
		
		add(texto);
		
		ip= new JTextField(8);
		add(ip);
		
		campo_chat=new JTextArea(12,20);
		add(campo_chat);
		
		campo1=new JTextField(20);
		
		add(campo1);		
	
		miboton=new JButton("Enviar");
		EnviaTexto oir= new EnviaTexto();
		miboton.addActionListener(oir);
		add(miboton);	
		Thread mi_hilo= new Thread(this);
		mi_hilo.start();
	}
	
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//System.out.println(campo1.getText());
			try {
				//------el socket es el flujo de datos de salida
				//-----eso lo mandamos como parametro en la funcion
				Socket mensaje= new Socket("IP",port);
				paqueteEnvio datos= new paqueteEnvio();
				//--captura de los datos
				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());
				//------construccion del socket para los objetos
				ObjectOutputStream pack= new ObjectOutputStream(mensaje.getOutputStream());
				pack.writeObject(datos);
				pack.close();
				//---------------------
				
				
				//DataOutputStream flujo_salida = new DataOutputStream(mensaje.getOutputStream());
				//flujo_salida.writeUTF(campo1.getText());//en el flujo de datos de salidad va a viajar lo del textfield
				//flujo_salida.close();//cerramos el flujo de dato
				
				
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}	
		}
		
	}
	
		
	private JTextArea campo_chat;	
	
	private JTextField campo1,nick,ip;
	
	private JButton miboton;

	
	public void run() {
		try{
			ServerSocket servidor_cliente = new ServerSocket(9090);
			Socket cliente;
			paqueteEnvio pack_recibido;
			while(true){
				cliente=servidor_cliente.accept();//acpetar las conexiones dle exterior
				ObjectInputStream flujo_entrada= new ObjectInputStream(cliente.getInputStream());
				pack_recibido=(paqueteEnvio) flujo_entrada.readObject();
				
				campo_chat.append("\n"+pack_recibido.getNick()+": "+pack_recibido.getMensaje());
				
			}
			
			
		}catch(Exception e){}
		
	}
	
}
class paqueteEnvio implements Serializable{
	private String nick,ip,mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}