
# Main AntT Web Site

This directory contains the main AntT project web resources.  Building
this project results in the generation of a war file that can be
served by Apache Tomcat >=8.

Static pages are served in the `WebContent` directory.  Examples of
such content are images, JavaScript, and CSS.

## Requirements

This project is based on Java and therefore requires that a JDK is
installed.  A JDK of >=8 is required.

The project uses Maven to manage dependencies and build the
project. Maven version >=3.3.9 is required to build the project.

The project requires that a PostgreSQL is installed.  PostgreSQL
version >= 9.4 is required by the project.

## Building

First, ensure that the database has been installed.  The `README.md`
file in the `./etc/sql` directory describes the process.

The project can be built by running Maven from this (AntT) directory. Typing

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


<!--  LocalWords:  mvn AntT WebContent CSS JDK PostgreSQL README md
 -->
<!--  LocalWords:  webapps
 -->
