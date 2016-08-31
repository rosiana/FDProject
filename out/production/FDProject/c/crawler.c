#include <stdio.h>
#include <windows.h>
#include <mysql.h>
#pragma comment(lib, "libmysql.lib")

MYSQL *conn;
int version = 1;

int main ( int argc, char *argv[] )
{
    conn = mysql_init ( NULL );
    mysql_real_connect ( conn, "localhost", "root",
            "password", "test", 3308, NULL, 0 );
	version = mysql_get_server_version( conn );
	printf("\nMySQL Version = %d\n",version);
    mysql_close ( conn );
    return 0;
}
