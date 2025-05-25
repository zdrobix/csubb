<?php
$conn = mysqli_connect("localhost", "root", "", "persoane_db");

$id = intval($_POST['id']);
$nume = mysqli_real_escape_string($conn, $_POST['name']);
$varsta = intval($_POST['varsta']);

$query = "UPDATE persoane_id SET nume = '$nume', varsta = $varsta WHERE id = $id";
if (mysqli_query($conn, $query)) {
    echo "ok";
} else {
    http_response_code(500);
    echo "error: " . mysqli_error($conn);
}
?>