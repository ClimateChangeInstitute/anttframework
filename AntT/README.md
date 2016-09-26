
# Main AntT Web Site

This directory contains the main AntT project web resources.  Building
this project results in the generation of a war file that can be
served by Apache Tomcat 8.

Static pages are served in the `WebContent` directory.

## Requirements

This project is based on Java and therefore requires that a JDK is
installed.  A JDK of >=8 is required.

The project uses Maven to manage dependencies and build the
project. Maven version >=3.3.9 is required to build the project.

## Building

The project can be built by running maven from this directory. Typing

```bash
mvn clean verify
```

will compile the source code, run all unit tests, and generate the web
archive file

```
AntT-0.0.1-SNAPSHOT.war
```

in the `target` directory.

To deploy the site contained by the war file to Tomcat, copy the war file into the Tomcat
`webapps` directory.


<!--  LocalWords:  mvn
 -->
