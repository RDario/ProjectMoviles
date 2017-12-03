<?php
$imgFile = $_POST['imgFile'];
$file_path = "images/";
$file_path = $file_path . basename($_FILES['imgFile']['name']);
if(move_uploaded_file($_FILES['imgFile']['tmp_name'], $file_path)) {
	echo "success";
} else {
	echo "fail";
} ?>