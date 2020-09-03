<?php

require_once __DIR__ . '/db_config.php';

$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
mysqli_set_charset($con, "utf8");

//check for post data
if(isset($_GET["note_id"])){
  $note_id = mysqli_real_escape_string($con, $_GET["note_id"]);

  $resultset = mysqli_query($con, "SELECT * FROM notes WHERE note_id = $note_id");

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
