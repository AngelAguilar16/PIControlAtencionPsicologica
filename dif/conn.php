<?php

    define('HOST', 'localhost');
    define('USER', 'root');
    define('PASS', '');
    define('DB', 'dbdif');

    $con = mysqli_connect(HOST,USER,PASS,DB) or die('GAGAL');

?>