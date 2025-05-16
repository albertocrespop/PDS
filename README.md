# OfCourses - Plataforma de Aprendizaje Interactivo en Ciberseguridad
## Proyecto de prácticas para la asignatura Procesos de Desarrollo de Software de 3º del Grado en Ingeniería Informática por la Universidad de Murcia

### Profesor

- Jesus Sánchez Cuadrado

### Alumnos

- Alberto Crespo Palma
- Diego Quesada López
- Daniel Conesa Sáez

## Objetivo del proyecto

**OfCourses** es una aplicación de escritorio orientada al aprendizaje de ciberseguridad a través de cursos estructurados en lecciones y preguntas interactivas. Está diseñada para usuarios que deseen practicar y evaluar sus conocimientos mediante ejercicios autocontenidos y gamificados.

## Casos de uso y modelo de dominio

Los recursos de análisis del sistema se encuentran en el siguiente enlace:  
[Casos de uso y modelo de dominio](https://github.com/albertocrespop/PDS/tree/main/Casos%20de%20Uso)

---

## Funcionalidad principal

- Registro de usuarios (con email como identificador único).
- Carga de **cursos en formato YAML**, con soporte para distintos tipos de preguntas.
- Cada curso se divide en **lecciones**, cada una con sus propias preguntas.
- Visualización y avance de preguntas según una **estrategia definida** (secuencial, aleatoria, repetición).
- Progreso del usuario por curso y estadísticas personales.
- 4 tipos de preguntas disponibles:
  - Verdadero/Falso  
  - Flashcard  
  - Rellenar huecos  
  - Ordenar palabras

---

## Funcionalidades extra implementadas

- **Sistema de lecciones** dentro de los cursos:  
  Cada curso está compuesto por varias lecciones con preguntas independientes. El progreso del curso se calcula según cuántas lecciones se hayan completado.

- **Sistema de vidas**:  
  El usuario dispone de **5 vidas**. Por cada respuesta incorrecta, pierde una.  
  Las vidas se **recargan automáticamente cada día nuevo** (comparando la fecha actual con la última recarga).  
  Si no tiene vidas, **no podrá acceder a las lecciones**.

---

## Cómo ejecutar la aplicación

### Primera alternativa: desde entorno de desarrollo:
1. Abre el proyecto en tu entorno Java (por ejemplo, IntelliJ o Eclipse).
2. Asegúrate de tener configurado:
   - Java 17 o superior.
   - Dependencias de JavaFX, JPA y Jackson (definidas en el `pom.xml`).
3. Ejecuta la clase principal:  
   `main.Main.java`

### Segunda alternativa: desde el fichero ofcourses.jar:
1. Asegúrate de tener Java 17 configurado en el sistema.
2. Ejecuta desde una terminal: ´java -jar ofcourses.jar´. Dicho jar se encuentra en la carpeta del proyecto.

### Cómo usar la aplicación:
1. Regístrate con un nuevo usuario (introduciendo email, nombre y contraseña).
2. Desde la pantalla principal, pulsa en **"Añadir Curso"** y selecciona un archivo YAML válido (en la carpeta del proyecto hay 3 cursos de ejemplo).
3. Una vez añadido, pulsa sobre un curso para entrar y explorar las lecciones.
4. Responde las preguntas, no pierdas vidas y mantén tu racha diaria.

---

## Archivos de curso

En la raíz del proyecto encontrarás 3 cursos de prueba con contenidos de ciberseguridad, todos en formato `.yaml`. Puedes editar estos o crear nuevos.

---

## Notas técnicas

- Los cursos se cargan en tiempo de ejecución mediante la librería **Jackson YAML**.
- Persistencia con **JPA + SQLite** (por usuario).
- Estilo visual construido con **JavaFX**, adaptable a distintos tamaños de ventana.
