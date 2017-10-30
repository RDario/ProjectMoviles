<?php
header('Content-Type: application/json');
error_reporting(E_ALL);
require_once "DVPublicacion.php";
$arrayPublicaciones = array();

$arrayPublicaciones[count($arrayPublicaciones)] = new DVPublicacion(
  1,
  "Lorem ipsum",
  "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit",
  "Fusce feugiat aliquam felis, quis imperdiet elit tristique eu. Duis ultrices, elit sit amet malesuada euismod, velit neque tincidunt nisl, vitae euismod justo nisi ut ante. Duis efficitur libero sapien, nec porta libero placerat ut. Cras rhoncus, libero eget suscipit sodales, purus magna elementum justo, quis convallis massa nulla eu diam. Proin ante tortor, feugiat vel vehicula id, hendrerit quis ipsum. Donec tempor, quam in vulputate aliquet, tortor metus sollicitudin erat, vel pellentesque mauris orci at lorem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam viverra facilisis diam vitae malesuada. Mauris porttitor, quam ac aliquam ultricies, quam est ullamcorper est, et euismod ligula turpis at orci. Duis elementum lorem sit amet quam condimentum auctor.",
  "Consectetur Tempus",
  20,
  "http://192.168.0.10:80/WebserviceProyectoMoviles\imagenes\users\papa_dexter.jpg",
  "28/08/2017",
  "17:45:12",
  1,
  "http://192.168.0.10:80/WebserviceProyectoMoviles\imagenes\articles\ahorcado.jpg",
  "");

  $arrayPublicaciones[count($arrayPublicaciones)] = new DVPublicacion(
    2,
    "Lorem ipsum",
    "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit",
    "Fusce feugiat aliquam felis, quis imperdiet elit tristique eu. Duis ultrices, elit sit amet malesuada euismod, velit neque tincidunt nisl, vitae euismod justo nisi ut ante. Duis efficitur libero sapien, nec porta libero placerat ut. Cras rhoncus, libero eget suscipit sodales, purus magna elementum justo, quis convallis massa nulla eu diam. Proin ante tortor, feugiat vel vehicula id, hendrerit quis ipsum. Donec tempor, quam in vulputate aliquet, tortor metus sollicitudin erat, vel pellentesque mauris orci at lorem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam viverra facilisis diam vitae malesuada. Mauris porttitor, quam ac aliquam ultricies, quam est ullamcorper est, et euismod ligula turpis at orci. Duis elementum lorem sit amet quam condimentum auctor.",
    "Consectetur Tempus",
    25,
    "http://192.168.0.10:80/WebserviceProyectoMoviles\imagenes\users\shrek_meme.jpg",
    "28/08/2017",
    "17:55:45",
    2,
    "http://192.168.0.10:80/WebserviceProyectoMoviles\imagenes\articles\asfixia.jpg",
    "");

    $arrayPublicaciones[count($arrayPublicaciones)] = new DVPublicacion(
      3,
      "Lorem ipsum",
      "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit",
      "Fusce feugiat aliquam felis, quis imperdiet elit tristique eu. Duis ultrices, elit sit amet malesuada euismod, velit neque tincidunt nisl, vitae euismod justo nisi ut ante. Duis efficitur libero sapien, nec porta libero placerat ut. Cras rhoncus, libero eget suscipit sodales, purus magna elementum justo, quis convallis massa nulla eu diam. Proin ante tortor, feugiat vel vehicula id, hendrerit quis ipsum. Donec tempor, quam in vulputate aliquet, tortor metus sollicitudin erat, vel pellentesque mauris orci at lorem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam viverra facilisis diam vitae malesuada. Mauris porttitor, quam ac aliquam ultricies, quam est ullamcorper est, et euismod ligula turpis at orci. Duis elementum lorem sit amet quam condimentum auctor.",
      "Consectetur Tempus",
      30,
      "http://192.168.0.10:80/WebserviceProyectoMoviles\imagenes\users\portrait_19th_century.png",
      "28/08/2017",
      "18:33:35",
      3,
      "http://192.168.0.10:80/WebserviceProyectoMoviles\imagenes\articles\conspiracion_keanu.jpg",
      "");

  echo json_encode($arrayPublicaciones);
  ?>
