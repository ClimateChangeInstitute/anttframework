#!/usr/bin/python

import getpass
from subprocess import check_call, CalledProcessError
from sys import stderr

from properties import DATABASE, ADMIN


def main():

    if(getpass.getuser() != "postgres"):
        stderr.write("It may not work if you are not the postgres user\n")

    print """
******************************************************************************
WARNING !!!!
******************************************************************************
Deleting the %s Database.
""" % DATABASE

    ans = raw_input("Are you absolutely sure you want to continue (y/N)?")
    
    if ans.lower() != 'y' :
        exit(0)
    
    print """
******************************************************************************
Deleting the %s database and removing the %s user account.
******************************************************************************
""" % (DATABASE, ADMIN)

    try :
        check_call(["dropdb", DATABASE])
        check_call(["dropuser", ADMIN])
    except CalledProcessError :
        stderr.write("Something went wrong.  Are you sure you have the proper permissions?\n")
        exit(1)

if __name__ == "__main__":
    main()
