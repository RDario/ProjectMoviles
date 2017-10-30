<?php
error_reporting(E_ALL);
ini_set("display_errors", "1");

class DVPublicacion {
  var $identificador;
  var $titulo;
  var $descripcion;
  var $texto;
  var $autor;
  var $url_img_autor;
  var $id_autor;
  var $fecha;
  var $hora;
  var $tipo;
  var $url_img_articulo;
  var $url_pdf;

  function __construct($identificador, $titulo="", $descripcion="", $texto="", $autor, $id_autor, $url_img_autor="",
  $fecha, $hora, $tipo, $url_img_articulo="", $url_pdf="") {
    $this-> identificador = $identificador;
    $this-> titulo = $titulo;
    $this-> descripcion = $descripcion;
    $this-> texto = $texto;
    $this-> autor = $autor;
    $this-> id_autor = $id_autor;
    $this-> url_img_autor = $url_img_autor;
    $this-> fecha = $fecha;
    $this-> hora = $hora;
    $this-> tipo = $tipo;
    $this-> url_img_articulo = $url_img_articulo;
    $this-> url_pdf = $url_pdf;
  }
}
?>
