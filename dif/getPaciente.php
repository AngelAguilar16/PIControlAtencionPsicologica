<?php

    $mysqli = new mysqli("localhost","root","","dbdif");
    $id_paciente = intval($_GET['id_paciente']);

    $query = "SELECT * FROM paciente WHERE id_paciente = '". $id_paciente ."'";

    $result = mysqli_query($mysqli, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0){
        while($row = mysqli_fetch_assoc($result)){
            $response[] = $row;
        }
    }

    header('Content-Type: application/json');
    echo json_encode(array("pacientesList"=>$response));
    mysqli_close($mysqli);

?>