<?php
$conn = mysqli_connect("localhost", "root", "","persoane_db");
$result = mysqli_query($conn, "SELECT id FROM persoane_id;");

while($row = mysqli_fetch_array($result)) {
    echo "<option value=" . $row["id"] . ">" . $row["id"] . "</option>";
}
?>