<?php
require_once __DIR__ . '/db_config.php';

$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
mysqli_set_charset($con, "utf8");

//create current date and time
$creation_datetime = date("Y-m-d" . " " . "H:i:s");

//check for required fields
if(isset($_POST['content']) && isset($_POST['latitude']) && isset($_POST['longitude'])) {
  //get user POST Data
  $content = mysqli_real_escape_string($con, $_POST['content']);
  $lat = mysqli_real_escape_string($con, $_POST['latitude']);
  $lon = mysqli_real_escape_string($con, $_POST['longitude']);

  //mysql inserting new row
  $resultset = mysqli_query($con, "INSERT INTO notes(note_id, content, latitude, longitude, creation_datetime) VALUES(NULL, '$content', '$lat', '$lon', '$creation_datetime')");

  if($resultset) {
    //check if row inserted or note
    $response["success"] = 1;
    $response["message"] = "Note successfully created";
  }else{
    $response["success"] = 0;
    $response["message"] = "An error occured";
  }
}else{
  //required field missing
  $response["success"] = 0;
  $response["message"] = "Required field(s) is missing";
}

echo json_encode($response);
?>
