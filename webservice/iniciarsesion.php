<?php
header('Content-Type: application/json');
header("Access-Control-Allow-Origin: *");
header('Access-Control-Allow-Credentials: true');
header("Access-Control-Allow-Methods: GET, POST, HEAD");
ini_set('display_errors', 1);
error_reporting(E_ALL);
include("conexionBD.php");
require_once('DVUsuario.php');

$connection = conectarBD();
$correo = $_POST["txtCorreo"];
$pass = $_POST["txtPassword"];

$sql = 'CALL `spValidarCredencialesLogin`("'.$correo.'", "'.$pass.'");';
$result = mysqli_query($connection, $sql);
$arrayUser = array();
if ($result->num_rows > 0) {
	while ($row = $result->fetch_array()) {
		$arrayUser[count($arrayUser)] = new DVUsuario($row["idUsuario"], $row["nombre"], $row["apellidoP"], $row["apellidoM"], $row["tipoUsuario"], $row["correo"], $row["contrasenia"], $row["urlImgPerfil"]);
	}
}
$connection->close();
echo json_encode($arrayUser);
?>