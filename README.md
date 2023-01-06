![](https://raw.githubusercontent.com/williamniemiec/nshopping-api/master/docs/images/logo/logo.jpg)

<h1 align='center'>nShopping API</h1>
<p align='center'>API built with Spring Framework for a shopping system.</p>
<p align="center">
	<a href="https://github.com/williamniemiec/nshopping-api/actions/workflows/windows.yml"><img src="https://github.com/williamniemiec/nshopping-api/actions/workflows/windows.yml/badge.svg" alt=""></a>
	<a href="https://github.com/williamniemiec/nshopping-api/actions/workflows/macos.yml"><img src="https://github.com/williamniemiec/nshopping-api/actions/workflows/macos.yml/badge.svg" alt=""></a>
	<a href="https://github.com/williamniemiec/nshopping-api/actions/workflows/ubuntu.yml"><img src="https://github.com/williamniemiec/nshopping-api/actions/workflows/ubuntu.yml/badge.svg" alt=""></a>
	<a href="http://java.oracle.com"><img src="https://img.shields.io/badge/java-11+-D0008F.svg" alt="Java compatibility"></a>
	<a href="https://github.com/williamniemiec/nshopping-api/releases"><img src="https://img.shields.io/github/v/release/williamniemiec/nshopping-api" alt="Release"></a>
	<a href="https://github.com/williamniemiec/nshopping-api/blob/master/LICENSE"><img src="https://img.shields.io/github/license/williamniemiec/nshopping-api" alt="License"></a>
</p>
<p align="center">
	<a href='https://wniemiec-api-nshopping.up.railway.app/swagger-ui.html'><img alt='Deploy' src='https://railway.app/button.svg' width=200/></a>
</p>

<hr />

## ❇ Introduction
nShopping API is an API built with Spring Framework for a simple shopping system. This application was made for the sole purpose of learning the Spring framework better. You can interact with the project through the Heroku platform ([click here to access](https://wniemiec-api-nshopping.up.railway.app/swagger-ui.html)).

### Login information
| Email| Password |
|------- | ----- |
| william@email.com |123|

## ⚠ Warnings
The hosting service Heroku may have a certain delay (~ 1 min) for uploading the application so the loading of the website may have a certain delay. 

## 📖 Documentation
See [here](https://wniemiec-api-nshopping.up.railway.app/swagger-ui.html) the OpenAPI documentation.

## ✔ Requiremens
- [JDK 11+](https://www.oracle.com/java/technologies/downloads/);
- [Spring Framework](https://spring.io/projects/spring-boot);

## 🚩 Changelog
Details about each version are documented in the [releases section](https://github.com/williamniemiec/nshopping-api/releases).

## 🗺 Project structure
![architecture](https://raw.githubusercontent.com/williamniemiec/nshopping-api/master/docs/images/design/architecture.jpg)

## 📁 Files

### /
|        Name        |Type|Description|
|----------------|-------------------------------|-----------------------------|
|docs |`Directory`|Documentation files|
|src  |`Directory`|Application and test files|

### /src
|        Name        |Type|Description|
|----------------|-------------------------------|-----------------------------|
|main|`Directory`|Application files|
|test|`Directory`|Test files|

### /src/main
|        Name        |Type|Description|
|----------------|-------------------------------|-----------------------------|
|resources|`Directory`|Static files|
|java|`Directory`|Source files|

### /src/main/java/wniemiec/api/nshopping
|        Name        |Type|Description|
|----------------|-------------------------------|-----------------------------|
|config|`Directory`|Configuration classes|
|controllers|`Directory`|Classes that handle with HTTP requests and responses|
|domain|`Directory`|Application domain classes|
|dto|`Directory`|Data transfer object classes|
|filters|`Directory`|Classes that intercept the HTTP requests and responses|
|repositories|`Directory`|Classes that handle with database|
|security|`Directory`|Classes responsible for authentication|
|services|`Directory`|Classes responsible for providing data |
|App.java|`File`|Application entry point|
