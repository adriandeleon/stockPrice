# StockPrice

**StockPrice** is a simple Java 25+ compact source file script example.  

It demonstrates how to run Java file directly from source files without a build tool and without compiling (no `javac`, Maven or Gradle required).

The example source code is based on the third example in this Jetbrains Blog post: [Java 24: Build Games, Prototypes, Utilities, and More –With Less Boilerplate](https://blog.jetbrains.com/idea/2025/02/java-24-build-games-prototypes-utilities-and-more-with-less-boilerplate/#3.-building-utilities,-such-as-a-stock-price-scraper). 

---

## 📂 Project Structure

```
StockPrice/
├── README.md      # Project documentation.
├── src/Main.java  # Main script file (Java 25 compact source, you can add more files if needed).
├── libs/*         # Optional JAR dependencies (classpath, only if needed).
├── run.bat        # Windows 10/11 batch launcher.
└── run.sh         # POSIX shell launcher (Linux, macOS, WSL).
```

---

## 🚀 Features

- **Compact Source Files** (Java 25+): run java directly without compiling.
- **Cross-platform** launchers:
    - `run.bat` → Windows 10/11
    - `run.sh` → Linux, macOS, or WSL
- **Automatic Java version check**: requires **JDK 25 or later**.
- **Zero build tools**: no Maven or Gradle needed.

Modify the `Main.java` code as you wish, delete/add any `JAR` files in the `libs/` directory, add more files to the `src/` directory: No need to change the run scripts in any way.

---

## 🛠️ Requirements

- **Java JDK 25 or newer** installed and available in your `PATH`.

Check your version:

```sh
java -version
```

---

## ▶️ Usage

Clone the repository:

```sh
git clone https://github.com/adriandeleon/StockPrice.git
cd StockPrice
```

Run on **Windows**:

```bat
run.bat
```

Run on **Linux / macOS / WSL**:

```sh
./run.sh
```

Both launchers will:
1. Verify that Java 25+ is installed.
2. Run the script directly:

```sh
java --class-path "libs/*;src" src/Main.java
```

---

## 📦 Version

**1.0.0**

---
## 🔮 Future
Add support for command line arguments and options.

---

## 📜 License

This project is licensed under the **MIT License**.  
See the [LICENSE](LICENSE) file for details.

