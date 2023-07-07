<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceuil</title>
    <style>
        body{
            display: flex;
            flex-direction: column;
        }
    </style>
</head>
<body>
    <a href="new-emp.do">
        Ajout employe
    </a>
    <a href="emp-list.do">
        Liste des employes
    </a>
    <a href="increment.do">
        Incrementer 'entree'
    </a>
    <a href="test-singleton.do">
        Test singleton (json)
    </a>
    <a href="add-admin.do">
        Se connecter en tant qu'admin
    </a>
    <a href="add-user.do">
        Se connecter en tant que simple utisateur
    </a>
    <a href="add-session.do">
        Ajouter dans la session
    </a>
    <a href="showSession.do">
        Afficher les sessions
    </a>
    <a href="invalidate.do">
        Invalider la session (json)
    </a>
    
    <div>
        <label for="">
            Supprimer la session
        </label>
        <form action="remove-session.do" method="get">
            <input type="text" name="remove" id="">
            <input type="submit" value="Supprimer">
        </form>
    </div>
</body>
</html>