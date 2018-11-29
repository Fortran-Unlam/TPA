package cliente.input;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_LAST;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwJoystickIsGamepad;
import static org.lwjgl.glfw.GLFW.glfwJoystickPresent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import config.Posicion;

public class Joystick {

	public Posicion ultimaPulsada = null;
	
	
	public Joystick() {
		
		// TODO: se supone que con esto leo el joystick
		glfwInit();
		glfwPollEvents();
		for (int i = 0; i <= GLFW_JOYSTICK_LAST; i++) {
			if (glfwJoystickPresent(i)) {
				// System.out.println(i);
			}
		}
		// System.out.println(glfwJoystickPresent(GLFW_JOYSTICK_1));
		// System.out.println(glfwJoystickIsGamepad(GLFW_JOYSTICK_1));
		if (glfwJoystickIsGamepad(GLFW_JOYSTICK_1)) {
			ByteBuffer gamepadButton = glfwGetJoystickButtons(GLFW_JOYSTICK_1);

			FloatBuffer gampepadAxes = glfwGetJoystickAxes(GLFW_JOYSTICK_1);

			for (int i = 0; i < gampepadAxes.capacity(); i++) {
				System.out.print((int) gampepadAxes.get(i) + " ");
				// casteado a entero va a de -1 a 1 para x y -1 a 1 para y
			}
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

			for (int i = 0; i < gamepadButton.capacity(); i++) {
				if (gamepadButton.get(i) == 1) {
					System.out.print(i);
				}
			}

			System.out.println();
		}
	}
}
