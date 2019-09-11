package dev.hophamducnguyen.auto.web.test.demo;

import dev.hophamducnguyen.auto.web.common.core.BaseTest;
import dev.hophamducnguyen.auto.web.common.pageobject.WebAdmin;
import dev.hophamducnguyen.auto.web.common.pageobject.WebClient;
import dev.hophamducnguyen.auto.web.common.parameter.csv.ExCsvFileSource;
import dev.hophamducnguyen.auto.web.common.utils.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;

public class SmokeTests extends BaseTest {

    @ParameterizedTest
    @ExCsvFileSource(resources = "/testdata/smoketest/smokeTest.csv")
    void qboFileUploadFinInstitutions(String storeId, String customId, String financialText,
                                      String qBOFileImportText, String importFile) {
        WebAdmin webAdmin = new WebAdmin(driver, settings);
        webAdmin.login.logInAsSuperTwoFactor();
    }
}
