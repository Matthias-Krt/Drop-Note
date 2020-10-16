<?php

require_once __DIR__ . '/db_config.php';

$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
mysqli_set_charset($con, "utf8");

//check for post data
if(isset($_GET["latitude"]) && isset($_GET["longitude"]) && isset($_GET["radius"])){
  $lat = mysqli_real_escape_string($con, $_GET["latitude"]);
  $lon = mysqli_real_escape_string($con, $_GET["longitude"]);
  $radius = mysqli_real_escape_string($con, $_GET["radius"]);

  //Calculation
  $r_earth = 6371000; //ca. 6371km in m

  //Calculate new Coordinates
  $new_lat_north = $lat + ($radius / $r_earth) * (180 / M_PI);
  $new_lat_south = $lat - ($radius / $r_earth) * (180 / M_PI);

  $new_lon_east = $lon + ($radius / $r_earth) * (180 / M_PI) / cos($new_lat_north * M_PI / 180);
  $new_lon_west = $lon - ($radius / $r_earth) * (180 / M_PI) / cos($new_lat_south * M_PI / 180);

  $resultset = mysqli_query($con, "SELECT * FROM notes WHERE latitude BETWEEN $new_lat_south AND $new_lat_north AND longitude BETWEEN $new_lon_west AND $new_lon_east");

  //check for empty result
  if(!empty($resultset)){
    if(mysqli_num_rows($resultset) > 0){

      $result = array();
      while ($row = mysqli_fetch_assoc($resultset)) {
        $result[] = $row;
      }

      $response["success"] = 1;
      $respond["note"] = $result;
    }else{
      //no note found
      $response["success"] = 0;
      $response["message"] = "No note found";
    }
  }else{
    //no note found
    $response["success"] = 0;
    $response["message"] = "No note found";
  }
}else{
  //required field missing
  $response["success"] = 0;
  if (!isset($_GET["latitude"])) {
    $response["message"] = "Required field(s) missing (Lat)";
  }else if(!isset($_GET["longitude"])) {
    $response["message"] = "Required field(s) missing (Lon)";
  }else if (!isset($_GET["radius"])) {
    $response["message"] = "Required field(s) missing (Rad)";
  }
}

echo json_encode($response);

if($response["success"] >= 1){
  //check for success
  echo json_encode($respond);
}

 ?>
