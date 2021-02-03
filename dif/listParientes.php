<?php

    $mysqli = new mysqli("localhost","id15894422_root","PI5toSeme???","id15894422_dbdif");
    $paciente = intval($_GET['paciente']);

    $query = "SELECT * FROM parientes WHERE id_paciente = '". $paciente ."'";

    $result = mysqli_query($mysqli, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0){
        while($row = mysqli_fetch_assoc($result)){
            $response[] = $row;
        }
    }

    header('Content-Type: application/json');
    echo json_encode(array("parientesList"=>$response));
    mysqli_close($mysqli);

?>
