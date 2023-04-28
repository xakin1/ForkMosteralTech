# Lista de tareas pendientes
## APIS

### "APIs tiendas": Obtener acceso a las APIs de Idealista, Autoscout24, Ebay, Wallapop
* **Wallapop**: Hasta donde he podido observar no tiene un api pública que podamos consumir, lo máximo que he encontrado es gente intentando "romper" el api interna que ellos usan para devolver el resultado

* **Ebay**: Tengo una cuenta de developer, pero no me deja generar los tokens en modo sandbox, que son los que necesitaríamos para el dessarrollo, los tokens de producción, en principio, parece que si que deja, pero no lo puedo comprobar porque faltan datos para solicitarla

![ebay](https://user-images.githubusercontent.com/43583094/232519724-9c6a4e2b-047a-499b-9a08-01876c33d38b.jpg)

* **AutoScout**: Pendiente de que acepten/rechacen la solicitud para el token

* **Idealista**: Pendiente de que acepten/rechacen la solicitud para el token (llevamos ya meses esperando por ella, dudo que llegue si no ha llegado ya)

### "APIs autentificación"Conectarse con las Apis de Facebook y Google para iniciar sesión 

* **Facebook**: Aunque parece que funciona el log a veces cuando inicias la aplicación suelta un error E/com.facebook.GraphResponse: {HttpStatus: 400, errorCode: 190, subErrorCode: -1, errorType: OAuthException, errorMessage: Invalid OAuth access token signature.}, el cual convendría revisar en algún momento


## Aplicación
**Prioridad alta**
* Como no sé en que muy bien que pantalla encajar exactamente esto lo pongo aquí, cuando la aplicación pasa mucho tiempo en segundo plano (por tanto cambia de estado en su ciclo de vida), al volver a entrar nos vuelve a rederigir al login y lo que en verdad queremos es que se guarde la sesión del usuario.

* Al girar el móvil sería conveniente revisar como quedan las pantallas cuando el movil queda en horizontal, sobre todo cuando se trata de pantallas grandes, veease tablets. (Adrián)

### Pantallas

#### Login

**Prioridad alta** (Daniel)
* Añadir los botones a las APIs de google y facebook y que se comunique con ellas.
* Finalizar su diseño, que la disposición de los elementos sea la más próxima a la pantalla final.
* Habría que ir pensando en como engancharíamos este usuario a la blockChain para registrar sus transacciones, por tanto habría que ver si eso tiene alguna consecuencia a nivel de interfaz o solo sería problema de backend (supongo que en la blockChain tienes una cuenta/ un usuario/un id único, tal vez haya que ver como integrarse con eso)

**Prioridad media**
* Definir las comprobaciones que se aplicaran a las contraseñas y que se empiecen a comprobar actualmente solo está la de que tu contraseña no puede tener menos de x caracteres, pero aunque la incumpla te deja igualmente acceder a la aplicación. Por ello estaría bien definir que política de contraseñas vamos a utilizar y implementar las comprobaciones pertinentes.

* Definir que datos va a tener que registrar el usuario (cuales nos interesan realmente) y cuales van a ser únicos, vease el login de una persona es único (o no, hay que definirlo) y modificar la pantalla de registro conforme a lo definido.

**Prioridad baja**
* Que las operaciones de registro y login vayan contra la base de datos y comprobar que existen

#### Home
**Prioridad Media**
* Cargar los datos de los productos de las APIS (BLOQUEADA HASTA QUE TENGAMOS ACCESO A ALGUNA API)
#### Favoritos

**Prioridad Alta**
* Diseñar y implementar como el resto de pantallas va a comunicarse con esta pantalla para cargar los productos favoritos de cada usuario, una posible solución sería simplemente cargar todos los productos de los usuarios de cada vez pero entiendo que es una solución bastance ineficiente y habría que buscar otra forma

* Plantear que pasaría en el caso de que un producto dejara de estar disponible. Esto también incluiría el como nos enteramos de que un producto deja de estar disponible.

#### Add product

**Prioridad Alta**
* Decidir si al final vamos a tener productos o no y si esta pantalla va a tener alguna utilidad. Posibles consideraciones antes de tomar la decisión, en caso de que NO vayamos a tener productos internos:
    - El principal atractivo de nuestra aplicación es la blockChain, pero nuestro sistema se basa en consumir APIs cuyos productos no estan en la blockChain, por lo que a lo mejor sería necesario tener esta pantalla para registrar dicho producto en la blockchain.
    - Si hacemos caso al punto anterior tendríamos que duplicar todos estos productos que nos traen las apis y sería muy fácil perderles la vista (en cuanto a la cadena de transiciones se refiere), podríamos ignorar este problema teniendo en cuenta que es un ejercicio para clase y esto complicaría demasiado la práctica.
    - Si no lo tenemos no tenemos una forma real de decir por que manos o que imperfectos tuvo un producto, nuestra aplicación pasaría a solo tener una blockChain del usuario y de sus transacciones, pero estas nunca estarían sujetas a ningún objeto (Podría estar igualmente interesante para tener más info del vendedor y por tanto una mayor confianza que al final es de lo que se trata).
    - Si hacemos caso al punto anterior Tendríamos el problema de que no sabríamos que objeto está vendiendo o comprando el señor/a y sería muy fácil de trampear. Solucionar esto no sería trivial

**Prioridad media** (Adrián)
* Si al final queda esta pantalla habría que mejorarla visualmente y hacer que los botones hicieran algo (vease añadir la imagen, que aunque no se guarden ya podríamos ir definiendo esa lógica).
#### Profile

**Prioridad Alta** (Xaquín)
* Crear una pantalla/parametrizar el fragmento que ya existe, para que cuando clickes en el perfil de una persona puedas verlo.

**Prioridad Media** (Adrián)
* Tal vez necesitamos añadir un perfil "avanzado" donde muestres más datos/puedas cambiar tus datos / puedas borrar tu cuenta.

#### Filter Advanced (cuando en home pulsas un filtro)

**Prioridad alta** (Xaquín)
* Finalizar la gestión de filtros (No queremos tener un fragmento para cada posible departamente, queremos tener una misma vista parametrizada, hay algunos filtros que serán comunes a todos los departamentos y tendrán una función similar, hay otros que simplemente serán únicos).

**Prioridad media**

* Hacer que los filtros se apliquen al conjunto de objetos.

* Crear la actividad de "filtros", que te permitirá gestionar de una forma más cómoda los filtros que quieras aplicar.

#### Product Detail

**Prioridad alta**

* Finalizar la vista (Adrián)
    * Añadirle la opción de favoritos 
    * Añadirle el enlace a Propietario (nos falta el parametrizar la vista de perfil, pero aún así ya debería estar planteado el enlace)
    * Si al final va a tenerlo, añadirle las transacciones del producto (con datos dummy), entiendo que ver el historial refleja esto, sería enlazar/progamar la lógica para que este bptón funcione
    * Refactorizar la vista, por culpa de que se metió el action bar, en teoría se pisa un cierto fragmento de la imagen, habría que solucionarlo

### Backend

Habría que definir que datos vamos a guardar, que operaciones vamos a definir y con que tecnología vamos a desarrollarlo para ir empezando a avanzar por este frente. En clase ya se dijo que como estuviese hecho le daba igual así que dudo que haya una semana con la tarea de desarrollar el backend, esto es algo que vamos a tener que ir desarrollando en paralelo con la aplicación.
