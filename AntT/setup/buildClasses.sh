# Need to create exploded war so we can reference the libraries (jar files)
mvn -f ../pom.xml clean compile war:exploded
