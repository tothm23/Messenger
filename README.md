# messenger
REST Web Szolgáltatás JAX-RS használatával

# Eszközkészlet
- Eclipse
- Apache Tomcat 10.1.9
- Jersey 3.2.1

# Projekt Létrehozása
1. **New** > **Project** > **Maven** > **Maven Project**
2. next >
3. **jersey-quickstart-webapp**, ezen belül az a típus, amelyiknek a Group ID-ja megyegyezik ezzel: **org.glassfish.jersey.archetypes**
4. Tetszés szerint: Project Name, Group ID, Artifact ID, Version, Package > Finish

# Szerver hozzáadása
1. **Servers** > **New** > **Servers**   
2. **Apache** > **Tomcat v10.1 Server** > Next
3. Add All > Next > Finish

# Projekt Indítása
1. Window > Web Browser > 0. Internal Web Browser
2. Indítsd el a Tomcat Szervert (A Szerver fülön jobb click a Szerverre és start)
3. Projekt Neve + jobb click > Run As > 1. Run on Server

# Az alkalmazás szerkezetének megértése

`http://localhost:8080/messenger/webapi/myresource`

`http://localhost:8080`	Szerver, ami fut

`messenger`	Projekt neve

`webapi`	Valamilyen kontexus

`myresource`	Erőforrás

# Erőforrás létrehozása

`<servlet-class>`	A Jersey azt az osztály fogja elindítani, ami ebben a tagben van

`<init-param>` Ebben a tagben lesz a package `<param-name>`, amiben pedig egy osztály `<param-value>`, amely képes kezelni a kéréseket 

1. Hozz létre egy új osztályt

2. Adj hozzá egy metódust, amely visszaadja a választ

3. Győződj meg arról, hogy az osztály a Jersey-ben konfigurált `servlet` tag `init-param`-jában van

4. Írd meg az osztályt a `@Patch` annotációval

5. Annotáld a metódust a megfelelő HTTP-módszer-annotációval

6. Írd meg a metódust a `@Produces` válaszformátumot  megadva

# XML-válasz visszaadása

A model osztály az üzenet egy példányát tartalmazza

A service osztály visszaada az üzenetek listáát vagy csatlakozik az adatbázishoz

1. Hozzd  létre a szükséges  modelt  és  szolgáltatási  osztályokat

2. Győződj  meg  arról, hogy a modellosztályok  rendelkeznek  argumentummentes  konstruktorral

3. Hívd meg a szolgáltatást az erőforrás osztályban és válaszolj

4. Frissítsd a `@Procedures`  annotációt XML formátumra

5. Jelöld meg a modellosztályt  az`@XmlRootElement`  annotációval

# DEBUG
 1. Frissítsd a `@Procedures` annotációt JSON formátumra
 2. Illeszd be a függőségeket a `pom.xml`-be 

 ```xml
<!-- XmlRootElement Dependency from YouTube Comments -->

<dependency>
	<groupId>javax.xml.bind</groupId>
	<artifactId>jaxb-api</artifactId>
	<version>2.3.1</version>
</dependency>

<dependency>
	<groupId>org.glassfish.jaxb</groupId>
	<artifactId>jaxb-runtime</artifactId>
	<version>3.0.0-M3</version>
</dependency>

<dependency>
	<groupId>org.glassfish.jersey.media</groupId>
	<artifactId>jersey-media-moxy</artifactId>
</dependency>

<!-- uncomment this to get JSON support -->
<dependency>
	<groupId>org.glassfish.jersey.media</groupId>
	<artifactId>jersey-media-json-binding</artifactId>
</dependency>
```

# A POST módszer megvalósítása
1. Jelöld meg a kezelő metódust `@POST` és `@Produces` segítségével

2. Fogadd el a Model típust argumentumként a kérés törzséhez való kötéshez

3. A `@Consumes` használatával add meg a kivételes kéréstörzs formátumát

# Lapozás és szűrés
Hiba: Ne módosítsd a méretet >= 0-ra, ahogy az itt látható. Vagy a `getAllMessages()` metódus soha nem lesz meghívva!

# Paraméter Annotációk
|Annotáció|URL|
|------------|-----|
|`@PathParam`|?param=value|
|`@MatrixParam`|;param=value|
|`@HeaderParam`|A Header-ben|
|`@CookieParam`|A Cookie-ban|
|`@Context`|Mindezeket magában foglalja. A `UriInfo` és a `HttpHeaders` osztállyal használható|
|`@BeanParam`|Egy saját osztály, ami magána foglalja az annotációkat. Értékeikre getterekkel lehet hivatkozni|

# Alforrások megvalósítása
Az osztályszintű `@Path` annotáció nem kötelező az alerőforrások számára
Az `@XmlTransient` annotáció megakadályozza a metódus leképezését, amikor az osztályt példányosítják

# Köszönet
[Java Brains](https://www.youtube.com/@Java.Brains)
