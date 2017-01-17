
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

These sample layouts utilize HTML5 and Bootstrap v3.3.7. These are to be used for templating only. These layouts will later be built automatically.

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

However, this assumes that the database has already been generated and populated with data.  If this has not ocurred, follow 
the following directions.

**NOTE** The following tasks require that the **USER** and **PASS** are set in the admin.py file.


### Deleting the database

```bash
cd anttframework/AntT/setup && ./deleteDB.sh
```

### Creating the database

```bash
cd anttframework/AntT/setup && ./createDB.sh
```

### Populating the database with some **test** data

```bash
cd anttframework/AntT/setup && ./generateTestData.sh
```

### Importing and exporting data to the database

You can generate database table CSV templates from an empty database by typing the following command.

```bash
cd anttframework/AntT/setup && ./exportDB.sh
```

This generates CSV files in the `AntT/antt-data` directory. The same command can be used to export the database to the same CSV files.  If the files exist in the `AntT/antt-data` directory, the data can be imported using the following command.

```bash
cd anttframework/AntT/setup && ./importDB.sh
```

Alternatively, the Postgres database can be backedup using standard `pg_dump` options.

## Other

1. Will be linted with bootlint on every release (see https://github.com/twbs/bootlint)


<!--  LocalWords:  mvn AntT WebContent CSS JDK PostgreSQL README md
 -->
<!--  LocalWords:  webapps
 -->
