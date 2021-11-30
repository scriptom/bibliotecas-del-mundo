# Bibliotecas del Mundo

Repositorio completo del proyecto "Bibliotecas del Mundo", una aplicación Java que comunica clientes y servidores mediante Java RMI.

Mediante este sistema se puede, mediante la interfaz de una sola biblioteca, consultar a las demás bibliotecas de la red conocida.

## Requisitos
- Java 1.8 o superior
- Maven 3.x o superior

## Estructura del proyecto

El proyecto consiste en 3 módulos:
- shared: Contiene clases comunes para los demás módulos, como el dominio de la aplicación
- client: Contiene la interfaz de usuario, mediante la cual un usuario puede consultar a las demás bibliotecas
- server: Contiene el servidor, que se encarga de comunicar con los clientes listados de libros mediante consultas

Cada módulo del proyecto trató de desarrollarse usando _Clean Architectures_, específicamente Arquitectura Hexagonal; no obstante puede haber fallos en la implementación de la misma.

## Instalación

### Desde el código fuente
Clonar el repositorio con Git:
```shell
git clone https://github.com/scriptom/bibliotecas-del-mundo.git
cd bibliotecas-del-mundo
```
Una vez clonado el repositorio, se debe instalar las dependencias:
```shell
mvn clean install
```
Esto generará una carpeta llamada `target` que contiene los archivos compilados.

Alternativamente, se pueden compilar los módulos individualmente:
```shell
cd shared
mvn clean install
cd ../client
mvn clean install
cd ../server
mvn clean install
```


#### Perfiles de Maven disponibles
(TBD)

### Usando ZIP
(TBD)
