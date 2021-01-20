<?php

    $caso = intval($_GET['caso']);
    $paciente = intval($_GET['paciente']);
    $mysqli = new mysqli("localhost","root","","dbdif");

    $query = "UPDATE paciente SET caso = '$caso' WHERE id_paciente = '$paciente'";
    $result = mysqli_query($mysqli, $query);
    if($result == TRUE){
        echo 'true';
    } else {
        echo 'error';
    }
    mysqli_close($mysqli);
?>