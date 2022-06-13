# Reflective Serialization
*Advanced Programming Techniques (CPSC 501)*

Simple project to showcase Java Reflection, Serialization/Deserialization, and Sockets.

Allows creation of 5 different types of objects whose instance variables can be set. Object creation allows different types of 
objects to represent a wide case of objects that might exist in a given project. The objects are then reflected to inspect their 
values which are then serialized, sent as a string through a socket, then deserialized and recreated on the second computer.

## Dependencies
Used for JSON.
```
com.fasterxml.jackson.core
com.fasterxml.jackson.databind
com.fasterxml.jackson.annotation
```

## Running
**Receiver Computer**
<br>
Runs the receiver/deserializer program on the target computer on port 6666
```
java Receiver
```

**Sender Computer**
<br>
Runs the sender/serializer program on the target computer on port 6666
```
java Sender <host name>
```

## Unit Testing
### Serialization
A few objects that the object creator can create were serialized then checked to see if the JSON had the correct values set.
### Deserialization
A simple object was serialized, deserialized, then through reflection the value of its field was checked for correctness.

