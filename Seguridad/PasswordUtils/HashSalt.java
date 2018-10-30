package PasswordUtils;

import javax.json.JsonValue;

public class HashSalt{

	private String hash;
	private String salt;

	public HashSalt(String hash, String salt) {
		this.hash = hash;
		this.salt = salt;
	}

	/* Getters y setters omitidos */
}
