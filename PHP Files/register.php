<?php

	require "config.php";
	
	$first_name = $_POST["first_name"];
	$last_name = $_POST["last_name"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$email = $_POST["email"];


	$sqla="SELECT * FROM users WHERE username='".$username."'";
    $resulta = $conn->query($sqla);
    if($resulta->num_rows == 0) {
	
		$sql="INSERT INTO users (first_name,last_name,username,password,email) VALUES ('".$first_name."','".$last_name."','".$username."','".$password."','".$email."')";

    	$result = $conn->query($sql);
  		if($result) {
		echo "success";
		}
		else {
		echo "error";
		}
	}
	else {
		echo "username already exists";
	}
 ?>

