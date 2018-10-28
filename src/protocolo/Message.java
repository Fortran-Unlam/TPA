package protocolo;

import java.io.Serializable;

import javax.json.JsonObject;

public class Message implements Serializable {

	private static final long serialVersionUID = -4265077006956449470L;
	private JsonObject json;
	
	public Message(JsonObject json) {
		this.json = json;
	}

	public JsonObject getJson() {
		return json;
	}
	

}
