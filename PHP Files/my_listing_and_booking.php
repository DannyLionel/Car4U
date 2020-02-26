<?php

	require "config.php";
	
	$id = $_POST["ID"];
    $response = array();
    $the_array = array();

	$sql = "SELECT make.name AS Make, model.model AS Model, CONCAT(users.first_name, ' ', users.last_name )AS Owner, cars.image_link AS Image, bookings.carid AS CarID, bookings.from_date AS From_Date, bookings.to_date AS To_Date, bookings.total_price AS Price FROM bookings LEFT JOIN cars ON cars.id = bookings.carid LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id LEFT JOIN users ON cars.owner_id = users.id WHERE user = '".$id."'";

    $result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$make = $row['Make'];
					$model = $row['Model'];
					$owner = $row['Owner']; 
 					$image = $row['Image'];
					$carID = $row['CarID']; 
 					$from_date = $row['From_Date']; 
					$to_date = $row['To_Date']; 
					$price = $row['Price'];
			
					$the_array[] = array('Make'=> $make,'Model'=> $model,'Owner'=> $owner, 'Image'=> $image, 'CarID'=> $carID, 'From_Date'=> $from_date, 'To_Date'=> $to_date, 'Price'=> $price);
				}
		
		$response['the_array'] = $the_array;
	    $fp = fopen('my_listing_and_booking_results.json', 'w');
		fwrite($fp, json_encode($response));
		fclose($fp);
		
		echo "success";
	}
	else {
		echo "error";
	}

 ?>