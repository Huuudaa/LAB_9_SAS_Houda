<?php
class Membre {
    private $id, $nom, $prenom, $ville, $genre;

    function __construct($id, $nom, $prenom, $ville, $genre) {
        $this->id     = $id;
        $this->nom    = $nom;
        $this->prenom = $prenom;
        $this->ville  = $ville;
        $this->genre  = $genre;
    }

    function getNom()    { return $this->nom; }
    function getPrenom() { return $this->prenom; }
    function getVille()  { return $this->ville; }
    function getGenre()  { return $this->genre; }
}
?>