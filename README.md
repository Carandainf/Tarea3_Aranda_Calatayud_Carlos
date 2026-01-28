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
1. **Clonación:**
   ```bash
   git clone https://github.com/Carandainf/Tarea3_Aranda_Calatayud_Carlos

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
* **Dificultades:** El mayor reto fue la gestión de límites de la API (error 429), lo cual solucioné implementando retardos controlados (`delay`) y carga progresiva para mejorar la experiencia de usuario y la estabilidad de la app.

---

## 📸 Capturas de pantalla
*(Arrastra aquí tus imágenes para dar por finalizada la documentación)*