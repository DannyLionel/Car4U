<?php
$hostname = "localhost";
$username = "mobile_app_user";
$password = "mobile_app_pass";
$database = "admin_mobile_app_project";
$conn = new mysqli($hostname, $username, $password, $database);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
  }
 ?>