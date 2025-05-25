<?php
header("Content-Type: application/json");

$b = json_decode($_POST["b"]);
$ai = $_POST["ai"];
$h = $ai === "X" ? "O" : "X";

function win($b) {
  $l = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0 ,4, 8],
    [2, 4, 6]
  ];
  foreach ($l as $p) {
    if ($b[$p[0]] && $b[$p[0]] === $b[$p[1]] && $b[$p[0]] === $b[$p[2]])
      return $b[$p[0]];
  }
  return null;
}

if (!win($b) && in_array("", $b)) {
  $e = [];
  foreach ($b as $i => $v) 
    if ($v === "") 
      $e[] = $i;
  $m = $e[array_rand($e)];
  $b[$m] = $ai;
}

$w = win($b);
$msg = $w ? ($w === $ai ? "ai a castigat!" : "ai castigat!") : (in_array("", $b) ? "randul tau" : "remiza");

echo json_encode([
  "board" => $b,
  "msg" => $msg,
  "over" => $w || !in_array("", $b)
]);
