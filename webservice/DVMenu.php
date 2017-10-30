<?php
header('Content-Type: application/json');
error_reporting(E_ALL);
require_once "DVItemMenu.php";
$arrayMenu = array();

$arrayMenu[count($arrayMenu)] = new DVItemMenu("pant_home", "Portada", "http://192.168.0.10:80/WebserviceProyectoMoviles/DVGetPortada.php");
$arrayMenu[count($arrayMenu)] = new DVItemMenu("pant_sugerencias_semanal", "Sugerencias Semanal", "");
$arrayMenu[count($arrayMenu)] = new DVItemMenu("pant_eventos", "Eventos", "");

echo json_encode($arrayMenu);
?>
