package cashwise;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"html:target/reports/cucumberReport.html", "json:target/reports/cucumber.json"},
        features = "src/test/resources/features",
        glue = "cashwise.steps",
        tags = "@signup",
        dryRun = true

)
public class CucumberRunner{

}
