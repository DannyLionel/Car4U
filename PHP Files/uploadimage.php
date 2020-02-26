<?php 
	
	//importing dbDetails file 
	require "config.php";
	
	//this is our upload folder 
	$upload_path = 'images/';
	
	//creating the upload url 
	$upload_url = 'https://allansantosh.com/mobileappproject/'.$upload_path; 
	
	//response array 
	$response = array(); 
			
			//connecting to the database 
			//$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');
			
			//getting name from the request 
			$name = $_POST['name'];
			$user_id = $_POST['user_id_rental'];
			
			//getting file info from the request 
			$fileinfo = pathinfo($_FILES['image']['name']);
			
			//getting the file extension 
			$extension = $fileinfo['extension'];
			
			//file url to store in the database 
			$file_url = $upload_url . $name . "_user_" . $user_id.'.' . $extension;
			
			//file path to upload in the server 
			$file_path = $upload_path . $name . "_user_" . $user_id . '.'. $extension; 
			
			//trying to save the file in the directory 
			try{
				//saving the file 
				move_uploaded_file($_FILES['image']['tmp_name'],$file_path);
				//$sql = "INSERT INTO cars VALUES ('999', '999','999', '999','999', '999', '".$file_url."')";
				}

			catch(Exception $e){

			}		
