<?php

	require "config.php";
	
	$id = $_POST["ID"];
    $response = array();
    $the_array = array();

	$sql = "SELECT cars.make_id AS Make_ID,cars.model_id AS Model_ID, model.num_seats AS Seats, make.name AS Make, model.model AS Model, CONCAT(users.first_name, ' ', users.last_name )AS Owner, cars.image_link AS Image, cars.id AS CarID,  cars.price AS Price FROM cars LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id LEFT JOIN users ON cars.owner_id = users.id WHERE cars.owner_id = '".$id."'";

    $result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$make_id = $row['Make_ID'];
					$model_id = $row['Model_ID'];
					$make = $row['Make'];
					$model = $row['Model'];
					$owner = $row['Owner']; 
 					$image = $row['Image'];
					$carID = $row['CarID']; 
					$seats = $row['Seats']; 
					$price = $row['Price'];
			
					$the_array[] = array('Make_ID'=> $make_id,'Model_ID'=> $model_id,'Make'=> $make,'Model'=> $model,'Owner'=> $owner, 'Image'=> $image, 'CarID'=> $carID, 'Seats'=> $seats, 'Price'=> $price);
				}
		
		$response['the_array'] = $the_array;
	    $fp = fopen('my_listing_and_booking_results1.json', 'w');
		fwrite($fp, json_encode($response));
		fclose($fp);
		
		echo "success";
	}
	else {
		echo "error";
	}

 ?>