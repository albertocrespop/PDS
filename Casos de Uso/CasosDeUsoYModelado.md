# Identificación de Casos de Uso

La aplicación permitirá a los usuarios aprender programación a través de la realización de cursos con diferentes tipos de ejercicios, estos cursos pueden ser creados por la comunidad o bien cursos nativos de la aplicación. A continuación, se identifican y detallan los principales casos de uso de la aplicación.

## Actores del sistema
- **Usuario**: Persona que utiliza la aplicación para realizar cursos o crear nuevos cursos.
- **Sistema**: Actor que inicia procesos para llevar a cabo la funcionalidad de la aplicación.

## Lista de Casos de Uso
| ID   | Nombre del Caso de Uso           | Descripción                                          | Actor Principal |
|------|--------------------------------|---------------------------------------------------|----------------|
| CU01 | Registrarse                   | Permite a un usuario crear una cuenta nueva.       | Usuario       |
| CU02 | Iniciar sesión                | Permite a un usuario autenticarse para acceder a la aplicación | Usuario       |
| CU03 | Crear Curso                  | Permite a los usuarios crear sus propios cursos con preguntas personalizadas | Usuario, Sistema       |
| CU04 | Realizar Curso               | Permite al usuario realizar un curso seleccionado | Usuario, Sistema     |
| CU05 | Consultar Estadísticas       | Permite al usuario visualizar sus estadísticas en la aplicación así como de un único curso | Usuario       |
| CU06 | Reanudar Curso              | Permite al usuario continuar con un curso desde el punto en que lo dejó | Usuario       |

## Descripción de Casos de Uso

### CU01: Registrarse
**Actor Principal:** Usuario  
**Precondiciones:** El correo electrónico del usuario no debe estar registrado en el sistema  
**Postcondiciones:** El usuario accede a la aplicación  

**Flujo Básico:**

1. El usuario accede a la ventana de registro
2. El usuario introduce su correo electrónico
3. El usuario introduce su contraseña 
4. El usuario gana acceso a la aplicación

**Flujos Alternativos:**
2a. Si el correo ya está registrado, el sistema muestra un mensaje de error.

---

### CU02: Iniciar Sesión
**Actor Principal:** Usuario  
**Precondiciones:** El usuario debe estar registrado.  
**Postcondiciones:** El usuario accede a la aplicación.  

**Flujo Básico:**
1. El usuario introduce su correo electrónico
2. El usuario introduce su contraseña
3. El sistema comprueba que el usuario este registrado
4. El sistema carga la página principal con los datos de aplicación del usuario

**Flujos Alternativos:**

2a. El usuario ha olvidado la contraseña y selecciona la opción para recuperarla
3a. Los datos introducidos son correctos y el sistema lanza un mensaje de error

---

### CU03: Crear Curso
**Actor Principal:** Usuario, Sistema 
**Precondiciones:** El usuario debe estar autenticado.  
**Postcondiciones:** El curso queda registrado. 

**Flujo Básico:**
1. El usuario accede a la opción "Crear Curso"
2. El usuario introduce el nombre y selecciona el tipo de curso*
3. El usuario pulsa la opción "Añadir Pregunta"
4. El usuario selecciona el tipo de pregunta
5. El usuario guarda la pregunta
6. El usuario guarda el curso
7. El sistema almacena el curso**

*Por defecto los cursos serán privados
**El curso se guardará en el repositorio de cursos del usuario

**Flujos Alternativos:**
2a. El usuario desea que el curso sea público y le ha puesto un nombre existente por lo que el sistema lanza un error
4a. El usuario crea un nuevo tipo de pregunta
6a. El usuario quiere que el curso sea privado por lo que tan solo se guarda un JSON.

---

### CU04: Realizar Curso

**Actor Principal:** Usuario, Sistema
**Precondiciones:** El Usuario debe estar autenticado
**Postcondiciones:** El estado del curso queda guardado

**Flujo Básico:**

1. El Sistema muestra la lista de cursos disponibles públicamente
2. El Usuario selecciona una estrategia de aprendizaje*
3. El Sistema presenta una pregunta al usuario
4. El Usuario responde la pregunta
5. El Sistema verifica la respuesta
6. El Usuario sale del curso
7. El Sistema guarda el progreso del curso y actualiza las estadísticas**

*La estrategia de aprendizaje 
**Se registra el progreso automáticamente

## Modelado de dominio

Por último la representación del Modelado de domínio que creemos oportuna en un análisis preliminar de la app es la siguiente:

![ModeladoImg](CModelado.jpeg)
