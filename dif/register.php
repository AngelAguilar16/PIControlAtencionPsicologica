<?php
    $email = filter_input(INPUT_POST, "correo");
    $nombre = filter_input(INPUT_POST, "nombre");
    $ap = filter_input(INPUT_POST, "ap");
    $am = filter_input(INPUT_POST, "am");
    $pass = filter_input(INPUT_POST, "password");
    $t_usuario = filter_input(INPUT_POST, "t_usuario");

    $mysqli = new mysqli("localhost","root","","dbdif");


    $result = mysqli_query($mysqli, "SELECT * FROM usuario WHERE correo LIKE '$email'");

    if (mysqli_num_rows($result) >= 1) {
        echo '0'; //ya existe
    } else {
        $id_q = mysqli_query($mysqli, "SELECT id_usuario FROM usuario ORDER BY id_usuario DESC LIMIT 1");
        $row = mysqli_fetch_array($id_q);
        $id = intval($row[0]);
        $id += 1;
        $insert = mysqli_query($mysqli, "INSERT INTO usuario VALUES ('$id', '$nombre', '$ap', '$am', '$email', '$pass', '$t_usuario')");
        //$insert = mysqli_query($mysqli, "INSERT INTO usuario (id_usuario, nombres, correo, password, tipo_usuario) VALUES ( '3', 'Daniel Montes de Oca' , 'danymontes00@hotmail.com', '123', 'Psicología')");
        if ($insert == TRUE) {
            echo $id; //se ingreso correctamente
        } else {
            echo 'false'; //Error
        }
    }
    mysqli_close($mysqli);
?>
