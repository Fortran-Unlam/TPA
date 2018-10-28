# Rama Cliente-Servidor

## Objetivo
Desarrollo por separado de las funcionalidades correspondientes al manejo de mensajes entre el cliente y el servidor.

## Version 28/10

**A nivel Base de Datos**:  
-  Se implementa Hibernate utilizando una base de datos (MYSQL) montada en la nube.
-  Se adjunta el esquema(*.sql*) de la única tabla existente en la base de datos llamada usuarios.  
-  Se crea el paquete Hibernate que contiene la configuración de Hibernate, así como las entidades, actualmente usuarios, mapeadas con su respectiva tabla en la base de datos.
**Nota**, para mas información acerca de la implementación de Hibernate: https://www.youtube.com/watch?v=3ssa2-D9W_E

**A nivel código**:
-  Se incluye un paquete de ConfiguracionesGlobales que hace referencia a las constantes, actualmente de TipoMensajes, que manejan tanto cliente como servidor.
-  Se incluyen las clases TratamientoMensajeCliente y TratamientoMensajeServidor que realizan valga la redundancia el tratamiento de los mensajes provenientes del cliente y el servidor respectivamente, para mantener la legibilidad del código.
-  Ahora funciona correctamente el ingresar, del lado del cliente, ya que la escucha de mensajes provenientes del servidor, por parte del cliente es realizado en un thread.  

**A nivel funcionalidad**:  

Debería ser posible iniciar un servidor y varios clientes, al menos en entorno local con la dirección localhost y el puerto 1234, y realizar la registración y el acceso de distintos usuarios.  
El resultado de la registración y el acceso, se muestran en un label ubicado en la parte inferior del Jframe.

**Datos de la base de datos**:
-  Host: sql10.freesqldatabase.com
-  Puerto: 3306
-  Usuario: sql10263270
-  Contrasena: kcvyx4LrVl
-  Esquema(defecto): sql10263270

## Version 21/10 (Servidor)

Se incluye JDBC del lado del servidor y ademas se implementan sockets junto a hilos.  
La idea es que cada cliente realice una peticion de login a través de sockets enviando sus creedenciales encriptadas  
al servidor, donde el servidor es el responsable de constatar si los datos recibidos por el cliente son validos  
a traves de una conexión y una consulta a la base de datos (MYSQL) y retornar la respuesta al cliente.

## Version 21/10 (Cliente)
Se incluye una interfaz grafica donde el usuario ingresa sus creedenciales y un boton para el envio de las mismas  
al servidor.
Abajo se encuentra un label con el estado de la solicitud, creedenciales validas o invalidas.  
Datos de prueba:  
Reflejo
asd1234
