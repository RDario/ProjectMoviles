<?php
function conectarBD() {
	$conexion = mysqli_connect("db709792197.db.1and1.com", "dbo709792197", "Moviles2017", "db709792197");
	if (mysqli_connect_errno()) {
		echo 'Error al conectar la base de datos '. mysqli_connect_error();
	} else {
	}
	return $conexion;
} ?>