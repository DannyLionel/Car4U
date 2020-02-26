<?php

	require "config.php";

	$ID = $_POST["ID"];
	
    $response = array();
    $the_array = array();

	$sql = "SELECT cars.id, model.model, make.name AS make, users.first_name, users.last_name, cars.price, cars.image_link AS image_link FROM `cars` LEFT JOIN model ON cars.model_id = model.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN users ON cars.owner_id = users.id WHERE cars.id = '".$ID."'";

    $result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$car_id = $row['id'];
					$model = $row['model'];
					$make = $row['make']; 
					$first_name = $row['first_name']; 
					$last_name = $row['last_name'];
					$price = $row['price'];
					$image_link = $row['image_link'];
 
			
					$the_array[] = array('id'=> $car_id,'model'=> $model,'make'=> $make,'first_name'=> $first_name,'last_name'=> $last_name,'price'=> $price,'image_link'=> $image_link);
				}
		
		$response['the_array'] = $the_array;
	    $fp = fopen('booking_confirm_view_result.json', 'w');
		fwrite($fp, json_encode($response));
		fclose($fp);
		
		echo "success";
	}
	else {
		echo "error";
	}

 ?>