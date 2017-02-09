package prova;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	
	
	ServerSocket server = null;
	Socket client = null;
	String stringaRicevuta = null;
	String stringaModificata = null;
	BufferedReader inDalClient;
	DataOutputStream outVersoClient;

	public Socket attendi() {
		try {
			System.out.println("1 server partito in esecuzione... ");
			// creo un server sulla porta 6789
			server = new ServerSocket(6789);
			// rimane in attesa di un client
			client = server.accept();
			// chiudo il server per impedire accesso ad altri client
			server.close();
			// associo due oggetti al socket del client per effettuare la
			// scrittura
			// e lettura delle strighe
			inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outVersoClient = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return client;
	}

	public void comunica() {
		try {
			// rimango in attesa della riga trasmessa dal client
			System.out.println("3 benvenuto client, scrivi una frase e la trasformo in maiuscolo. ATTENDO....");
			stringaRicevuta = inDalClient.readLine();
			System.out.println("6 ricevuta la stringa dal cliente : " + stringaRicevuta);

			// la modifico e la rispedisco al client
			stringaModificata = stringaRicevuta.toUpperCase();
			System.out.println("7 invio stringa modificata al client..");
			outVersoClient.writeBytes(stringaModificata + '\n');

			// termina l'elaborazione sul server : chiudo la connessione del
			// client
			System.out.println("9 server: fine elaborazione....vafanculo!");
			client.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Errore durante la comunicazione col server!");
			System.exit(1);
		}
	}

	public static void main(String arg[]) {
		Server servente = new Server();
		servente.attendi();
		servente.comunica();
	}
}
