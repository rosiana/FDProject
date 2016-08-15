<?php
$username = "root";
$password = "";
$host = "localhost";
$database = "fdproject";

$mysqli = new mysqli($host, $username, $password, $database);
$myArray = array();
if ($result = $mysqli->query("SELECT `lelang`.`id`, `lelang`.`nama`, `lelang`.`lpse`, `lelang`.`tahun`, `lelang`.`status`, `lelang`.`pagu`, `lelang`.`hps`, `lelang`.`penawaranmenang`, `lelang`.`pemenang`, `t1`.`periodelelang` FROM `lelang` join `t1` on `lelang`.`id` = `t1`.`lelangnum`")) {

    while($row = $result->fetch_array(MYSQLI_ASSOC)) {
        foreach($row as $key => $value){
            $row[$key] = utf8_encode($value);
        }
        array_push($myArray,$row);
    }
    var_dump($myArray);
    echo "<hr/>";
    echo json_encode($myArray, JSON_NUMERIC_CHECK);
    echo "<hr/>";
}

$result->close();
$mysqli->close();
?>
