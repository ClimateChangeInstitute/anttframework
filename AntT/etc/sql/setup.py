#!/usr/bin/python

from subprocess import check_call, CalledProcessError
from sys import stderr
import sys
import textwrap

from properties import SQL_DATA, DATABASE, SQL_SCHEMA, \
    SQL_BACKUP_FILE, ADMIN, ADMIN_PASS


CREATE = "create"
SCHEMAONLY = "schema"
RESTORE = "restore"

def main() :

    if(len(sys.argv) < 2) or (sys.argv[1] != CREATE and sys.argv[1] != RESTORE and sys.argv[1] != SCHEMAONLY):
        stderr.write(textwrap.dedent("""\
                   usage: ./setup.py option
                     option: Either '%s', '%s', or '%s'.
                         - %s: An empty DB is created.
                         - %s: An empty DB is created and initialized with data using %s file.
                         - %s: A DB is created using %s file.
                   """ % (CREATE, SCHEMAONLY, RESTORE, SCHEMAONLY, CREATE, SQL_DATA, RESTORE, SQL_BACKUP_FILE)))
        exit(1)

    progOption = sys.argv[1]

    print textwrap.dedent("""\
                ********
                Warning: Installation user must have access to all files in the current directory.
                Run as postgres from /tmp if you don't want to modify your permissions.
                Alternatively, make sure the current user is a postres super user.
                ********""")


    print "Creating the " + DATABASE + " db ..."
    
    try :
    
        check_call(["createdb", DATABASE])

        # First create the admin then have him create related schema, etc.
        check_call(["psql", "-c", "CREATE USER %s WITH CREATEDB password '%s'" % (ADMIN, ADMIN_PASS)])

        if progOption == CREATE:
            check_call(["psql", DATABASE, "-f", SQL_SCHEMA, "-v", "ADMIN=%s" % (ADMIN)])
            check_call(["psql", DATABASE, "-f", SQL_DATA])  # Default users and data
        elif progOption == RESTORE:
            check_call(["psql", DATABASE, "-f", SQL_BACKUP_FILE])
        elif progOption == SCHEMAONLY:
            check_call(["psql", DATABASE, "-f", SQL_SCHEMA, "-v", "ADMIN=%s" % (ADMIN)])
        else:
            stderr.write("The program option was not recognized.\n")
            
    except CalledProcessError :
        stderr.write(textwrap.dedent("""\
        Something went wrong. Are you sure you have the proper permissions?
        Perhaps the current user is not a DB super user?\n"""))
        exit(1)


if __name__ == "__main__":
    main()
