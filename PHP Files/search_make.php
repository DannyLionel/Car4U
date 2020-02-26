<?php

	require "config.php";
	
    $response = array();
    $the_array = array();
	

	$sql="SELECT * FROM make";
    $result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$id = $row['id'];
					$name = $row['name'];
					$the_array[] = array('id'=> $id,'name'=> $name);
				}
		
		$response['the_array'] = $the_array;
		$fp = fopen('make_results.json', 'w');
		fwrite($fp, json_encode($response));
		fclose($fp);
		
		echo "success";
		
	}
	else {
		echo "error";
	}

 ?>