package cliente.input;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Input {

	public Teclado teclado = new Teclado();
	//public Joystick joystick = new Joystick();

	public Input() {

		/*if (joystick.isActive()) {
			joystick.run();
		}*/
	}

	public static void terminate() {
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}
