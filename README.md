## Description of Lab_SMTP

This project is a SMTP client written in Java. It's possible to send forged emails to a group (like a Prank). The sender is chosen randomly inside a group (a groupe contains at least 3 people). You can edit the configuration with files victims.utf8, messages.utf8 and config.properties.

## Client

Lab_SMTP is a maven project, if you want run it with no IDE, you can use the jar file in the directory target. You can change configurations files (**you cannot change filenames  like victims.utf8**), you can creat a folder where you want. Where you have the file pom.xml, use **mvn clean package** to creat the jar (standalone one).

To run the prank you just have to use the cmd : **java -jar target/SMTP-1.0-SNAPSHOT-standalone.jar path-resources**

In this case :  **java -jar target/SMTP-1.0-SNAPSHOT-standalone.jar src/main/resources/**

## Mock server

The mock server used in this project is **https://github.com/tweakers/MockMock**, we only use the jar file here.

### With Docker

- To use this Client smtp with docker, you just have to go in the directory **DockerMockMock** and use this two commands:
  - sh build-image.sh (you have to be in the directory with these files)
  - sh run-container.sh

Docker will build a image with the Mock server and it can be used with interface web on port **8080**. To access this server, you have to use the Ip from docker (ex: 192.168.99.100:8080 in mozzila). You can also have un acces by terminal with nectat on port **25000** (ex: nc 192.168.99.100 25000). You can change these ports in the file run-container.sh.

### Without Docker

If you want use the Mocker server without Docker, you can juste use the file .jar in the directory DockerMockMocker/src/ with the cmd : **java -jar MockMock.jar -p 25000 -p 8080**

## Configuration for a good Prank campaign

You have to change files in the directroy src/main/resources or creat a new folder 

- **config.properties** contains the IPaddress for the smtp server, the port, the number of groupe and the carbon copy (if you want to receive emails.) If you want to use esmtp you should setup usingEsmtpWithLoginAuthentification to true. You should also add your login and pass with esmptLogin and esmtpPassword.

- **messages.utf8** contains messages you want to send

  It has to begin with **Subject** : <subject> and end with **==**

  You can write what ever you want.

- **victims.utf8** contains emails addresses for the prank. You have to write one per line. It'should be large enough, because a group need at least 3 people. If victims are too small, the number of group will be reduce.

## Implementation

We have 3 package here : 

- **configuration** contains the configuration manager. 

  These class are used to read files like properties, victims and messages.

- **model** contains two packages, it's for the prank. 

  - mail is used to modelize Person, Message and Group

  - prank is used to modelize Prand and PrankGenerator

    - PrankGenerator return just a list of prank.

- **protocol** contains SmtpClient and the protocol

  The Client will send every prank and close the connection.