<?php 

    include_once("conn.php");
    $query = "SELECT * FROM `cita` ";

    $result = mysqli_query($con, $query);
    $number_of_rows = mysqli_num_rows($result);

    $response = array();

    if($number_of_rows > 0){
        while($row = mysqli_fetch_assoc($result)){
            $response[] = $row;
        }
    }

    header('Content-Type: application/json');
    echo json_encode(array("citasList"=>$response));
    mysqli_close($con);

?>