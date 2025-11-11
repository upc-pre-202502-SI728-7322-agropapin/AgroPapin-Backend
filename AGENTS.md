# 📜 Instrucciones para el Agente de IA (Proyecto IoT Plantaciones)

## 1. Contexto General del Proyecto

Este es un API backend para un sistema de seguimiento de plantaciones (Proyecto IoT).
El stack principal es **Spring Boot 3+**, **Java 17+** y **JPA (Hibernate)**.

El objetivo arquitectónico más importante es seguir un enfoque estricto de **Domain-Driven Design (DDD)**.

---

## 2. 🚨 Reglas de Arquitectura y DDD (¡Mandatorio!)

Estas reglas son la prioridad número uno.

### 2.1. Arquitectura de Capas (Layers)

El proyecto sigue una arquitectura de 4 capas. NO mezcles responsabilidades:



1.  **`domain` (El Núcleo):**
    * **Contiene:** Entidades, Agregados, Objetos de Valor (Value Objects), Repositorios (solo interfaces), interfaces de servicio y Lógica de Dominio pura.
    * **PROHIBIDO:** Cero dependencias de Spring (`@Service`, `@Component`, etc.), cero dependencias de infraestructura (JPA, SQL, etc.). Debe ser POJO (Plain Old Java Objects).
    * La lógica de negocio DEBE estar dentro de las entidades (Ej: `plantacion.regarCultivo()`), no en servicios anémicos.
    * Para cada agregado se debe extender de `AuditableAbstractAggregateRoot` que contiene la generacion del id basado en UUID. y datos de creacion y update.

2.  **`application` (Casos de Uso):**
    * **Contiene:** Implementacion de Servicios de Aplicación (`@Service`) que orquestan los casos de uso.
    * **Regla:** Esta capa es delgada. Su trabajo es: 1) Cargar un agregado desde un repositorio, 2) Llamar a un método de la entidad de dominio, y 3) Guardar el agregado en el repositorio.
    * Aquí SÍ se usa Spring (`@Service`, `@Transactional`).

3.  **`infrastructure` (Implementación):**
    * **Contiene:** Implementaciones de las interfaces del dominio.
    * Ejemplos: Clases de Repositorio JPA (`@Repository`), clientes de API de IoT, adaptadores de mensajería.

4.  **`interfaces` (API):**
    * **Contiene:** Controladores REST (`@RestController`).
    * **PROHIBIDO:** No debe contener lógica de negocio. Solo debe mapear DTOs y delegar el trabajo a la capa de `application`.
    * En esta capa se manejan las carpetas de resources y transform para mapeo de los controlles, se usan Record para los resources.
    * Los transform deben seguir el formato de funcion estatica dentro de la clase como `toResourceFromEntity`

### 2.2. Bloques de Construcción de DDD

* **Agregados (Aggregates):**
    * Siempre identifica el `Aggregate Root`. Para nuestro proyecto, `Plantacion` es un Agregado que contiene `Parcelas`.
    * **Regla:** Los repositorios SÓLO deben existir para los `Aggregate Roots`. No crees un `ParcelaRepository` si `Parcela` no es una raíz.

* **Objetos de Valor (Value Objects):**
    * Úsalos para conceptos que no tienen identidad, solo atributos (Ej: `Ubicacion(latitud, longitud)`, `CondicionAmbiental(temperatura, humedad)`).
    * **Regla:** Deben ser **inmutables** (usa `final` en sus campos y un constructor).

---

## 3. Reglas Tecnológicas (Stack)

* **Java:** Usar Java 17+. Prefiere siempre Streams, `Optional`, y records para DTOs.
* **Spring:**
    * Usar inyección de dependencias por **constructor** (no `@Autowired` en campos).
    * Los `@Service` pertenecen a la capa de `application`, no a `domain`.
* **Pruebas:**
    * Usar **JUnit 5**.
    * Las pruebas de la capa `domain` deben ser pruebas unitarias puras, sin cargar el contexto de Spring.

## 4. Convenciones de Código

* **Idioma:** Todo el código (clases, variables, métodos) debe estar en **Inglés**.
    * *Bien:* `Plantation`, `CropService`, `addParcel()`.
    * *Mal:* `Plantacion`, `ServicioCultivo`, `agregarParcela()`.
* **Errores:** Usar excepciones personalizadas (Ej: `PlantationNotFoundException`) que hereden de `RuntimeException`.