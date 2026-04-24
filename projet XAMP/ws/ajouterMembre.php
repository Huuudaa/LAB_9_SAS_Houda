<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include_once '../service/MembreService.php';
    extract($_POST);
    $ms = new MembreService();
    $ms->create(new Membre(0, $nom, $prenom, $ville, $genre));
    header('Content-Type: application/json');
    echo json_encode($ms->recupererTous());
}
?>