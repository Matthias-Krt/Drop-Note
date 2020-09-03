<?php

require_once __DIR__ . '/db_config.php';

$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
mysqli_set_charset($con, "utf8");

//check for post data
if(isset($_GET["latitude"]) && isset($_GET["longitude"]) && isset($_GET("[radius]"))){
  $lat = mysqli_real_escape_string($con, $_GET["latitude"]);
  $lon = mysqli_real_escape_string($con, $_GET["longitude"]);
  $entf = mysqli_real_escape_string($con, $_GET["radius"]);

  //Calculation
  $r_earth = 6371000; //ca. 6371km in m

  $new_lat_east;
  $new_lon_west;
  $new_lat_north;
  $new_lon_south;

  //Calculate new Coordinates
  $new_lat_east = $lat + ($entf / $r_earth) * (180 / M_PI);
  $new_lat_north = $lat + (((-1) * $entf) / $r_earth) * (180 / M_PI);

  $new_lon_west = $lon + ($entf / $r_earth) * (180 / M_PI) / cos($new_lat_east * M_PI/180);
  $new_lon_south = $lon + (((-1) * $entf) / $r_earth) * (180 / M_PI) / cos($new_lat_east * M_PI/180);

  $resultset = mysqli_query($con, "SELECT * FROM notes WHERE latitude BETWEEN $new_latitude_west AND $new_latitude_east AND longitude BETWEEN $new_longitude_north AND $new_longitude_south");

  //check for empty result
  if(!empty($resultset)){
    if(mysqli_num_rows($resultset) > 0){

      $result = mysqli_fetch_array($resultset);

      $note = array();
      $note["note_id"] = $result["note_id"];
      $note["content"] = $result["content"];
      $note["latitude"] = $result["latitude"];
      $note["longitude"] = $result["longitude"];
      $note["creation_datetime"] = $result["creation_datetime"];

      $response["success"] = 1;

      $respond["note"] = array();
      array_push($respond["note"], $note);
      //echo json_encode($respond);

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
  $response["message"] = "Required field(s) missing";
}

echo json_encode($response);

if($response["success"] >= 1){
  //check for success
  echo json_encode($respond);
}

 ?>
