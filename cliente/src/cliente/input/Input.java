package cliente.input;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import cliente.Cliente;

public class Input {

	public Teclado teclado = new Teclado();
	//public Joystick joystick = new Joystick();

	public Input() {

		/*if (joystick.isActive()) {
			joystick.run();
		}*/
	}

	public static void terminate() {
		try {
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		} catch (UnsatisfiedLinkError e) {
			Cliente.LOGGER.error("No se puede terminar el Joystick");
		}
	}
}
