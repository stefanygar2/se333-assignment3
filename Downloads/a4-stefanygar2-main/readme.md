[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/wp0kqiYH)
# Deadline

June 5th, 2026 - midnight

> - Before you start, remember to write your student id and full name below:
> - STUDENTID:
> - FULLNAME:

---
# Instruction #

### Background

In this assignment, you will automate test and perform quality checks using GitHub Actions, a CI (Continuous Integration Tool) platform that runs your build and test. In this assignment, we will only be focused on continuous integration.

You’ll act as part of a engineering team responsible for maintaining code quality through automated testing and static analysis (linting).

1. **Workflows** – Automation pipelines defined in YAML files inside `.github/workflows/`.
2. **Triggers** – Events that start the workflow, for example, `push`, `pull_request`, or `schedule`.
3. **Jobs** – Sets of steps that can run in sequence or parallel.
4. **Steps** – Commands or actions that perform tasks within a job.
5. **Runners** – Virtual machines, GitHub-hosted or self-hosted, that execute your jobs.

**Example:** A workflow that runs tests automatically on each push.

Read [GitHub Actions Quickstart](https://docs.github.com/en/actions/writing-workflows/quickstart) before starting.

---

### Task 1 – Writing Larger Tests

**Goal:** Write integration and unit tests for the Amazon package.

1. **Explore the package:** Understand how the components interact.
2. **Write two types of tests:**
    - Integration Tests → name file `AmazonIntegrationTest.java`
    - Unit Tests → name file `AmazonUnitTest.java`
3. **For each, include:**
    - `@DisplayName("specification-based")` tests
    - `@DisplayName("structural-based")` tests
4. **Integration Tests:**
    - Test how multiple components work together.
    - Likely involves a database — reset the DB before each test using `@BeforeAll` or `@BeforeEach`.
5. **Unit Tests:**
    - Test individual classes in isolation.
    - Use mocks or stubs on all your unit test that rely on external services.
6. **Commit your work:**
    - `{YourName} + added tests`
7. **Push to main branch** to trigger the workflow.

---

### Task 2 – Automate Testing with GitHub Actions

**Goal:** Create a workflow that automatically runs tests and analysis on every push to GitHub.

1. **Create a workflow file:**
    - `.github/workflows/SE333_CI.yml`
2. **Configure the workflow to:**
    - Trigger on push to the main branch.
    - Run on Ubuntu runners.
3. **Add static analysis (Checkstyle):**
    - Follow [Maven Checkstyle Plugin](https://maven.apache.org/plugins/maven-checkstyle-plugin/usage.html).
    - Run Checkstyle during the `validate` phase, before tests.
    - Modify settings so Checkstyle does not fail the build on violations.
4. **Upload Checkstyle Report:**
    - After Checkstyle runs, upload its report using [`upload-artifact`](https://github.com/actions/upload-artifact).
5. **Add JaCoCo Coverage:**
    - Configure JaCoCo in `pom.xml`.
    - Run after tests and upload its report using [`upload-artifact`](https://github.com/actions/upload-artifact).
    - Make sure the static analysis runs before testing.
6. **Add a Build Badge:**
    - Display a build status badge in your `README.md`.
    - See: [Workflow Status Badge Docs](https://docs.github.com/en/actions/monitoring-and-troubleshooting-workflows/monitoring-workflows/adding-a-workflow-status-badge).
7. **Commit and Push:**
    - Commit message:
        - `{YourName} + added Workflow`
    - Push to GitHub to trigger your workflow.
    - Ensure all steps, static analysis, tests, and coverage, run successfully.

Your GitHub Actions run should generate these artifacts:

- `checkstyle.xml`
- `jacoco.xml` (coverage report)

#### Hint

Before submission, make sure:

- You have made three clear commits for each part.
- GitHub Actions executed successfully.
- The workflow produced Checkstyle and JaCoCo reports.
- Commit messages clearly describe the change, additions, deletions, or modifications.

---

### Evaluation Breakdown for Part 1

| Criteria | Description |
|---|---|
| GitHub Repository | Use Git & GitHub properly. Include 2 commits with clear messages. |
| GitHub Actions Workflow | Workflow runs automatically, includes log output for static analysis, testing, and coverage. |
| Reports & Artifacts | Checkstyle and JaCoCo reports are generated and uploaded successfully. |
| Test Quality | Includes both unit and integration tests with strong coverage, aim for 100% where possible. |
| README.md | Includes link to workflow, brief project overview, and confirmation that all actions passed. |

---

## Part 2 — User-Interface Testing

### Background

In this assignment, you will learn how to use user interface testing tools, specifically Playwright. Specifically, you will learn how to use a UI testing tool, create a UI test report, and create UI tests.

### Definition

UI testing is:

- Used to verify the reliability of User Interfaces and ensure the UI meets specifications.
- Done through test cases.
- Possible to do manually, but much more scalable through automation.

### Goals

To determine if UI behaves according to specifications and in consistent ways.

### Mechanism

Graphical user interfaces can present problems that will not be readily apparent through regular source code testing such as unit tests. Some of these problems might be subjective and must be tested by human beings to determine their severity. However, it is possible to automate certain aspects of UI testing. For example, it is possible to determine if all the desired and expected elements are present after a web page is done loading.

### Tools

Various tools exist to test user interfaces. These tools are usually highly dependent on the kind of UI being tested. Websites can be tested with tools such as Selenium. Tools such as Telerik can be used to test HTML AJAX and Silverlight applications, Cucumber can be used to test system behavior in a variety of languages, and can be used to run other UI tests. In mobile applications, we can use tools such as Espresso to automate UI testing.

### Playwright - Features

Playwright is a framework for testing web applications. It is composed of multiple components such as Playwright codeGen and recorder, which is a browser extension that can record and replay user interactions within a website. Playwright framework also contains a WebDriver, which allows programmers to write tests in a variety of programming languages using the Playwright API. These tests can then be run using most modern browsers to determine if the UI under test is functioning correctly.

### Environment and Requirements

Please make sure that you have Maven and Java installed on your computer.

- **OS:** macOS/Windows 10
- **Java:** version 1.8+
- **Maven:** version 3.8.5+

> **Note:** Only the above environment is tested by us, we are not sure if other versions work well.

---

### Task 1 — Adding playwright into your `pom.xml`

This assignment does not have a starter code. Your job is to create a new maven project and add playwright as dependency in your `pom.xml`.

```xml
<dependency>
  <groupId>com.microsoft.playwright</groupId>
  <artifactId>playwright</artifactId>
  <version>LATEST</version> // replace with the latest version
</dependency>
```

---

### Task 2 — Writing Playwright tests automations

The test case you will automate will be a purchase pathway scenario for a specific product in the DePaul University website bookstore. The test case should be performed as follows:

#### Startup

Start playwright codegen and record by typing cmd below:

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="codegen"
```

Go to [https://depaul.bncollege.com/](https://depaul.bncollege.com/) link on the browser.

Then start recording.

#### Write Test Cases

1. **TestCase Bookstore**
    - Enter “earbuds” on the search box in the upper right and press the return key.
    - Click on the “Brand” filter to expand it, then select “JBL”.
    - Click on the “Color” filter to expand it, then select “Black”.
    - Click on the “Price” filter to expand it, then select “$25-$50”.
    - Click on the any (e.g., "JLAB") item link.
    - AssertThat product name, SKU number, the price, and the product description.
    - Add 1 to the Cart.
    - AssertThat “1 Items" in cart, upper-right of page.
    - [You may need to pause here and wait for the cart icon to reflect the change].
    - Click “Cart”, click icon in upper right of page.

2. **TestCase Your Shopping Cart Page:**
    - AssertThat you are at cart: “Your Shopping Cart”.
    - AssertThat the product name, `whatever itemm you chose`, quantity (1), and price.
    - Select “FAST In-Store Pickup”.
    - AssertThat sidebar subtotal, handling, taxes, and estimated total is correct accorindingly.
    - Enter promo code `TEST` and click `APPLY`, code doesn’t exist so it should fail.
    - AssertThat promo code reject message is displayed.
    - Click “PROCEED TO CHECKOUT”.

3. **TestCase Create Account Page**
    - AssertThat “Create Account” label is present.
    - Select “Proceed as Guest”.

4. **TestCase Contact Information Page**
    - AssertThat you are at Contact Information page.
    - Enter a first name, a last name, an email address, a phone number.
    - AssertThat sidebar subtotal, handling, taxes, and estimated total.
    - Click `CONTINUE`.

5. **TestCase Pickup Information**
    - AssertThat Contact Information: name, email, and phone are correct.
    - AssertThat Pick Up location, DePaul University Loop Campus & SAIC.
    - AssertThat selected Pickup Person, “I’ll pick them up”.
    - AssertThat sidebar subtotal, handling, taxes, and estimated total.
    - AssertThat pickup item and price are correct.
    - Click `CONTINUE`.

6. **TestCase Payment Information**
    - AssertThat sidebar subtotal, handling, taxes, and estimated total.
    - AssertThat pickup item and price are correct.
    - Click “< BACK TO CART”> (upper-right of page).

7. **TestCase Your Shopping Cart**
    - Delete product from cart.
    - AssertThat your cart is empty.
    - Close window.

After you have developed all your test. Record the video of the entire Playwright test run start to finish. To record the video, use the code below:

```java
BrowserContext context = browser.newContext(new Browser.NewContextOptions()
    .setRecordVideoDir(Paths.get("videos/"))
    .setRecordVideoSize(1280, 720));
```

To execute the tests, you need to run `mvn test` to ensure that your UI test work as intended and passes all the tests and builds.

> **Note:** You MUST clear the browser cache between script runs or the cart quantities and the “tax” value will not be listed as TBD as it should do until the user’s account information.

---

### Task 4 — Use Github Actions

Upload your code to GitHub and set up GitHub Actions workflows so that every time code is pushed, your user interface tests are automatically executed. Please refer to the [link](https://playwright.dev/java/docs/ci-intro).

---

### Evaluation Breakdown for Part 2

| Criteria | Description |
|---|---|
| Video Recording | Your submission should have the recording of the video. |
| GitHub Actions Workflow | Workflow runs automatically, includes log output for static analysis, testing, and coverage. |
| README.md | Includes link to workflow, brief project overview, and answer to all the questions. |

---

## Submission Guideline

You must submit your assignment in **two places**:

1. **GitHub Classroom**
    - Push your final code to your GitHub Classroom repository.
    - Make sure all required files are included in the repository.
    - Make sure your commit history clearly shows your work for each major task.

2. **D2L**
    - Submit the link to your GitHub Classroom repository on D2L.

Before submitting, make sure the following requirements are satisfied:

- Your repository includes all required source code and test files.
- Your `README.md` includes:
    - A brief project overview.
    - A link to your GitHub Actions workflow.
    - Your build status badge.
- Your GitHub Actions workflow has run successfully.
- Your workflow uploads the required reports/artifacts.
- Your Playwright UI tests run successfully using `mvn test`.
- Your Playwright test execution video is included or clearly linked.
- Your final version is pushed before the deadline.

**Late Policy**

- No late policy.
- Note, any test you upload should be executeable. Meaning, you must upload required pom.xml. It it does not compile nor executable, it will be instantly zero.
- You do not submit it, it is automatically zero.
- Do not email me the assignment.
- You must submit this assignment to Github classroom.
- NOTE: If you do not submit your Github repository online. It will assume it is late or unfinshed, and will not be graded.