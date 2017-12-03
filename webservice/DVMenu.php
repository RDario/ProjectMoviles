<?php
header('Content-Type: application/json');
error_reporting(E_ALL);
require_once "DVItemMenu.php";
$arrayMenu = array();

$arrayMenu[count($arrayMenu)] = new DVItemMenu("pant_home", "Portada", "http://www.elevation.com.mx/pages/pruebas/moviles/DVGetPortada.php");
$arrayMenu[count($arrayMenu)] = new DVItemMenu("pant_eventos", "Eventos", "http://www.elevation.com.mx/pages/pruebas/moviles/DVGetEventos.php");
$arrayMenu[count($arrayMenu)] = new DVItemMenu("pant_sugerencias_semanal", "Sugerencias Semanal", "");

echo json_encode($arrayMenu);
?>