# Equinox Loader Module

[![Build Status](https://travis-ci.org/collonville-tom/equinox-loader.svg?branch=master)](https://travis-ci.org/collonville-tom/equinox-loader)

Ce dépôt contient les projets d'un conteneur d'application OSGi (Eclipse Equinox) minimaliste. Il permet d'initialiser un environnement OSGi, de gérer dynamiquement le cycle de vie des bundles et d'offrir des outils de management à distance.

## Architecture du Projet

Le projet est découpé en plusieurs modules Maven :

*   **tc-osgi-bundle-utils-interfaces** : Définition des interfaces communes et des contrats de service.
*   **tc-osgi-bundle-utils** : Implémentations de base (Logging via `LoggerGestionnary`, Configuration, utilitaires RMI).
*   **tc-osgi-bundle-manager** : Gestionnaire de bundles exposant des MBeans JMX pour le monitoring et le contrôle.
*   **tc-osgi-equinox-loader** : Le bootstrapper principal qui lance le framework Equinox et charge les bundles initiaux.

## Fonctionnalités Clés

*   **Bootstrapping Equinox** : Lancement automatique du framework OSGi.
*   **Management à distance** : Support JMX et RMI pour la gestion des bundles.
*   **Pattern Command** : Utilisation systématique de commandes pour les opérations sur les bundles (`Load`, `Start`, `Filter`).
*   **Packaging Flexible** : Génération de paquets Debian (`.deb`) et d'images Docker.

## Prérequis

*   Java JDK 8 ou supérieur.
*   Maven 3.x.
*   (Optionnel) Docker.

## Installation et Build

Pour compiler l'intégralité du projet et générer les artefacts :

```bash
mvn clean install
```

Pour générer l'image Docker (si Docker est installé) :

```bash
mvn clean install -P DOCKER
```

## Déploiement

Le projet est configuré pour être déployé dans l'arborescence suivante sur un système Linux :

*   **Application** : `/opt/equinox-loader`
*   **Bundles** : `/opt/equinox-loader/bundles`
*   **Données/Travail** : `/var/equinox-loader-manager`

### Paquets Debian

Les fichiers `.deb` générés dans les dossiers `target/` de chaque module peuvent être installés via :

```bash
sudo dpkg -i tc-osgi-equinox-loader_*.deb
```

## Utilisation

Le point d'entrée principal est la classe `org.tc.osgi.equinox.loader.EquinoxLoaderMain`.

Pour étendre les fonctionnalités :
*   Pour une console, utilisez le bundle `tc-osgi-bundle-console-wrapper`.
*   Pour SpringDM, utilisez le bundle `tc-osgi-bundle-spring-wrapper`.

## Licence

Copyright © 2018-2026 org.tc. All rights reserved.
