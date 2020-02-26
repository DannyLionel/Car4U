<?php

	require "config.php";

	//$count = 0;
	
	$id = $_POST["id"];
	$car_id = $_POST["Car_ID"];
	$from_date = $_POST["fromdate"];
	$to_date = $_POST["todate"];
	$total_price = $_POST["total_price"];

	$sql="SELECT * FROM bookings";
    $result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
			
			$count = $count + 1;
		}
	}

	$count = $count + 1;
		
	$sql = "INSERT INTO bookings VALUES ('".$count."','".$car_id."','".$id."','".$from_date."','".$to_date."','".$total_price."')";
	if ($conn->query($sql) === TRUE) {
    	echo "suc";
	} else {
    	echo "error";
	}


	$sql = "UPDATE cars SET avaliable = 0 WHERE id = '".$car_id."'";
	if ($conn->query($sql) === TRUE) {
    	echo "cess";
	} else {
    	echo "error";
	}
?>
