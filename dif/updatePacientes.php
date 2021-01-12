<?php
    $id = $_POST['id'];
    $telefono = $_POST["telefono"];
    $estado = $_POST["estado"];
    $municipio = $_POST["municipio"];
    $domicilio = $_POST["domicilio"];
    $sexo = $_POST["sexo"];
    $fecha_nacimiento = $_POST["fecNac"];
    $estado_civil = $_POST["estCiv"];
    $escolaridad = $_POST["escolaridad"];
    $ocupacion = $_POST["ocupacion"];

    $mysqli = new mysqli("localhost","root","","dbdif");

    $query = "UPDATE paciente SET telefono = '$telefono', estado = '$estado', municipio = '$municipio', domicilio = '$domicilio', sexo = '$sexo', fecha_nacimiento = '$fecha_nacimiento', estado_civil = '$estado_civil', escolaridad = '$escolaridad', ocupacion = '$ocupacion' WHERE id_paciente = '$id' ";
    $result = mysqli_query($mysqli, $query);

    mysqli_close($mysqli);