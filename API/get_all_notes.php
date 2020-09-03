<?php

require_once __DIR__ . '/db_config.php';

$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
mysqli_set_charset($con, "utf8");

$resultset = mysqli_query($con, "SELECT * FROM notes");

$json_arr = array();
while($row = mysqli_fetch_assoc($resultset)){
  $json_arr[] = $row;
}

echo json_encode($json_arr);

 ?>
