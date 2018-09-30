# Trabajo Práctico del Taller de Programación Avanzada

## Introducción
Durante el taller desarrollaremos un juego: el clásico juego del snake o viborita, pero multijugador.
En las clases del taller se les proveerá de herramientas para poder realizar el juego en Java, con las buenas prácticas que irán adquiriendo.

### Metodología de trabajo
La realización del juego, tendrá 2 etapas con sus respectivas entregas. Sus fechas serán el 13 de Octubre, y el 10 de Noviembre. La entrega final será el día 1 de Diciembre.
Cada una de las etapas intermedias tienen un mínimo de requisitos obligatorios para entregar. No se aceptará la entrega sin los mismos. Se bajará puntos a la nota final por entregas tardías.

### Metodología de evaluación
Para tener el juego y el taller aprobado, los grupos deberán cumplir con todos los requisitos esenciales. Esto se corresponde a una nota de 4.
Superada esa cantidad, se podrán sumar puntos extras con los requisitos opcionales, y las buenas prácticas de programación.
Adicionalmente, se utilizará el seguimiento individual de los estudiantes para obtener una calificación final del taller.

### Entregas
1. La primera entrega debe contar con el diagrama de clases del modelo del juego, implementado y testeado.
2. La segunda entrega debe contar con la posibilidad de interactuar con los elementos del juego en tiempo real en un entorno simulado.
3. Entrega final, ¡todo lo que tengan!

> Se espera que las prácticas de programación sean buenas, y adecuadas al conocimiento adquirido en la materia.


## Mecánica del juego
La mecánica del juego es similar a la del clásico snake, pero se modifica la forma de jugar, agregando otros contrincantes.
* Una partida debe contar como mínimo con 2 viboritas
* Una partida puede contar con una o varias rondas
* El final de la ronda puede estar determinado por un intervalo de tiempo, un puntaje a alcanzar, supervivencia; o una combinación de las anteriores
* El puntaje se determina por la cantidad de fruta consumida
* Cuando cualquier viborita choca (su cabeza) contra cualquier cosa que no sea comida, muere
* Cuando la viborita "come" una fruta, debe crecer en longitud
* La viborita debe poder moverse por lo menos en 4 direcciones
* La viborita debe poder girar o rotar como máximo 90° a cada lado
* La configuración de la partida y su transcurso debe estar balanceada de modo tal que no beneficie a ningún jugador ya sea por cercanía intencional a las frutas, o por estar alejado de los otros contrincantes al momento de iniciar la partida, entre otros.


## Requisitos esenciales

1. Como usuario quiero ingresar al juego con mi nombre de usuario y una contraseña, para poder jugar contra otros jugadores en una sala
2. Como usuario quiero seleccionar una sala de las existentes, para poder ingresar y jugar con los participantes (*1)
3. Como usuario quiero crear nuevas salas para que ingresen otros jugadores (*2)
4. Como usuario quiero poder salir de una sala, incluso si en la misma el juego se encuentra corriendo
5. Como usuario quiero poder identificar cada viborita (saber quién la maneja, y ver su puntaje)

Notas:
> (*1) Una sala puede estar activa para que ingresen jugadores siempre, o sólo mientras un juego no esté activo en la misma. Aún así, debe aparecer en la lista de juegos como que el juego ya inició
>
> (*2) La partida podrá ser iniciada por el creador de la sala, o cuando todos los jugadores estén listos, o cualquier otra condición que consideren


## Requisitos opcionales

1. Como usuario quiero crear salas privadas, en las cuales solo pueden ingresar los usuarios que deseo *(esto se puede hacer, por ejemplo, a través de contraseñas, o salas que sólo se pueda ingresar con invitación)*
2. Como usuario quiero poder utilizar un joystick
3. Como usuario quiero poder jugar una partida de un solo jugar contra la AI, sin necesidad de ingresar usuario, ni crear salas
4. Como usuario quiero enviar reacciones o mensajes predeterminados durante la partida
5. Como usuario quiero elegir al momento de crear la sala un terreno donde ocurrirá el juego
6. Como usuario quiero configurar los controles de mi personaje
7. Como usuario quiero ver mi historial de partidas con sus detalles *(puntos, cantidad de fruta comida, muertes, asesinatos; de cada viborita, etc)*
8. Como usuario quiero ingresar a una partida, pero no participar, sino solo ver la partida, como un espectador
9. Como usuario quiero obtener ventajas temporales sobre los otros jugadores, luego de interactuar con objetos en el mapa (velocidad, invulnerabilidad, toque venenoso, proteccion temporal, etc)
10. Como usuario quiero desplazarme a una velocidad mayor en forma limitada
11. Como usuario quiero disfrutar de buenos efectos de sonido y música mientras juego

**Continuará...**

