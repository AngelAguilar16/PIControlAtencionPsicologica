<?php
    $fecha_registro = filter_input(INPUT_POST, "fecha_registro");
    $nombres = filter_input(INPUT_POST, "nombres");
    $ap = filter_input(INPUT_POST, "ap");
    $am = filter_input(INPUT_POST, "am");
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

    $id_q = mysqli_query($mysqli, "SELECT id_paciente FROM paciente ORDER BY id_paciente DESC LIMIT 1");
    $row = mysqli_fetch_array($id_q);
    $id = intval($row[0]);
    $id += 1;
    $insert = mysqli_query($mysqli, "INSERT INTO paciente(id_paciente, fecha_registro, nombre, ap, am, menor_de_edad, telefono, estado, municipio, calle, sexo, fecha_nacimiento, estado_civil, escolaridad, ocupacion)
    VALUES ('$id', '$fecha_registro', '$nombres', '$ap', '$am', 0, '$telefono', '$estado', '$municipio', '$domicilio', '$sexo', '$fecha_nacimiento', '$estado_civil', '$escolaridad', '$ocupacion')");

    if ($insert == TRUE) {
        echo '1'; //se ingreso correctamente
    } else {
        echo '2'; //Error
    }

    mysqli_close($mysqli);
?>
