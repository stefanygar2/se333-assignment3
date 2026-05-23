[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/ZU0IiOqS)
## Deadline

May, 22, 2026 - midnight
> - This assignment is worth 15%
> - Before you start, remember to write your student id and full name below:
> - STUDENTID: 2196685
> - FULLNAME: Stefany Garcia 

---

## Instruction

Static analysis is the practice of analyzing source code **without executing it**.

In software testing pipelines, static analysis typically runs during code review, in pre-commit or pre-merge checks and as part of continuous integration (CI). Conceptually, static analysis automates what developers do during manual code review, such as spotting risky patterns, identifying design smells and detecting bug-prone code early. Static analysis tools provide fast, repeatable, automated feedback, reducing the likelihood that risky changes ever reach later pipeline stages.

In this lab, you will build a small static analysis tool using `Spoon`, which is an open-source Java framework for source-code analysis and transformation. As we have seen in the lecture, the `Spoon` framework will build a model of your codebase, such that it can be queried programmatically. For example, let’s say that we are trying to detect instances in the codebase where function is empty. To find such instances, you can use `Spoon` API to encode the rule below.

<img width="882" height="527" alt="image" src="https://github.com/user-attachments/assets/dce4a86d-05a5-44c2-9253-4a2ffd0cfff1" />

### Explanation for Code Above

The call to `getElements` queries the Spoon `CtModel` for all elements of type `CtMethod`, effectively retrieving every method declared in the codebase. The returned result is a list of method nodes, which can then be analyzed individually.

For each method, the code checks whether its body contains any statements using `m.getBody().getStatements().isEmpty()`. If the method body is empty, the method is recorded as a detected issue, along with its source file and line range.

### Example Output

When this analysis is executed, an empty method is reported as follows:

```java
Detected: Empty Method Block at Line[8:10] in class BuggyFile.java
```

This output indicates the location of the empty method and helps developers quickly identify and address dead or incomplete code.

## Starter Code (Provided)

You are given a small Java codebase that simulates **bug-prone production code**. The project includes the following files:

- **`BuggyFile.java`** — intentionally **seeded** with common bug patterns and design flaws
- **`ModelUtil.java`** — constructs the Spoon **AST model** (meaning, this is the code that builds database of your codebase).
- **`EmptyCatch.java`** — a fully working example detector
- **`EmptyMethod.java`** — another fully working example detector
- **`Main.java`** — a CLI runner used to execute detectors (you will extend this)

### Constraints

- You may **only** add new detector implementations under:

  ```text
  src/test/java/.../detectors/
  ```

- You **may** modify:
  - `Main.java`
  - `ModelUtil.java`
- **Do not modify `BuggyFile.java`**. Treat it as production code that you do **not** control.

## Prerequisites& environment.

Before starting, ensure the following are installed:

- Java **17+**
- Maven **3.9+**
- Git
- Basic familiarity with **JUnit 5**

