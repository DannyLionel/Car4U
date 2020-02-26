<?php
	require "config.php";
	
	$make = $_POST["make"];
	$model = $_POST["model"];
	$location = $_POST["location"];
	$price = $_POST["price"];
	$userid = $_POST["ID"];
	$car_suv = $_POST["car_suv"];
	$num_seats = $_POST["num_seats"];
	$image_link = $_POST["image_link"];
	$sql = "Select * from make where name = '".$make."'";
	$result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$make_id = $row['id'];
				}
	}
	else {
		$make_id = 1;
		
		$sql = "Select * from make";
		$result = $conn->query($sql);
		if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$make_id = $make_id + 1;
				}
		}	
		$sql = "Insert into make Values ('".$make_id."','".$make."')";
		$result = $conn->query($sql);
	}
	$sql = "Select * from model where model.model = '".$model."'";
	$result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$model_id = $row['id'];
				}
	}
	else {
	
		$model_id = 1;
		$sql = "Select * from model";
		$result = $conn->query($sql);
		if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$model_id = $model_id + 1;
				}
		}	
		$sql = "Insert into model Values ('".$model_id."','".$make_id."','".$model."','".$num_seats."','".$car_suv."')";
		$result = $conn->query($sql);
	}
	$cars_id = "1";
	$sql = "Select * from cars";
	$result = $conn->query($sql);
	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$cars_id = $cars_id + 1;
				}
		}	
	
	$sql = "Insert into cars Values ('".$cars_id."','".$make_id."','".$model_id."','1','".$userid."','".$price."','".$image_link."')";
	$result = $conn->query($sql);
	echo "success";
 ?>