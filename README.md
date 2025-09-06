# nopcommerce-ui-automation

Professionally structured **UI test automation** project (Selenium + Cucumber + TestNG) for `https://demo.nopcommerce.com`.

## Why this structure (industry-style, beginner-friendly)
- **Clear package names**: `core/` (infrastructure), `pages/` (POM), `steps/`, `hooks/`, `runner/`.
- **Single config point**: `AppConfig.BASE_URL` to change environments.
- **TestNG-first**: `testng.xml` is the execution entry for IDE and Maven.
- **One simple scenario** to start; extend with more features easily.

## Run via Eclipse TestNG plugin (strict)
1. Install **TestNG** plugin (Eclipse Marketplace).
2. Import as **Existing Maven Project**.
3. Right-click **`testng.xml`** → **Run As → TestNG Suite**.

## Run via Maven
```bash
mvn clean test
```

## Reports
- TestNG (IDE run): `test-output/`
- Cucumber HTML: `target/cucumber-report.html`

## Extend
- Create new Page Objects under `pages/`.
- Add scenarios to `src/test/resources/features/`.
- Add step defs under `steps/` and glue them via annotations.
