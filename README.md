# Blackjack Console Application

## Overview

This project contains a simple Java blackjack game as a console application. The project was originally completed as assignment 2 for COMP 1502 at Mount Royal University.

## Features

- Main Blackjack game
- Save player stats
- Search players by name
- View top players with the most wins

## Getting Started

### Prerequisites

Make sure you've installed a [Java SDK](https://www.oracle.com/java/technologies/downloads/) on your machine before running this program.

### Installation

1. Clone the repo on your local machine:

```bash
git clone https://github.com/nalhe627/blackjack-console-app.git
```

2. Move into the project's root directory:

```bash
cd blackjack-console-app
```

### Build & Run the Program

1. Complete the [installation steps](#installation) and build the project as a JAR file:

```bash
jar cvfm BlackJack.jar Manifest.txt -C bin . lib res
```

2. Run the JAR file:

```bash
java -jar Blackjack.jar
```

The program should then start with this menu screen:

```
Select one of these options:

	(P) Play game
	(S) Search
	(E) Exit

Enter a choice:
```
