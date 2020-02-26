<?php

	require "config.php";
	
	$make = $_POST["make"];
	$suv_or_not = $_POST["suv_or_not"];

    $response = array();
    $the_array = array();
	
	if ($suv_or_not == '9999'){
		
			if ($make == '9999') {
				$sql="SELECT * FROM model";}
			else {
				$sql="SELECT * FROM model WHERE make_id = '".$make."'";
			}
	}

	elseif ($suv_or_not == '0'){
	
					if ($make == '9999') {
						$sql="SELECT * FROM model WHERE suv = 0";}
					else {
						$sql="SELECT * FROM model WHERE make_id = '".$make."' AND suv = 0";
					}
		
	}

else {
						if ($make == '9999') {
						$sql="SELECT * FROM model WHERE suv = 1";}
					else {
						$sql="SELECT * FROM model WHERE make_id = '".$make."' AND suv = 1";
					}
	
	
}
	


    $result = $conn->query($sql);
  	if($result->num_rows > 0) {
		while($row = mysqli_fetch_array ($result)) {
					$id = $row['id'];
					$model = $row['model'];
					$the_array[] = array('id'=> $id,'model'=> $model);
				}
		
		$response['the_array'] = $the_array;
		$fp = fopen('model_results.json', 'w');
		fwrite($fp, json_encode($response));
		fclose($fp);
		
		echo "success";
		
	}
	else {
		echo "error";
	}

 ?>