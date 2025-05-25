<?php
$pagina = intval($_GET['number']);
$paginaSize = 3;
$offset = $pagina * $paginaSize;

$conn = mysqli_connect("localhost", "root", "","persoane_db");
$result = mysqli_query($conn, "SELECT * FROM persoane limit " .$offset ."," . $paginaSize .";");

echo "<tr><th>Nume</th><th>Prenume</th><th>Tel</th><th>Email</th></tr>";
while($row = mysqli_fetch_array($result)) {
    echo "<tr><td>".$row['nume']."</td><td>".$row['prenume']."</td><td>".$row['telefon']."</td><td>".$row['email']."</td></tr>";
}
?>