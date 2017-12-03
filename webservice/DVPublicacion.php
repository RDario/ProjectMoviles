<?php
ini_set("display_errors", "1");
error_reporting(E_ALL);

class DVPublicacion {
  var $identificador;
  var $titulo;
  var $descripcion;
  var $texto;
  var $seccion;
  var $autor;
  var $fuente;
  var $tipo;
  var $id_usuario;
  var $usuario;
  var $fecha;
  var $hora;
  var $url_img_articulo;

  function __construct($identificador, $titulo, $descripcion="", $texto, $seccion, $autor, $fuente, $tipo, $idUsuario, $user, $fecha, $hora, $url_img="") {
    $this-> identificador = $identificador;
    $this-> titulo = $titulo;
    $this-> descripcion = $descripcion;
    $this-> texto = $texto;
    $this-> seccion = $seccion;
    $this-> autor = $autor;
    $this-> fuente = $fuente;
    $this-> tipo = $tipo;
    $this-> id_usuario = $idUsuario;
    $this-> usuario = $user;
    $this-> fecha = $fecha;
    $this-> hora = $hora;
    $this-> url_img_articulo = $url_img;
  }
} ?>