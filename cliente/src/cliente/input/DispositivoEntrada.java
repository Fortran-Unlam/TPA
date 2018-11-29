package cliente.input;

import config.Posicion;

public interface DispositivoEntrada {

	public Posicion getUltimaPulsada();
	
	public void setUltimaPulsada(Posicion ultimaPulsada);
}
