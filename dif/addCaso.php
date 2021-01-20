<?php
    $mysqli = new mysqli("localhost","root","","dbdif");

    $fecha = filter_input(INPUT_POST, "fecha");
    $descripcion = filter_input(INPUT_POST, "descripcion");

    $id_q = mysqli_query($mysqli, "SELECT id_caso FROM caso ORDER BY id_caso DESC LIMIT 1");
    $row = mysqli_fetch_array($id_q);
    $id = intval($row[0]);
    $id += 1;
    $insert = mysqli_query($mysqli, "INSERT INTO caso (id_caso, fecha_apertura, descripcion_general) VALUES ('$id', '$fecha', '$descripcion')");
    //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
    if ($insert == TRUE) {
        echo $id; //se ingreso correctamente
    } else {
        echo 'error'; //Error
    }
    mysqli_close($mysqli);
?>