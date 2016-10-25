# Default values for the web site setup.

from admin import THE_ADMIN, THE_ADIM_PASS

DATABASE = "antt"
ADMIN = THE_ADMIN # Set in admin.py file
ADMIN_PASS = THE_ADIM_PASS # Set in admin.py file

SQL_SCHEMA = "setup-schema.sql"
SQL_DATA = "setup-data.sql"  # Not to be confused with dump file data
SQL_BACKUP_FILE = "antt-backup.psql" # Backup file with schema information
