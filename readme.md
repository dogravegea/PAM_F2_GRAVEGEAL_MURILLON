# TP 2 de programmation d'applications mobiles : 

## Présentation générale

Le but de ce tp était de réaliser une application mobile android avec les contraintes suivantes :
 -  Utilisation d'au moins une liste
 -  Un écran de détail pour chaque item de la liste
 -  Utilisation de viewmodel et de coroutines

Nous avons donc développé une application permettant de lister ces différentes recettes et obtenir le détail de chacune d'entre-elles.

### Présentation générale de l'application

Pour développer cette application, nous avons utilisé l'API suivante : [themealdb](https://www.themealdb.com/api.php).

Cette API permet de récupérer des informations sur des recettes de cuisine. On peut notamment obtenir les éléments suivants :

- Nom de la recette
- Type de recette (plat principale, accompagnement, dessert...)
- Instructions pour la préparation
- Ingrédients nécessaires ainsi que leur quantité

## Fonctionnalités

Au lancement de l'application, cette dernière effectue une requête à l'API pour récupérer la liste des recettes. Dans l'éventualité où l'utilisateur n'a pas accès à internet, un message d'erreur s'affiche pour prévenir qu'une erreur a eu lieu et un bouton s'affiche. L'appui sur ce dernier permet de retenter d'appeler l'API afin de charger les recettes.

Une fois que les données ont été récupérées, on les affiche sous forme de carte dans une liste. Chaque carte présente l'image correspondant au plat que la recette permet de cuisiner, le nom du plat ainsi, ainsi que le type de la recette. Il est possible de filtrer les éléments de cette liste grâce à une barre de recherche (recherche sur le nom ou sur le type de la recette) 

Un clic sur une carte amène sur le détail de la recette. On retrouve alors les éléments suivants :

- Image du plat
- Nom du plat
- Type de recette
- Ingrédients et quantité
- Instructions de préparation

## Choix techniques

Sur la page principale de l'application, qui propose la liste des recettes, nous avons utilisé une RecyclerView pour l'affichage des éléments. Cette dernière est renseignée à partir d'un ViewModel qui permet de persister les éléments de la liste lors de la destruction de l'activité (exemple : rotation d'écran). Ce ViewModel est rempli avec les données une seule fois au lancement de l'application, les données ne sont pas rechargées par la suite.
Lors de l'accès au fragment de détail d'une recette, aucun nouvel appel d'api n'est réalisé. La recette à afficher est sérialisée dans un Bundle, puis transmise au fragment de détail qui l'affichera. De même, lors du retour sur le fragement contenant la liste des recettes, les données ne sont pas à nouveau récupérées depuis l'api car elles ont été conservées par le ViewModel.
En ce qui concerne la barre de recherche, elle est liée directement à l'adapteur de la RecyclerView, ce qui permet de filter les éléments à l'affichage, encore une fois sans avoir besoin de rappeller l'api.
