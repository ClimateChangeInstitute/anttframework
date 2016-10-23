
# /etc/sql Folder

The files in this directory are related to database setup, restoration, and 
destruction.  To change default settings (database name, database backup file), 
modify the preferences.py file. The administrator name and password will be 
kept in a special file that is not under git control.

The commands must be executed as the PostgreSQL user or a database user with 
creation privileges.

To install from scratch type

./setup.py create

This will create an empty database.

To remove all database contents, type

./teardown.py

If you already have a database backup created, you can recreate the database 
by typing 

./setup.py restore

which will attempt to recreate the backup database specified in properties.py 