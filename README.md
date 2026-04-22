Ejercicios Persistencia y bases de datos III 

Con el esquema de la Vuelta Ciclista responde a los siguientes ejercicios: 

Ejercicio 1 

Implementa un programa Java que permita registrar automáticamente una nueva etapa 
completa, con participación incluida, respetando la integridad del esquema. 

El programa debe: 

1. Solicitar por consola los siguientes datos para insertar una nueva etapa:
   
o Número de etapa (int) 

o Origen 

o Destino 

o Distancia en km 

o Fecha (YYYY-MM-DD)


2. Iniciar una transacción JDBC (setAutoCommit(false)).

   
3. Insertar la etapa en la tabla ETAPA con un PreparedStatement.

   
4. Insertar automáticamente en PARTICIPACION un registro por cada ciclista 
existente:

o El programa debe consultar todos los ciclistas. 

o Para cada uno, insertar una fila en participacion(numero_etapa, 
id_ciclista, posicion, puntos) 

o La posición debe generarse aleatoriamente sin repetir valores.  

o Se debe aplicar la lógica de puntos (100, 90, 80, 70, 60, 0) desde Java, no 
desde SQL. 


5. Si alguna inserción falla (por ejemplo, por clave primaria duplicada):
   
o Capturar la excepción 

o Realizar ROLLBACK 

o Mostrar por consola: “Etapa cancelada por error. No se guardaron los datos.” 


6. Si todo va bien:
   
o Hacer COMMIT 

o Mostrar el resumen: número de etapa, total de ciclistas añadidos y fecha. 
