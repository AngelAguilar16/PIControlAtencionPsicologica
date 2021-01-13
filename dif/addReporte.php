<?php
    $mysqli = new mysqli("localhost","root","","dbdif");

    $usuario = filter_input(INPUT_POST, "usuario");
    $cita = filter_input(INPUT_POST, "cita");
    $caso = filter_input(INPUT_POST, "caso");
    $paciente = filter_input(INPUT_POST, "paciente");
    $motivo = filter_input(INPUT_POST, "motivo");
    $notas = filter_input(INPUT_POST, "notas");
    $tipo = filter_input(INPUT_POST, "t_consulta");
    $fecha = filter_input(INPUT_POST, "fecha");
    $hora = filter_input(INPUT_POST, "hora");


    $id_q = mysqli_query($mysqli, "SELECT id_consulta FROM consulta ORDER BY id_consulta DESC LIMIT 1");
    $row = mysqli_fetch_array($id_q);
    $id = intval($row[0]);
    $id += 1;
    $insert = mysqli_query($mysqli, "INSERT INTO consulta (id_consulta, usuario, cita, caso, paciente, fecha, hora, motivo_atencion, notas_sesion, tipo_consulta) VALUES 
                            ('$id','$usuario', '$cita', '$caso', '$paciente', '$fecha', '$hora', '$motivo', '$notas', '$tipo')");
    //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
    if ($insert == TRUE) {
        echo '1'; //se ingreso correctamente
    } else {
        echo '0'; //Error
    }
    mysqli_close($mysqli);
?>