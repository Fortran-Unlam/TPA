package core;


public class Obstaculo {
	
	private Coordenada ubicacion;
	
	public Obstaculo(Coordenada ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Obstaculo(int x, int y) {
		this.ubicacion = new Coordenada(x, y);
	}
	
	public int getX() {
		return this.ubicacion.getX();
	}
	
	public int getY() {
		return this.ubicacion.getY();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ubicacion == null) ? 0 : ubicacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Obstaculo other = (Obstaculo) obj;
		if (ubicacion == null) {
			if (other.ubicacion != null)
				return false;
		} else if (!ubicacion.equals(other.ubicacion))
			return false;
		return true;
	}
	
	

}
