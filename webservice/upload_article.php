<?php
header('Content-Type: application/json');
header("Access-Control-Allow-Origin: *");
header('Access-Control-Allow-Credentials: true');
header("Access-Control-Allow-Methods: GET, POST, HEAD");
ini_set('display_errors', 1);
error_reporting(E_ALL);
include("conexionBD.php");

$connection = conectarBD();
$title = $_POST["txtTitulo"];
$summary = $_POST["txtDescripcion"];
$text_complete = $_POST["txtTextoCompleto"];
$section = $_POST["txtSeccion"];
$author = $_POST["txtAutor"];
$source = $_POST["txtFuente"];
$type_article = $_POST["txtTipoArticulo"];
$id_usuario = $_POST["txtIdUsuario"];
$name_usuario = $_POST["txtUsuario"];
$date = $_POST["txtFecha"];
$date_time = $_POST["txtHora"];
$url_img = $_POST["txtUrlImg"];

$sql = 'CALL spSubirArticulo("'.$title.'", "'.$summary.'", "'.$text_complete.'", "'.$section.'", "'.$author.'", "'.$source.'", "'.$type_article.'", "'.$id_usuario.'", "'.$name_usuario.'", "'.$date.'", "'.$date_time.'", "'.$url_img.'");';
$result = mysqli_query($connection, $sql);
$arrayUser = array();
if ($result) {
	echo "success";
} else {
	echo "failed";
}
$connection->close(); ?>