<?php
    $mysqli = new mysqli("localhost","root","","dbdif");

    $fecha = filter_input(INPUT_POST, "fecha");
    $hora = filter_input(INPUT_POST, "hora");
    $paciente = filter_input(INPUT_POST, "paciente");
    $usuario = filter_input(INPUT_POST, "usuario");

    $id_q = mysqli_query($mysqli, "SELECT id_cita FROM cita ORDER BY id_cita DESC LIMIT 1");
    $row = mysqli_fetch_array($id_q);
    $id = intval($row[0]);
    $id += 1;
    $insert = mysqli_query($mysqli, "INSERT INTO cita (id_cita, fecha, hora, paciente, usuario) VALUES ('$id', '$fecha', '$hora', '$paciente', '$usuario')");
    //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
    if ($insert == TRUE) {
        echo '1'; //se ingreso correctamente
    } else {
        echo '0'; //Error
    }
    mysqli_close($mysqli);
?>