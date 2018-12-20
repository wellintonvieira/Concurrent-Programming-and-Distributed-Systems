package resource;

import java.util.ArrayList;

public class Strings {
	
	public static final String app_name = "RubbitMQ";
	public static final String app_queue = "wellinton";
	public static final String app_username = "mqadmin";
	public static final String app_password = "mqadmin";
//	public static final String app_host = "can707402.canoinhas.ifsc.edu.br";
	public static final String app_host = "192.168.1.41";
	public static final String app_vhost = "/";
	public static final String app_topic = "cstads";
	
	public static final String app_message_emitter = "Para";
	public static final String app_message_receiver = "De";
	
	public static final String app_error_start = "Não foi possível iniciar";
	public static final String app_error_send = "Não foi possível enviar";
	public static final String app_error_empty = "Escreva uma mensagem";
	public static final String app_error_queue = "Selecione uma fila para enviar a mensagem";
	
	public static final ArrayList<String> queues = new ArrayList<String>();
	public static final ArrayList<String> getQueues() {
		queues.add("aldir");
		queues.add("bruno");
		queues.add("daniel");
		queues.add("fernanda");
		queues.add("joão");
		queues.add("luciano");
		queues.add("mateus");
		queues.add("matheus");
		queues.add("vinicius");
		queues.add("wellinton");
		return queues;
	}
}
