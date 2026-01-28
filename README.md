# App de Rick and Morty - Tarea 3 PMDM

## 📝 Introducción
Esta aplicación ha sido desarrollada como parte de la Tarea 3 de Programación de Multimedia y Dispositivos Móviles. El propósito es gestionar y visualizar información sobre los episodios y personajes de la serie "Rick and Morty", permitiendo a los usuarios llevar un control de los episodios vistos y consultar estadísticas.

## ✨ Características principales
* **Autenticación:** Sistema de Login y Registro vinculado a **Firebase Auth**.
* **Listado de Episodios:** Visualización de episodios obtenidos desde una API REST.
* **Detalle y Personajes:** Información extendida de cada episodio con carga progresiva de personajes mediante **Retrofit**.
* **Gestión de Vistos:** Posibilidad de marcar episodios como "vistos", guardando el estado en **Firebase Firestore**.
* **Estadísticas:** Gráfica circular que muestra el porcentaje de progreso (vistos vs. totales) usando **MPAndroidChart**.
* **Ajustes:** Configuración de tema (Modo Oscuro) e idioma mediante **SharedPreferences**.

## 🚀 Tecnologías utilizadas
* **Kotlin** como lenguaje principal.
* **Firebase:** Authentication para usuarios y Firestore para la base de datos en tiempo real.
* **Retrofit:** Para el consumo de la API de Rick and Morty.
* **RecyclerView:** Con adaptadores personalizados para listas dinámicas.
* **Corrutinas de Kotlin:** Para la gestión de hilos y peticiones asíncronas seguras.
* **SharedPreferences:** Para la persistencia de ajustes locales.
* **Material Components:** Para un diseño moderno y responsive.

## 🛠️ Instrucciones de uso

### 1. Clonación:
   ```bash
   git clone https://github.com/Carandainf/Tarea3_Aranda_Calatayud_Carlos
   ```
### 2. Configuración de Firebase
* Crear un proyecto en la consola de Firebase.
* Añadir una App Android con el paquete `dam.pmdm.tarea3_aranda_calatayud_carlos`.
* Descargar el archivo `google-services.json` y colocarlo en la carpeta `app/`.
* Habilitar **Email/Password Auth** y **Cloud Firestore**.

### 3. Ejecución
* Abrir en Android Studio, sincronizar Gradle y ejecutar en un emulador o dispositivo físico.

---

## 🧠 Conclusiones del desarrollador

* **Aprendizajes:** He profundizado en el uso de arquitecturas basadas en Fragments y Navigation Component, así como en la sincronización de datos remotos con Firestore.
  
* **Dificultades:**
     * **Problemas para hacer funcionar la barra lateral de manera táctil**...Resulta que cambiando el modelo de emulador, a nexus 5x por ejemplo, funciona correctamente
     * **Cantidad de cuelgues infinitos con la última versión de Android studio**
     * **Problemas para mostrar en la posición adecuada el ToolBar**, al final tuve que quitar el código que pone Android por defecto y personalizar la barra
     * **Problemas para que el proyecto reconozca la librería de firebase**. He tenido problemas con un fichero llamado libs.version.tom1 le pongo comentario, para que arranque con las dependencia descargadas de FireBase.
     * **He elegido la librería Coil** (para cargar las imágenes de los personajes, ya que se valora muy positivamente en internet, además de aconsejarla las diferentes IA).
     * **Gestión de límites de la API (Error 429):** El reto principal fue evitar el bloqueo por exceso de peticiones. Se solucionó implementando retardos controlados (`delay`) y una estrategia de carga progresiva de personajes.
    * **Configuración de Idiomas y Temas:** Lograr que los cambios en `SharedPreferences` se aplicaran instantáneamente en todas las actividades sin romper el ciclo de vida de la App.
    * **Navegación y Backstack:** Controlar el comportamiento del botón "atrás" tras el cierre de sesión para evitar que el usuario volviera a pantallas protegidas.

* **Conclusión final:** Totalmente desbordado con la cantidad de información necesaria para hacer la app, realmente sino fuera por los videos suyos y de otro copañeros y la ayuda de la IA, para todo el tema de errores, como el error 429, me hubiera sido imposible hacer nada...Sinceramente sigo muy desanimado con la assignatura...

*  **Un saludo**
    
---

## 📸 Capturas de pantalla
### Pantalla de Login
<img width="1279" height="914" alt="imagen" src="https://github.com/user-attachments/assets/a8bc9a57-034d-43bb-8135-42a6f8371ead" />

### Pantalla de Registro
<img width="1222" height="891" alt="imagen" src="https://github.com/user-attachments/assets/b8fc41c4-adc7-47f7-beec-d597680b136f" />

### Pantalla de Episodios
<img width="1202" height="923" alt="imagen" src="https://github.com/user-attachments/assets/61aa4564-1643-4f38-86e3-b98021cefcdb" />

### Pantalla de detalles de Episodios
<img width="1210" height="914" alt="imagen" src="https://github.com/user-attachments/assets/c2cd1794-b112-41fc-8b28-8a5d8be7d8e5" />

### Barra lateral
<img width="1171" height="910" alt="imagen" src="https://github.com/user-attachments/assets/82db74f0-effa-4b53-a1da-8a53c6881824" />

### Pantalla de estadísticas
<img width="1164" height="909" alt="imagen" src="https://github.com/user-attachments/assets/903b3e00-ef9a-4c65-be2f-c1ddefa24fde" />

### Pantalla de Ajustes
<img width="1163" height="933" alt="imagen" src="https://github.com/user-attachments/assets/4005eff0-f9d9-4db6-a9b5-a435cea1ebe0" />

### Pantalla de Acerca de ...
<img width="1167" height="923" alt="imagen" src="https://github.com/user-attachments/assets/e51f9bd4-b5c1-4257-aec2-e8727d0116f8" />

### En la pantalla de episodios menu de vistos/No vistos
<img width="1165" height="912" alt="imagen" src="https://github.com/user-attachments/assets/083d286a-9277-4642-a234-67d495a53846" />

### Con el selector de Vistos activado
<img width="1167" height="908" alt="imagen" src="https://github.com/user-attachments/assets/185585ae-91a5-4e70-b85c-a815d604558d" />
