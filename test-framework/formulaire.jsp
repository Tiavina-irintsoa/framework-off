<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un nouvel employé</title>
</head>
<body>
    <h2>Ajouter un nouvel employé</h2>
    <form action="add-emp.do" method="post" enctype="multipart/form-data">
        
        <div>
            <label for="nom">Nom :</label>
            <input type="text" id="nom" name="nom" required>
        </div>
        <div>
            <label for="departement">Département :</label>
            <select id="departement" name="departement">
                <option value="Comptabilite">Comptabilite</option>
                <option value="Finance">Finance</option>
                <option value="Informatique">Informatique</option>
            </select>
        </div>
        <div>
            <label for="">Date d'embauche</label>
            <input type="date" name="dateEmbauche" id="">
        </div>
        <div>
            <label for="cin">CIN :</label>
            <input type="file" id="cin" name="cin">
        </div>
        <div>
            <label for="remarque">Remarque :</label>
            <input type="text" id="remarque" name="remarque">
        </div>
        <div>
            <input type="submit" value="Ajouter">
        </div>
    </form>
</body>
</html>
