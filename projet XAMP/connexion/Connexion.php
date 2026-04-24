<?php
class Connexion {
    private $lien;

    public function __construct() {
        try {
            $this->lien = new PDO(
                "mysql:host=localhost;dbname=campus_db;charset=utf8",
                "root", ""
            );
            $this->lien->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            die('Connexion échouée : ' . $e->getMessage());
        }
    }

    public function getLien() {
        return $this->lien;
    }
}
?>