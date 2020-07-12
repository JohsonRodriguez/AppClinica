<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
 $dni=$_POST['dni'];
 $nombre=$_POST['nombre'];
 $apellido=$_POST['apellido'];
 $correo=$_POST['correo'];
 $celular=$_POST['celular'];
 $contraseña=$_POST['contraseña']
 
 require_once 'connect.php';

 $sql="insert into paciente (dni,nombre,apellido,correo,celular,contraseña) values ('$dni','$nombre','$apellido','$correo','$celular','$contraseña')"
if(mysqli_query($conn, $sql)){
    $result["success"]="1";
    $result["message"]="success";

    echo json_encode($result);
    mysqli_close($conn);
}else{
    $result["success"]="o";
    $result["message"]="error";

    echo json_encode($result);
    mysqli_close($conn);
}
}
?>