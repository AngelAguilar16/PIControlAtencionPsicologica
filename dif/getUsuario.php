<?php

   $mysqli = new mysqli("localhost","root","","dbdif");

   $result = mysqli_query($mysqli, "SELECT * FROM usuario");

   $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0){
        while($row = mysqli_fetch_assoc($result)){
            $response[] = $row;
        }
    }

    header('Content-Type: application/json');
    echo json_encode(array("Usuario"=>$response));
  

   mysqli_close($mysqli);
?>
?>