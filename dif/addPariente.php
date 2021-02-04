<?php
    $mysqli = new mysqli("localhost","id15894422_root","PI5toSeme???","id15894422_dbdif");

    $idPacienteIn = filter_input(INPUT_POST, "idPacienteInicial");
    $tipo = filter_input(INPUT_POST, "tipo");
    $idPaciente = filter_input(INPUT_POST, "idPaciente");

    $id_q = mysqli_query($mysqli, "SELECT id FROM parientes ORDER BY id DESC LIMIT 1");
    $row = mysqli_fetch_array($id_q);
    $id = intval($row[0]);
    $id += 1;
    $insert = mysqli_query($mysqli, "INSERT INTO parientes (id, id_paciente_1, tipo, id_paciente) VALUES ('$id', '$idPacienteIn', '$tipo', '$idPaciente')");
    //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
    if ($insert == TRUE) {
        echo '1'; //se ingreso correctamente
    } else {
        echo '0'; //Error
    }
    mysqli_close($mysqli);
?>