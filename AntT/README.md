
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


## Setup scripts

The following section describes a number of scripts that can be used
to create the database, build the source code, import data, export
data, and generate XML files for the website.

**NOTE** The following tasks require that the **USER** and **PASS**
  are set in the `/AntT/etc/sql/admin.py` file.


### Deleting the database

```bash
cd anttframework/AntT/setup && ./deleteDB.sh
```

### Creating the database

```bash
cd anttframework/AntT/setup && ./createDB.sh
```

### Building the source code

#### Using docker image

If you don't have the exact versions of Maven 3.6.3 and Java 8 (there
appear to be compatibility issues with Java >=11) installed, you can
use docker to run the builds.  For example,

```bash
docker run --rm -it -v $(pwd):/maven -w /maven maven:3.6.3-jdk-8 mvn clean verify
```

will clean, build, test, and generate a war file.

#### Maven 3.6.3 and Java 8 properly installed

Assuming that you have Maven 3.6.3 and Java 8 installed, the following
commands will work.


Use the following script to compile the Java source code.  You must
run this command before executing commands listed below.

```bash
cd anttframework/AntT/setup && ./buildClasses.sh
```

To build the WAR file, execute the command shown below. The WAR file
contains the entire web site.  While the site is built as a deployable
WAR file, there are no server dependencies that require the site to be
served by on a Java Servlet Container.  The unpacked contents of the
WAR file can be served by any web server, eg Apache.

```bash
cd anttframework/AntT/setup && ./buildWarFile.sh
```

### Populating the database with some **test data**

The following command populates an empty database with some **test
data**.  It ensures that every table in the database has at least
*some* data -- including images.

```bash
cd anttframework/AntT/setup && ./generateTestData.sh
```

### Importing and exporting data to the database

You can generate database table CSV templates from an empty database
by typing the following command.

```bash
cd anttframework/AntT/setup && ./exportDB.sh
```

This generates CSV files in the `AntT/antt-data` directory. The same
command can be used to export the database to the same CSV files.  If
the files exist in the `AntT/antt-data` directory, the data can be
imported using the following command.

```bash
cd anttframework/AntT/setup && ./importDB.sh
```

When importing data into the database using the `importDB.sh` option,
any CSV file in the `AntT/antt-data` directory will be processed.  The
`importDB.sh` script will attempt to append the data in the directory
by default.  If any of the data in the CSV files already exists in the
database, then the import transaction will be aborted.

Alternatively, the PostgreSQL database can be backed up using standard
`pg_dump` options.

## Building details

This section describes the build process in a little more detail.  You
should only need to refer to this section if there are problems using
the scripts from the previous section.

First, ensure that the database has been installed.  The `README.md`
file in the `./etc/sql` directory describes the process.

The project can be built by running Maven from this (AntT)
directory. Typing

```bash
mvn clean verify
```

will compile the source code, run all unit tests, and generate the web
archive file

```
AntT-0.0.1-SNAPSHOT.war
```

in the `target` directory.

To deploy the site contained by the war file to Tomcat, copy the war
file into the Tomcat `webapps` directory.

However, this assumes that the database has already been generated and
populated with data.  If this has not occurred, follow the following
directions.

## WebContent Directory

The contents of the WebContent directory is where all of the files
used by the web server are located.  The XML file `methodology.xml`
and `team.xml` can be modified with personal information, and they
will be automatically loaded and populated on their corresponding
pages.

Sample layouts utilize HTML5, Bootstrap, and AngularJS. The files
found in the `WebContent` directory may be modified for content and
style.  Content that is generated from data found in the PostgreSQL
database are placed in the `WebContent/generated` directory.

Each generated XML file is placed in the `WebContent/generated`
directory along with a corresponding XML Schema Definition (XSD) file.
The XSD files may be used to validate each generated XML file in the
`WebContent/generated` directory.

The `WebContent/generated/XMLSamples` directory contains the images
and thumbnails that are used by samples.  These are stored in the
`images` and `image_thumbnails` sub-directories.

## Other

1. Developers should use some linter with every release (see
   https://github.com/twbs/bootlint)


<!--  LocalWords:  mvn AntT WebContent CSS JDK PostgreSQL README md
 -->
<!--  LocalWords:  webapps AngularJS CSV cd anttframework antt py xml
 -->
<!--  LocalWords:  linter importDB XSD
 -->
