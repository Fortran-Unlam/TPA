package servidor;

import java.io.Serializable;

import org.codehaus.jackson.*;

public class Message implements Serializable {

	private static final long serialVersionUID = -4187638026720768019L;
	private String type;
	private Object data;

	public Message(String type, Object data) {
		super();
		this.type = type;
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
