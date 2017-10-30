<?php
error_reporting(E_ALL);
ini_set("display_errors", "1");

class DVItemMenu {
  var $identificador;
  var $titulo;
  var $url;

  function __construct($identificador, $titulo="", $url="") {
    $this-> identificador = $identificador;
    $this-> titulo = $titulo;
    $this-> url = $url;
  }
}
?>
