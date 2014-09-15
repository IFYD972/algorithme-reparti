Système réparti pour gestion de traffic
==

Auteurs : [IFYDY972](https://github.com/IFYD972), [nissnac](https://github.com/nissnac) et  [AlexMili](https://github.com/AlexMili)

Ce projet a été réalisé dans le but d'appliquer les principes de base de réalisation d'applications réparties.

L'objectif' de ce projet est de réaliser un outil d’aide à la gestion du trafic de bus. L’application permet de prendre en charge plusieurs bus et de présenter à un contrôleur la liste des bus dont il a la charge. Un contrôleur peut sélectionner un ou plusieurs bus de cette liste et leur envoyer un ordre. 

L’application répartie est constituée d’un ensemble de bus qui communiquent avec leur contrôleur via un serveur de gestion du trafic qui coordonne les communications entre les bus et le contrôleur. 
 
Un bus est caractérisé par les informations suivantes : son numéro, sa destination, sa vitesse courante et sa position courante. 

L’état du bus doit être donné au contrôleur. Il doit être dans l’une des situations suivantes : 
- normal : le trafic est fluide, le bus n’a aucune difficulté à circuler 
- intermédiaire : il y a des ralentissements, le bus à une vitesse de circulation entre 30km et 20km/h 
- critique : situation de bouchon ou de problème, le bus présente une vitesse de 
circulation inférieure à 10km/h

A intervalle de temps régulier, un bus envoie toutes ses caractéristiques au serveur de gestion du trafic. 
 
Le rôle du serveur de gestion du trafic est donc de recevoir en simultané les messages de tous les bus et de les renvoyer vers le contrôleur. Il émet également les ordres du contrôleur à l'intention des bus concernés. 
 
Le contrôleur peut modifier les caractéristiques d’un bus en lui demandant de changer sa destination ou sa vitesse. Il devra sélectionner d’abord le numéro de bus concerné. 

La documentation du projet est disponilbe au format PDF.
