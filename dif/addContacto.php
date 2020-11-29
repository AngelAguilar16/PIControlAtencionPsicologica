<?php
    $fecha_registro = filter_input(INPUT_POST, "fecha_registro");
    $nombres = filter_input(INPUT_POST, "nombres");
    $nombre_pmt = filter_input(INPUT_POST, "nombre_pmt");
    $telefono = filter_input(INPUT_POST, "telefono");
    $estado = filter_input(INPUT_POST, "estado");
    $municipio = filter_input(INPUT_POST, "municipio");
    $domicilio = filter_input(INPUT_POST, "domicilio");
    $sexo = filter_input(INPUT_POST, "sexo");
    $fecha_nacimiento = filter_input(INPUT_POST, "fecha_nacimiento");
    $estado_civil = filter_input(INPUT_POST, "estado_civil");
    $escolaridad = filter_input(INPUT_POST, "escolaridad");
    $ocupacion = filter_input(INPUT_POST, "ocupacion");

    $mysqli = new mysqli("localhost","root","","dbdif");


    $result = mysqli_query($mysqli, "SELECT * FROM paciente WHERE nombres LIKE '$nombres'");

    if (mysqli_num_rows($result) >= 1) {
        echo '0'; //ya existe
    } else {
        $id_q = mysqli_query($mysqli, "SELECT id_paciente FROM paciente ORDER BY id_paciente DESC LIMIT 1");
        $row = mysqli_fetch_array($id_q);
        $id = intval($row[0]);
        $id += 1;
        $insert = mysqli_query($mysqli, "INSERT INTO paciente VALUES ('$id', '$fecha_registro', '$nombres', '$nombre_pmt', '$telefono', '$estado', '$municipio', '$domicilio', '$sexo', '$fecha_nacimiento', '$estado_civil', '$escolaridad', '$ocupacion'");
        //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
        if ($insert == TRUE) {
            echo '1'; //se ingreso correctamente
        } else {
            echo '2'; //Error
        }
    }
    mysqli_close($mysqli);
?>