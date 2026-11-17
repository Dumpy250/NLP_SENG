# NLP_SENG

A small **Java/Maven** Stanford CoreNLP console example. It reads `yourfilename.txt`, annotates the resulting text and prints each token's word, part-of-speech tag and named-entity label. This is separate from the Python `Spacy_SENG` project.

## Files and entrypoint

- `src/main/java/org/example/Main.java` — entrypoint **`org.example.Main`**.
- `pom.xml` — Java 17 source/target settings and Maven dependencies.
- `yourfilename.txt` — supplied input, read relative to the process's working directory.

The pipeline requests `tokenize, ssplit, pos, lemma, ner, parse, dcoref`. Only word/POS/NER fields are printed; the program does not display lemma, parse-tree or coreference results, despite configuring those annotators. No GUI, interactive menu, redaction or output-file export is implemented.

## Requirements

Use a maintained **JDK 17** distribution and Maven, or an IDE with Maven support. The project does not include a Maven wrapper or pin Maven/plugin versions.

Declared dependencies:

| Dependency | Version |
|---|---|
| Stanford CoreNLP and its `models` classifier | 4.5.5 |
| Stanford Parser | 3.9.2 |
| SLF4J API | 1.7.5 |
| SLF4J Simple binding | 1.6.4 |

This is a legacy dependency combination, not a verified modern compatibility baseline. Review library/logging alignment and maintenance before broader use; do not assume independently replacing jars is safe. The CoreNLP model resources must be on the runtime classpath as well as the library jars.

## Source-checked usage

These instructions are **unexecuted**. From the repository root:

```sh
mvn compile
```

Maven may download plugins, libraries and the declared model artifact, write its local cache, and create `target/`. This command is a build, not a read-only inspection.

For a straightforward launch, import `pom.xml` as a Maven project in an IDE, select JDK 17, and run **`org.example.Main`** using the project's Maven dependency classpath. Set the working directory to the repository root containing `yourfilename.txt`. Starting the program loads the NLP pipeline and performs annotation; no filename argument is implemented.

There is no executable-JAR manifest or configured run plugin in the POM, so `java -jar` is not a supplied launch route. Successful dependency resolution, compilation and model execution have not been established here.

## Input and limitations

- The file reader uses `Scanner(File)` without an explicit charset; the POM's UTF-8 **source-code** setting does not set the input text's decoding. On JDK 17, input decoding depends on the platform default.
- Input is first split using `\.\s` (a period followed by whitespace), then converted with `Arrays.toString(...)` before annotation. That introduces brackets/commas and removes matched delimiters; the annotated string is not the original file text unchanged.
- `dcoref` selects deterministic coreference in this CoreNLP version, not a modern neural-coreference configuration. Its results are not printed by this example.
- Model initialization happens before file reading. Only a missing input file is explicitly caught; model/classpath/runtime errors do not have a dedicated recovery workflow.
- No automated tests, measured extraction accuracy or verified output examples are included.
