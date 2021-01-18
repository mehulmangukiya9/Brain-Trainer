<?php

	$servername = "localhost";
	$username = "root";
	$password = "";
	$dbname = "braintrainertrial";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

if($_SERVER['REQUEST_METHOD']=='GET'){


 //Connecting to ;our database by calling dbConnect script 
//  require_once('dbConnect.php');
  
 $sql = "SELECT * FROM gametable";
 
 //Trying to insert the values to db 
 $res = mysqli_query($conn,$sql);
	 
	$result = array();
	
	while($row = mysqli_fetch_array($res)){
		array_push($result, array(
		'name'=>$row[0],
		'gameTime'=>$row[1],
		'score'=>$row[2]
	));	
	}

	echo json_encode($result);
 //Closing the database connection 
 mysqli_close($conn);
} 
else {
	echo 'Database error';
}
?>