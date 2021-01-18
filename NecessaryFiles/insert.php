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

if($_SERVER['REQUEST_METHOD']=='POST')
{

    $name = $_POST['name'];
 $gameTime = $_POST['gameTime'];
 $score = $_POST['score'];
 

$sql = "INSERT INTO gametable (name,gameTime,score) VALUES('$name',$gameTime,$score)";

if ($conn->query($sql) === TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
}
else {
    # code...
}

?>