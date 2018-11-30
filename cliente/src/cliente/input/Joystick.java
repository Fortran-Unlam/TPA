package cliente.input;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwJoystickIsGamepad;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;

import java.nio.FloatBuffer;

import cliente.Cliente;
import config.Posicion;

public class Joystick implements Runnable {

	public Posicion ultimaPulsada = null;

	/**
	 * Inicializa el joystick
	 */
	public Joystick() {

		System.setProperty("org.lwjgl.util.Debug","false");
		glfwInit();
		glfwPollEvents();
		
	}

	/**
	 * Si está activo el joystick
	 * 
	 * @return
	 */
	public boolean isActive() {
		return glfwJoystickIsGamepad(GLFW_JOYSTICK_1);
	}

	/**
	 * Al mismo tiempo que se fija si está activo setea la ultima tecla
	 * 
	 * @return
	 */
	public Posicion getUltimaPulsada() {
//		for (int i = 0; i <= GLFW_JOYSTICK_LAST; i++) {
//			if (glfwJoystickPresent(i)) {
//			}
//		}
		if (glfwJoystickIsGamepad(GLFW_JOYSTICK_1)) {
//			ByteBuffer gamepadButton = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
			FloatBuffer gampepadAxes = glfwGetJoystickAxes(GLFW_JOYSTICK_1);

//			for (int i = 0; i < gampepadAxes.capacity(); i++) {
//				System.out.print((int) gampepadAxes.get(i) + " ");
//				// casteado a entero va a de -1 a 1 para x y -1 a 1 para y
//			}
			int x = (int) gampepadAxes.get(0);

			if (x >= 0) {
				this.ultimaPulsada = Posicion.ESTE;
			} else {
				this.ultimaPulsada = Posicion.OESTE;
			}

			int y = (int) gampepadAxes.get(1);

			if (y >= 0) {
				this.ultimaPulsada = Posicion.NORTE;
			} else {
				this.ultimaPulsada = Posicion.SUR;
			}

//			for (int i = 0; i < gamepadButton.capacity(); i++) {
//				if (gamepadButton.get(i) == 1) {
//					System.out.print(i);
//				}
//			}
//
//			System.out.println();
		}
		return this.ultimaPulsada;
	}

	public void setUltimaPulsada(Posicion ultimaPulsada) {
		this.ultimaPulsada = ultimaPulsada;
	}

	@Override
	public void run() {

		/*Posicion posicion = getUltimaPulsada();
		long miliSeg = System.currentTimeMillis();
		while (true) {
			if (System.currentTimeMillis() - miliSeg > 5 && this.ultimaPulsada != posicion) {
				Cliente.getConexionServidor().enviarTecla(this.ultimaPulsada);
			}
		}*/
	}
}
