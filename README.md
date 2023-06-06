# messenger
REST Web Szolgáltatás JAX-RS használatával

# Eszközkészlet
- Eclipse
- Apache Tomcat 10.1.9
- Java 17
- Jersey 3.1.2

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

# REST Web Services 12 – Az alkalmazás szerkezetének megértése

`http://localhost:8080/messenger/webapi/myresource`

`http://localhost:8080`	Szerver, ami fut

`messenger`	Projekt neve

`webapi`	Valamilyen kontexus

`myresource`	Erőforrás

# REST Web Services 13 - Erőforrás létrehozása

`<servlet-class>`	A Jersey azt az osztály fogja elindítani, ami ebben a tagben van

`<init-param>` Ebben a tagben lesz a package `<param-name>`, amiben pedig egy osztály `<param-value>`, amely képes kezelni a kéréseket 

1. Hozzon létre egy új Java osztályt
2. Adjon hozzá egy metódust, amely visszaadja a választ
3. Győződjön meg arról, hogy az osztály a Jersey-ben konfigurált servelt init-param-jában van
4. Írja be az osztályt `@Patch` megjegyzéssel
5. Annotálja a metódust a megfelelő HTTP-módszer-annotációval
5. Írja be a metódust a `@Produces` válaszformátumot megadva

# REST Web Services 14 – XML-válasz visszaadása

A model osztály az üzenet egy példányát tartalmazza

A service osztály visszaada az üzenetek listáát vagy csatlakozik az adatbázishoz

1. Hozza létre a szükséges modellt és szolgáltatási osztályokat
2. Győződjön meg arról, hogy a modellosztályok rendelkeznek argumentummentes konstruktorral
3. Hívja fel a szolgáltatást a MessageResource-ból, és válaszoljon
4. Frissítse a `@Procedures` annotációt XML formátumra
5. Jelölje meg a modellosztályt az `@XmlRootElement` elemmel

 # DEBUG
 1. Frissítse a `@Procedures` annotációt JSON formátumra
 2. Illessze be a függőségeket a `pom.xml`-be 
 
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
# Köszönet
[Java Brains](https://www.youtube.com/@Java.Brains)
