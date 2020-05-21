# [Poker Calculator in Java](https://github.com/mr-wolf-rsh/poker-calculator-java/)

This is a demo that 'calculates' the winning poker hand between 2 players through algorithms.
This is the Java version of:

* [Poker Calculator in Python](https://github.com/mr-wolf-rsh/poker-calculator-python/)
* [Poker Calculator in C#](https://github.com/mr-wolf-rsh/poker-calculator-csharp/)
* [Poker Calculator in JavaScript](https://github.com/mr-wolf-rsh/poker-calculator-js/)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
Probably needed: [Writing Java with Visual Studio Code](https://code.visualstudio.com/docs/java/java-tutorial/).

### Installation

It requires no other installation than having JDK (Java 8 minimum). Personally, I used JDK 12.

### Built with

* [Visual Studio Code](https://code.visualstudio.com/) was the code editor used for this, so it requires extensions from [Java Extension Pack](https://code.visualstudio.com/docs/languages/java/), as VSCode suggests: <https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack>.

* [Maven](https://maven.apache.org/) was used for scaffolding this project, specifically 'Maven Archetype Quickstart for Java 8'. Helpful: [Java Project Management in VS Code](https://code.visualstudio.com/docs/java/java-project#_maven).

* [JUnit](https://junit.org/) was used for testing. This may help: [Testing Java with Visual Studio Code](https://code.visualstudio.com/docs/java/java-testing/).

### Usage

The process is simple, once is run, it will ask for the pokerdata.txt source directory and then, for the target directory (pokerdata.txt was added as an example for file format).

Unit tests are included.

### Bear-in-mind

As Visual Studio Code Output Console cannot receive inputs, it's necessary you add this to the generated launch.json file to use the Integrated Terminal:

```json
"console": "integratedTerminal"
```

Other ways to run/debug is up to you.

## Authors

* **Renzo S.** - *Final work* - [mr-wolf-rsh](https://github.com/mr-wolf-rsh/)

## Contributing

All kinds of suggestions are welcome. This has academic purposes only.

## License

This project is licensed under the [GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/).
