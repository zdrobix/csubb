<?php
$id = intval($_GET['id']);

$conn = mysqli_connect("localhost", "root", "","persoane_db");
$result = mysqli_query($conn, "SELECT * FROM persoane_id WHERE id = ". $id . ";");

while($row = mysqli_fetch_array($result)) {
    echo "<div><input type='text' name='name' value='" . $row["nume"] . "'><br><input type='text' name='varsta' value='" . $row["varsta"] . "'></div>";
}
?>