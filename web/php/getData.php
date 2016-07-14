<?php
    $username = "root";
    $password = "";
    $host = "localhost";
    $database = "fdproject";

    $mysqli = new mysqli($host, $username, $password, $database);
    $myArray = array();
    if ($result = $mysqli->query("SELECT `lelangnum`, `periodelelang` FROM `t1`")) {

        while($row = $result->fetch_array(MYSQLI_ASSOC)) {
            $myArray[] = $row;
        }
        echo json_encode($myArray, JSON_NUMERIC_CHECK);
    }

    $result->close();
    $mysqli->close();
?>