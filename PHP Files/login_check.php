<?php

	require "config.php";
	
	$username = $_POST["user"];
	$password = $_POST["pass"];
	   
	$sql="SELECT * FROM users WHERE username = '".$username."' AND password = '".$password."'";
    $result = $conn->query($sql);
  	
	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$user_id = $row['id']; 
 					$first_name = $row['first_name'];
					$last_name = $row['last_name']; 
 					$username_d = $row['username']; 
					$email = $row['email']; 
			
					$the_array[] = array('id'=> $user_id, 'first_name'=> $first_name, 'last_name'=> $last_name, 'username'=> $username_d, 'email'=> $email );
				}
		
		$response['the_array'] = $the_array;
		$fp = fopen('login_results.json', 'w');
		fwrite($fp, json_encode($response));
		fclose($fp);
		
		echo "success";
	}
	else {
		
		echo "error";
	}

 ?>