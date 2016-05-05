<?php
/**
 * Created by IntelliJ IDEA.
 * User: Rosiana
 * Date: 5/4/2016
 * Time: 10:26 PM
 */

  $q=$_GET["q"];
  $dbuser="root";
  $dbname="fdproject";
  $dbpass="";
  $dbserver="localhost";

$server = mysql_connect($dbserver, $dbuser, $dbpass);
$connection = mysql_select_db($dbname, $server);

$myquery = "
SELECT  `id`, `periodelelang` FROM  `t1`
";
$query = mysql_query($myquery);

if ( ! $query ) {
    echo mysql_error();
    die;
}

$data = array();

for ($x = 0; $x < mysql_num_rows($query); $x++) {
    $data[] = mysql_fetch_assoc($query);
}

echo json_encode($data);

mysql_close($server);
?>