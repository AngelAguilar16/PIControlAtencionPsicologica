<?php
$email = filter_input(INPUT_POST, "correo");
$pass = filter_input(INPUT_POST, "password");

$mysqli = new mysqli("localhost","root","","dbdif");

$result = mysqli_query($mysqli, "SELECT * FROM usuario WHERE correo = '". $email . "' AND password = '". $pass ."'");

if ($data = mysqli_fetch_array($result)) {
    echo '1';
} else {
    echo '0';
}

mysqli_close($mysqli);
?>