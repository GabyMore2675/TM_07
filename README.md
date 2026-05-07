<img width="678" height="937" alt="image" src="https://github.com/user-attachments/assets/20e39abb-a845-4916-a12e-6e10ad2b8362" />Ejercicio 01:
<img width="886" height="941" alt="image" src="https://github.com/user-attachments/assets/7bd1550b-9e45-4012-bdfe-37d24f1151c0" />
<img width="953" height="1010" alt="Captura de pantalla 2026-05-06 222615" src="https://github.com/user-attachments/assets/34b7652e-e56e-4943-b5ee-a36d68315a17" />

Ejercicio 02:
Tabla comparativa de frameworks de desarrollo móvil
<img width="1654" height="604" alt="image" src="https://github.com/user-attachments/assets/be9f4adb-2746-43be-b581-deb8fdbf26d6" />
Conclusión
Cada framework presenta ventajas específicas dependiendo del tipo de proyecto y experiencia del equipo de desarrollo. Jetpack Compose destaca en aplicaciones Android nativas modernas, Flutter sobresale por su rapidez de desarrollo multiplataforma, React Native aprovecha el ecosistema JavaScript y Kotlin Multiplatform permite compartir lógica de negocio manteniendo interfaces nativas. Actualmente, Flutter y Kotlin Multiplatform muestran un crecimiento importante en proyectos multiplataforma modernos.

Ejercicio 03:
Selección justificada del framework para el proyecto

Para el proyecto actual del curso se requiere desarrollar una aplicación móvil moderna, funcional y con posibilidad de crecimiento futuro. La primera decisión técnica importante consiste en seleccionar el framework de desarrollo más adecuado considerando las necesidades del proyecto y las capacidades del equipo.

(a) Plataformas objetivo del proyecto

El proyecto tiene como principal plataforma objetivo Android, debido a que la mayoría de pruebas y despliegues académicos se realizan en dispositivos Android. Sin embargo, también se considera importante la posibilidad de extender la aplicación a iOS en el futuro sin rehacer completamente el código fuente. Por esta razón, resulta conveniente utilizar una solución multiplataforma.

(b) Experiencia previa del equipo

El equipo posee experiencia previa principalmente en desarrollo web utilizando tecnologías como HTML, CSS, JavaScript y frameworks frontend. Asimismo, existe conocimiento básico-intermedio en Kotlin y Android Studio adquirido durante el curso. No obstante, la experiencia en desarrollo móvil nativo avanzado todavía es limitada, por lo que se requiere una herramienta que facilite el desarrollo, reduzca complejidad y permita una curva de aprendizaje razonable.

(c) Requisitos de rendimiento y acceso a APIs nativas

El proyecto requiere una interfaz fluida, rápida y moderna basada en Material Design 3. Además, se contempla la posibilidad de acceder a funcionalidades nativas como almacenamiento local, cámara, notificaciones o sensores del dispositivo. Por ello, el framework seleccionado debe ofrecer buen rendimiento y soporte estable para integración con APIs nativas.

(d) Framework elegido y justificación

El framework seleccionado es Flutter. Esta decisión se basa en varios criterios técnicos analizados previamente:

Permite desarrollar aplicaciones multiplataforma utilizando una sola base de código.
Ofrece un rendimiento elevado gracias a su motor gráfico propio.
Integra de forma sencilla Material Design 3.
Cuenta con Hot Reload, lo que mejora significativamente la productividad durante el desarrollo.
Posee una comunidad grande y un ecosistema amplio de librerías.
Su documentación oficial es clara y completa.

Además, Flutter facilita la creación de interfaces modernas y consistentes en diferentes plataformas, reduciendo tiempos de desarrollo y mantenimiento. Considerando la experiencia del equipo y los objetivos del proyecto, representa un equilibrio adecuado entre facilidad de uso, rendimiento y escalabilidad.

(e) Framework descartado y justificación

El framework que se descartaría es Jetpack Compose como solución principal. Aunque ofrece excelente rendimiento y una integración nativa muy eficiente con Android, su principal limitación es que se encuentra enfocado específicamente al ecosistema Android. Esto implicaría desarrollar una solución adicional si posteriormente se desea compatibilidad con iOS.

También se evaluó React Native; sin embargo, se descartó debido a posibles limitaciones de rendimiento en aplicaciones complejas y a la dependencia del bridge para comunicación con componentes nativos. Finalmente, Kotlin Multiplatform presenta una propuesta muy potente, pero su curva de aprendizaje y complejidad de configuración son mayores para el nivel actual del equipo.

En conclusión, Flutter representa la opción más adecuada para el proyecto debido a su enfoque multiplataforma, facilidad de desarrollo, integración con Material 3 y buen rendimiento general.
