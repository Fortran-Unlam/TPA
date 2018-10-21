# Rama Cliente-Servidor

## Objetivo
Desarrollo por separado de las funcionalidades correspondientes al manejo de mensajes entre el cliente y el servidor.

## Version 21/10 (Servidor)

Se incluye JDBC del lado del servidor y ademas se implementan sockets junto a hilos.  
La idea es que cada cliente realice una peticion de login a través de sockets enviando sus creedenciales encriptadas  
al servidor, donde el servidor es el responsable de constatar si los datos recibidos por el cliente son validos  
a traves de una conexión y una consulta a la base de datos (MYSQL) y retornar la respuesta al cliente.

## Version 21/10 (Servidor)
Se incluye una interfaz grafica donde el usuario ingresa sus creedenciales y un boton para el envio de las mismas  
al servidor.
Abajo se encuentra un label con el estado de la solicitud, creedenciales validas o invalidas.  
Datos de prueba:  
Reflejo
asd1234