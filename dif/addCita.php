<?php
    include_once("conn.php");

    $fecha = filter_input(INPUT_POST, "fecha");
    $hora = filter_input(INPUT_POST, "hora");
    $paciente = filter_input(INPUT_POST, "paciente");
    $nombre = filter_input(INPUT_POST, "nombre");
    $usuario = filter_input(INPUT_POST, "usuario");
    $asistio = filter_input(INPUT_POST, "asistio");



    $result = mysqli_query($con, "SELECT * FROM cita WHERE nombre LIKE '$nombre'");

    if (mysqli_num_rows($result) >= 1) {
        echo '0'; //ya existe
    } else {
        $id_q = mysqli_query($mysqli, "SELECT id_cita FROM cita ORDER BY id_cita DESC LIMIT 1");
        $row = mysqli_fetch_array($id_q);
        $id = intval($row[0]);
        $id += 1;
        $insert = mysqli_query($mysqli, "INSERT INTO paciente VALUES ('$id', '$fecha', '$hora', '$paciente', '$nombre', '$usuario', '$asistio' ");
        //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
        if ($insert == TRUE) {
            echo '1'; //se ingreso correctamente
        } else {
            echo '2'; //Error
        }
    }
    mysqli_close($mysqli);
?>