#!/usr/bin/python

import getpass
import os
from subprocess import check_call, CalledProcessError
from sys import stderr
import sys
import textwrap

from properties import SQL_DATA, DATABASE, SQL_SCHEMA, \
    SQL_BACKUP_FILE, ADMIN, ADMIN_PASS


CREATE = "create"
RESTORE = "restore"

def main() :

    if(len(sys.argv) < 2) or (sys.argv[1] != CREATE and sys.argv[1] != RESTORE):
        stderr.write(textwrap.dedent("""\
                   usage: ./setup.py option
                     option: Either '%s' or '%s'.
                         - %s: An empty DB is created.
                         - %s: A DB is created using %s file.
                   """ % (CREATE, RESTORE, CREATE, RESTORE, SQL_BACKUP_FILE)))
        exit(1)

    progOption = sys.argv[1]

    if(getpass.getuser() != "postgres"):
        textwrap.dedent("""\
                ********
                Warning: Not postgres user.  May not be able to create the db.
                ********""")

    print textwrap.dedent("""\
                ********
                Warning: Installation user must have access to all files in current directory.
                Run as postgres from /tmp if you don't want to modify your permissions.
                ********""")


    print "Creating the " + DATABASE + " db ..."
    
    try :
    
        check_call(["createdb", DATABASE])

        # First create the admin then have him create related schema, etc.
        check_call(["psql", "-c", "CREATE USER %s WITH CREATEDB password '%s'" % (ADMIN, ADMIN_PASS)])

        if progOption == CREATE:
            check_call(["psql", DATABASE, "-f", SQL_SCHEMA, "-v", "ADMIN=%s" % (ADMIN)])
            check_call(["psql", DATABASE, "-f", SQL_DATA])  # Default users and data
        else :
            check_call(["psql", DATABASE, "-f", SQL_BACKUP_FILE])
            
    except CalledProcessError :
        stderr.write("Something went wrong. Are you sure you have the proper permissions?\n")
        exit(1)


if __name__ == "__main__":
    main()