```xml
<dependencies>
  <dependency>
    <groupId>fr.inria.gforge.spoon</groupId>
    <artifactId>spoon-core</artifactId>
    <version>10.4.2</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Objective
> In this lab, you will use **Spoon** to statically analyze `BuggyFile.java` and detect common bug patterns and design issues.

### Your Task

Implement detectors for **seven (7)** issues in total using Spoon. Some detectors may already be provided as examples.

You must detect the following patterns below:

#### Always True Condition

Look for `CtIf` nodes where the condition always evaluates to `true`.

```bash
public void method3() {
    if (true) {

    }
    int x = 4;
    if (x < 3) {
        x = x + 1;
    }
}
```

#### Incorrect String Comparison

Identify binary comparisons (`==` or `!=`) between `String` values.

```bash
public void method5() {
    String s1 = "test";
    String s2 = "test";
    boolean equal = (s1 == s2);
}
```

Relevant Spoon nodes may include:

- `CtBinaryOperator`
- `CtExpression`
- Type references (`java.lang.String`)

#### File Not Closed

Detect file resources that are opened but never closed.

```bash
public void method6_1() {
    try {
        FileReader fileReader = new FileReader("file.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
```

You may want to inspect:

- `CtTry`
- `CtCatch`
- Constructor calls (e.g., `new FileReader(...)`)
- Missing `close()` calls or lack of try-with-resources

#### Division by Zero

Look for division operations where the divisor is zero.

```bash
public void method7_1() {
    int x = 10;
    x = x / 0;
}
```

Focus on:

- `CtBinaryOperator`
- Division operators (`/`)
- Literal values equal to `0`

## Part 1 — Implement Static Analysis and Test it on Seeded files

### 1. Understand the Starter Code

- Open the provided starter project and familiarize yourself with its structure.
- Your task is **not** to rewrite the project, but to **extend it** by adding new static analysis detectors.
- Again your goal is to extend only the **bug** shown in the figure below in the red box.

  <img width="388" height="536" alt="image" src="https://github.com/user-attachments/assets/1aa7ab3a-9ff2-49af-b244-54783a032f54" />

- Specifically, the code below in the red box.

  <img width="1141" height="513" alt="image" src="https://github.com/user-attachments/assets/7a9d86d0-4615-4b78-8089-4e3e3385dfe1" />

### 2. Perform Test-Driven Development

Before implementing the static analysis detector, you must first write tests that describe the expected behavior.

This means you should:
- Utilize the `BuggyFile.java` to test your code.
- Include at least one **positive test case**, where the detector should report an issue.
- Include at least one **negative test case**, where similar-looking code should not be reported.
- Run the tests before implementing the detector to confirm that they fail initially.
- Implement the detector only after the failing test clearly describes the expected behavior.
- Re-run the tests after implementation to confirm that the detector correctly identifies the seeded issue.
    
### 3. Implement a Static Analysis Tool
  - Using **Spoon**, implement static analysis rules to detect the required issue patterns in **Java** source code.
  - Each rule should be implemented as a **separate detector**.
  - The code is already written in such way that, when an issue is detected, log a message that includes relevant information such as:
    - File name
    - Line range
    - Code snippet or AST element

    (An example log message is shown in the provided screenshot.)

- When you implemented the rule, please update the `main.java` accordingly:

  <img width="617" height="486" alt="image" src="https://github.com/user-attachments/assets/02a1fd99-bb0f-42ea-98ae-3810b5a99f20" />

### 4. Bonus [2.5% of Final Grade]
  For the bonus section, go to the SpotBugs bug descriptions page:
  
  https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html
  
  Select **three SpotBugs rules** and implement corresponding static analysis detectors for them.
  
  If you complete this bonus section, you must also update the rest of the assignment instructions to include these new detectors. This includes:
  
  - adding tests for the selected rules,
  - adding seeded Java files with examples,
  - implementing each detector separately,
  - updating `main.java` so the new detectors run,
  - and including true positive / false positive analysis for the bonus rules in the final Answer section.


### Hints

- Start by looking at `EmptyMethod.java`.
- The detection logic is similar in structure to the provided `EmptyCatch` detector.
- Focus on identifying the appropriate Spoon AST node types (e.g., `CtMethod`, `CtCatch`, `CtIf`, etc.).

## Part 2 — Run your static analysis on Real Repository
> Previously, you ran your tool on a tester code. Now, let's run the tool on real life project.

### 1. Select a Target Repository
- Choose a **Java** repository fromm Github with **no more than ~50,000 lines of code**. If you have hard time deciding which project to analyze. You could try analyzing https://github.com/apache/commons-lang.

### 2. Execute the Analysis
- Run the `Main` on the repository’s `src` directory.
  <img width="1055" height="378" alt="image" src="https://github.com/user-attachments/assets/ce7877a8-c535-4c16-8999-bea904caa44c" />
  - The detector currently runs on the project specified on **line 30**. Clone a real-world Java repository, modify this line to reference the cloned repository’s source directory, and run the detector to analyze the project.

### 3. Sample and Inspect Results
- For **each rule**, select **two findings** from the output.
- Manually inspect the corresponding source code.
- Label each finding as either true positive (TP) or false positive (FP). Briefly provide explanation for why it is FP or TP?

  > **Note:** These detection rules are intentionally simple, so some issues may not appear in the selected project. In many cases, this is because the repository may already enforces similar checks. If a particular issue is not detected, clearly state this in your write-up. You will **not** be penalized for the absence of findings.

## Submission Guide
### Important Details to Submit ###
- Part1 - Write the source code and test code. Run it on `BuggyFile.java` save the outputs to a file called `part1.log` in the `resource` directory. Note, DO NOT modify `BuggyFile.java`. However, you could modify other files if needed. 
- Part2 - Run your previous code from Part1 on a real life Github Repository. Save the outputs to a file called `part2.log` in the `resource` directory.
- Part3 - Perform analysis of true positive and false positive, and write it in `Answer` section at the end of the markdown. For analysis, try to follow the template below:
  ```
      <code sample>
      <link to the GitHub file>
      The link should reference the corresponding line number.
      <analysis>
  ```
- If you complete the bonus section, you must also update the rest of the assignment instructions to include these new detectors. Otherwise, you will not get the marks.

### Submission Filename ###
- You must write your full name and student ID at beginning of the markdown.
- You must submit this assignment to Github classroom.
- NOTE: If you do not submit your Github repository online. It will assume it is late or unfinshed, and will not be graded.

### Late Policy ###
- No late policy.
- You do not submit it, it is automatically zero.
- Do not email me the assignment.
