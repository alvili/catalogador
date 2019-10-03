# catalogador

# Descripción del proyecto

Este proyecto nace en el contexto de un entorno académico de programación para Android y desarrolla una aplicación para identificar y catalogar libros a partir de su código de barras.

La aplicación reconoce el código ISBN de un libro leyendo su código de barras con la cámara y realiza una consulta a un servicio API REST para identificarlo y obtener el título, el autor, la editorial, la fecha de publicación, la carátula, etc... La intención es poder realizar un inventario de libros de forma rápida y cómoda, sin tener que buscar e introducir la información a mano. Además de la información propia de libro, la aplicación guarda automaticamente información adicional como la fecha en que se ha realizado la búsqueda y la ubicación geográfica. También permite añadir notas personales por cada libro y contempla guardar distintas copias de una misma edición del libro.


# Descripción de objetivos

Dada su naturaleza académica, el principal objetivo ha consistido en aprender y "probar cosas" así como la familiarización con el entorno de programación. Por ello y dado el horizonte temporal disponible, no ha sido posible implementar todas las funcionalidades. La relación de objetivos alcanzados y no alcanzados es la siguiente:


## Alcanzados

- Navegación e intercambio de datos entre las distintas vistas mediante inents i bundles
- Obtención de datos de un servicio REST y mapeo de los mismos a un objeto
- Almacenaje de los datos en una base de datos local sqlite
- Guardado y recuperación de datos de distintos tipos con la base de datos (boolean, imagenes, fechas, texto y números) 
- Ejecución de servicios asíncronos para obtener imagenes
- Uso de la cámara
- Uso de la localización
- Uso de herencia y polimorfismo
- Uso de un listview y un inflater para llenarlo de contenido.

## No alcanzados

- Uso de librerias para escanear códigos de barras
- Uso de fragments
- Mejora del aspecto gráfico y la apariencia de la aplicación
- Desarrollo de un backend para guardar la base de datos
- Mejorar la complejidad de la base de datos con tabla de autores, generos, etc...


# Funcionamiento

La aplicación implementa actualmente dos funcionalidades: Escanear un libro y navegar entre los libros ya guardados.

[![Fotol](https://raw.githubusercontent.com/alvili/catalogador/master/images/menu.png)](https://raw.githubusercontent.com/alvili/catalogador/master/images/menu.png)


"Scan New Book" abre la camara para tomar la foto de un código de barras de un libro. Esta foto se guarda en la base de datos por si fuera necesario consultarla mas adelante y deberia ser procesada para identificar dicho código (funcionalidad pendiente). A continuación se abre una nueva activity donde se muestra el código obtenido (actualmente hardcodeado) y un botón para confirmar que se trata del correcto. Desde ahi se podria modificar o introducir otro a mano.

[![Foto2](https://raw.githubusercontent.com/alvili/catalogador/master/images/codigo.png)](https://raw.githubusercontent.com/alvili/catalogador/master/images/codigo.png)

Pulsando el boton de aceptar, la aplicación hace una petición a [Open Library], una API que ofrece un servicio REST para identificar libros a partir de distintos códigos, en particular el ISBN. La aplicación realiza la petición de forma asíncrona y recibe un documento json, que es convertido a un objeto local mediante la libreria [Retrofit] segun la estructura de datos de [Open Library] (ver modelo en el apratado UID). Estos datos son convertidos a un formato local, mediante la selección de los campos mas significativos. 

La estrucutra de datos local (ver modelo en el apratado UID) está pensada para guardar libros pero también otros productos como discos o videojuegos (funcionalidad no implementada) mediante la herencia y el polimorfismo de las clases. A parte de las propiedades, las clases del modelo implementan métodos para exportar sus contenidos empaquetados en un bundle para facilitar el intercambio de datos entre activities.

Una vez obtenidos los datos de un libro, se abre una nueva activity donde se muestran en un formulario donde pueden ser editados de forma manual. Asimismo, se añaden campos nuevos para introducir el precio de libro así como comentarios. El formulario da opción a guardar esta información en la base datos local o descartarla volviendo al menu principal.

[![Foto3](https://raw.githubusercontent.com/alvili/catalogador/master/images/libro.png)](https://raw.githubusercontent.com/alvili/catalogador/master/images/libro.png)

La base de datos local se organiza en tres tablas. Una dedicada a libros donde cada entrada corresponde a una edición concreta de un libro y se identifica mediante una ID única. Otra donde se almacenan todas las carátulas de manera única identificadas por un ID. Y una tercera tabla donde se guardan los datos de una copia particular de un libro (fecha de búsqueda, ubicación, notas,..) lo que permite almacenar distintas copias de una misma edición.

La opción "Browse" muestra un listview con un listado de todos los libros que la aplicación ha escaneado así como la fecha en la que esto sucedió, y permite seleccionar cualquiera de ellos. Al hacerlo, se muestra la información completa permitiendo su modificación. El formulario ofrece distintas opciones: Modificar el registro, guardarlo como un registro nuevo o eliminarlo.

[![Foto4](https://raw.githubusercontent.com/alvili/catalogador/master/images/list.png)](https://raw.githubusercontent.com/alvili/catalogador/master/images/list.png)

La aplicación deberia permitir una tercera opción que consisitira en sincronizar la base de datos local con una base de datos ubicada en un servidor, pero es otra de las funcionalidades no implementadas.


# UID

## Modelo de datos:

[![Model](https://raw.githubusercontent.com/alvili/catalogador/master/images/LocalModel.gif)](https://github.com/alvili/catalogador/tree/master/images/LocalModel.gif)

El modelo esta basado en la herencia. La clase primaria es **Scan**, que recoge las propiedades relativas a una busqueda: codigo de barras, la imagen del código de barras, las coordenadas gps, notas, etc.. La clase **Media** extiende Scan incorporando propiedades comunes a cualquier media de los que se contempla catalogar (libros, discos,..). Propiedades como el título, el autor, etc.. Finalmente la clase **Book** recoge las propiedades únicas de un libro y que no se darian en los otros medias, como seria el número de páginas, el isbn, etc.. A parte estaria la clase **Cover** que encapsula las propiedades propias de una cubierta como serian el link a la cubierta y la imagen de la misma en formato bitmap.


## Modelo de datos del servicio REST de Open Library:

[![OpenLibrary Model](https://raw.githubusercontent.com/alvili/catalogador/master/images/OpenLibModel.gif)](https://github.com/alvili/catalogador/tree/master/images/OpenLibModel.gif)

Corresponde al [modelo de datos utilizado por Open Library].

[Open Library]: <https://openlibrary.org/>
[Retrofit]: <https://square.github.io/retrofit/>
[modelo de datos utilizado por Open Library]: <https://openlibrary.org/dev/docs/api/books>
