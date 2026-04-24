<?php
include_once '../service/MembreService.php';
$ms = new MembreService();
header('Content-Type: application/json');
echo json_encode($ms->recupererTous());
?>