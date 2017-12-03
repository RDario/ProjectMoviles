<?php
error_reporting(E_ALL);
ini_set("display_errors", "1");

class DVUsuario {
  var $idUsuario;
  var $nombre;
  var $apellidoP;
  var $apellidoM;
  var $tipoU;
  var $correo;
  var $contrasenia;
  var $url_img_perfil;

  function __construct($identificador, $nombre, $apePate="", $apeMate="", $tipo, $email, $pass, $url_imgautor="") {
    $this-> idUsuario = $identificador;
    $this-> nombre = $nombre;
    $this-> apellidoP = $apePate;
    $this-> apellidoM = $apeMate;
    $this-> tipoU = $tipo;
    $this-> correo = $email;
    $this-> contrasenia = $pass;
    $this-> url_img_autor = $url_imgautor;
  }
} ?>