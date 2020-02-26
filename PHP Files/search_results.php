<?php

	require "config.php";

	$ID = $_POST["ID"];
	$first_name = $_POST["first_name"];
	$last_name = $_POST["last_name"];
	$username = $_POST["username"];
	$email = $_POST["email"];
	$makeid = $_POST["makeid"];
	$modelid = $_POST["modelid"];
	//$fromdate = $_POST["fromdate"];
	//$todate = $_POST["todate"];
	$location = $_POST["location"];
	$price = $_POST["price"];
	$suv_or_not = $_POST["suv_or_not"];
	$num_seats = $_POST["num_seats"];
	
    $response = array();
    $the_array = array();


if ($num_seats == '9999') {
	
	if ($suv_or_not == '9999') {

	if ($makeid == '9999') {
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1' AND owner_id != '".$ID."' AND cars.price <= ".$price; 
	}
	
		else {
		
		if ($modelid == '9999') {
			
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."'  AND cars.make_id = ".$makeid." AND cars.price <= ".$price; 
		}
		
		else {
			
			$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."' AND cars.make_id = ".$makeid." AND cars.model_id =".$modelid."AND cars.price <= ".$price; 
		}
	}
	
}
	
	elseif ($suv_or_not == '1') {
		
		
		if ($makeid == '9999') {
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 1 AND cars.price <= ".$price; 
			
		}
	
		else {
		
		if ($modelid == '9999') {
			
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 1 AND cars.make_id = ".$makeid." AND cars.price <= ".$price; 
		}
		
		else {
			
			$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 1 AND cars.make_id = ".$makeid." AND cars.model_id =".$modelid."AND cars.price <= ".$price; 
		}
	}
	
	}


else {
		
		
		if ($makeid == '9999') {
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 0 AND cars.price <= ".$price; 
		}
	
		else {
		
		if ($modelid == '9999') {
			
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 0 AND cars.make_id = ".$makeid." AND cars.price <= ".$price; 
		}
		
		else {
			
			$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 0 AND cars.make_id = ".$makeid." AND cars.model_id =".$modelid."AND cars.price <= ".$price; 
		}
	}
	
	}
	
}
//-------------------------
	else {



if ($suv_or_not == '9999') {

	if ($makeid == '9999') {
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND  AND owner_id != '".$ID."' avaliable = '1' AND cars.price <= ".$price; 
	}
	
		else {
		
		if ($modelid == '9999') {
			
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."' AND cars.make_id = ".$makeid." AND cars.price <= ".$price; 
		}
		
		else {
			
			$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."'  AND cars.make_id = ".$makeid." AND cars.model_id =".$modelid."AND cars.price <= ".$price; 
		}
	}
	
}
	
	elseif ($suv_or_not == '1') {
		
		
		if ($makeid == '9999') {
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 1 AND cars.price <= ".$price; 
			
		}
	
		else {
		
		if ($modelid == '9999') {
			
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 1 AND cars.make_id = ".$makeid." AND cars.price <= ".$price; 
		}
		
		else {
			
			$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 1 AND cars.make_id = ".$makeid." AND cars.model_id =".$modelid."AND cars.price <= ".$price; 
		}
	}
	
	}


else {
		
		
		if ($makeid == '9999') {
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 0 AND cars.price <= ".$price; 
		}
	
		else {
		
		if ($modelid == '9999') {
			
		$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 0 AND cars.make_id = ".$makeid." AND cars.price <= ".$price; 
		}
		
		else {
			
			$sql="SELECT cars.make_id AS MakeID, cars.model_id AS ModelID, cars.id AS CarID, make.name AS Make, model.model AS Model, CONCAT(users.first_name,' ', users.last_name) As Owner, cars.price AS Price, cars.image_link AS Image, model.num_seats AS Seats FROM cars LEFT JOIN users ON cars.owner_id = users.id LEFT JOIN make ON cars.make_id = make.id LEFT JOIN model ON cars.model_id = model.id WHERE model.num_seats = ".$num_seats." AND avaliable = '1'  AND owner_id != '".$ID."' AND model.suv = 0 AND cars.make_id = ".$makeid." AND cars.model_id =".$modelid."AND cars.price <= ".$price; 
		}
	}
	
	}
	}
		
    $result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$make_id = $row['MakeID'];
					$model_id = $row['ModelID'];
					$car_id = $row['CarID']; 
 					$make = $row['Make'];
					$model = $row['Model']; 
 					$owner = $row['Owner']; 
					$price = $row['Price']; 
 					$image = $row['Image']; 
					$seats = $row['Seats']; 
			
					$the_array[] = array('MakeID'=> $make_id,'ModelID'=> $model_id,'CarID'=> $car_id, 'Make'=> $make, 'Model'=> $model, 'Owner'=> $owner, 'Price'=> $price, 'Image'=> $image, 'Seats'=> $seats );
				}
		
		$response['the_array'] = $the_array;
	    $fp = fopen('search_results.json', 'w');
		fwrite($fp, json_encode($response));
		fclose($fp);
		
		echo "success";
	}
	else {
		echo "error";
	}

 ?>