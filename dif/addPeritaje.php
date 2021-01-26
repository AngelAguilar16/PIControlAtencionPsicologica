<?php
    $mysqli = new mysqli("localhost","root","","dbdif");

    $usuario = filter_input(INPUT_POST, "usuario");
    $paciente = filter_input(INPUT_POST, "paciente");
    $motivo = filter_input(INPUT_POST, "motivo");
    $notas = filter_input(INPUT_POST, "notas");
    $fecha = filter_input(INPUT_POST, "fecha");
    $hora = filter_input(INPUT_POST, "hora");


    $id_q = mysqli_query($mysqli, "SELECT id_peritaje FROM peritaje ORDER BY id_peritaje DESC LIMIT 1");
    $row = mysqli_fetch_array($id_q);
    $id = intval($row[0]);
    $id += 1;
    $insert = mysqli_query($mysqli, "INSERT INTO peritaje (id_peritaje, usuario, paciente, fecha, hora, motivo_atencion, notas_sesion) VALUES 
                            ('$id','$usuario', '$paciente', '$fecha', '$hora', '$motivo', '$notas')");
    //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
    if ($insert == TRUE) {
        echo '1'; //se ingreso correctamente
    } else {
        echo '0'; //Error
    }
    mysqli_close($mysqli);
?>