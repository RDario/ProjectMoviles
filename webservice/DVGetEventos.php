<?php
header('Content-Type: application/json;charset=utf-8');
ini_set("display_errors", "1");
error_reporting(E_ALL);
include("conexionBD.php");
include("DVPublicacion.php");
$connection = conectarBD();

mysqli_query($connection, 'SET CHARACTER SET utf8');
$sql = "SELECT * FROM view_eventos;";
$result = $connection->query($sql);
$arrayPublicaciones = array();
if ($result->num_rows > 0) {
  while ($row = $result->fetch_assoc()) {
    $arrayPublicaciones[count($arrayPublicaciones)] = new DVPublicacion(
      $row["idArticulo"],
      $row["titulo"],
      $row["descripcion"],
      $row["textoCompleto"],
      $row["seccion"],
      $row["autor"],
      $row["fuente"],
      $row["tipoArticulo"],
      $row["idUsuario"],
      $row["usuario"],
      $row["fecha"],
      $row["hora"],
      $row["urlImg"]);
  }
}
$connection->close();
echo json_encode($arrayPublicaciones);

function utf8ize($d) {
  if (is_array($d)) {
     foreach ($d as $k => $v) {
       $d[$k] = utf8ize($v);
     }
  } else if (is_string ($d)) {
     return utf8_encode($d);
  }
   return $d;
} ?>