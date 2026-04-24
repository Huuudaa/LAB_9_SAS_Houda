<?php
include_once '../classes/Membre.php';
include_once '../connexion/Connexion.php';
include_once '../dao/IDao.php';

class MembreService implements IDao {
    private $connexion;

    function __construct() {
        $this->connexion = new Connexion();
    }

    public function create($o) {
        $sql  = "INSERT INTO Membre (nom, prenom, ville, genre)
                 VALUES (:nom, :prenom, :ville, :genre)";
        $stmt = $this->connexion->getLien()->prepare($sql);
        $stmt->execute([
            ':nom'    => $o->getNom(),
            ':prenom' => $o->getPrenom(),
            ':ville'  => $o->getVille(),
            ':genre'  => $o->getGenre()
        ]);
    }

    public function recupererTous() {
        $req = $this->connexion->getLien()->query("SELECT * FROM Membre");
        return $req->fetchAll(PDO::FETCH_ASSOC);
    }

    public function delete($o) {}
    public function update($o) {}
    public function findAll()  {}
    public function findById($id) {}
}
?>