package dev.hophamducnguyen.auto.web.common.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class TestSettings {
    // server properties
    private String webAdminUrl;
    private String webCustomerUrl;
    private String apiAdminUrl;
    private String apiCustomerUrl;
    private String adminUploadUrl;

    // user properties
    private String superAdminUserName;
    private String superAdminPassword;
    private String adminUserName;
    private String adminPassword;
    private String clientUserName;
    private String clientPassword;

    // saucelabs
    private String sauceUserName;
    private String sauceAccessKey;
    private String sauceURL;

    // common
    private int timeout;
    private String browserType;

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getAdminUploadUrl() {
        return adminUploadUrl;
    }

    public void setAdminUploadUrl(String adminUploadUrl) {
        this.adminUploadUrl = adminUploadUrl;
    }

    public String getWebAdminUrl() {
        return webAdminUrl;
    }

    public void setWebAdminUrl(String webAdminUrl) {
        this.webAdminUrl = webAdminUrl;
    }

    public String getWebCustomerUrl() {
        return webCustomerUrl;
    }

    public void setWebCustomerUrl(String webCustomerUrl) {
        this.webCustomerUrl = webCustomerUrl;
    }

    public String getApiAdminUrl() {
        return apiAdminUrl;
    }

    public void setApiAdminUrl(String apiAdminUrl) {
        this.apiAdminUrl = apiAdminUrl;
    }

    public String getApiCustomerUrl() {
        return apiCustomerUrl;
    }

    public void setApiCustomerUrl(String apiCustomerUrl) {
        this.apiCustomerUrl = apiCustomerUrl;
    }

    public String getSuperAdminUserName() {
        return superAdminUserName;
    }

    public void setSuperAdminUserName(String superAdminUserName) {
        this.superAdminUserName = superAdminUserName;
    }

    public String getSuperAdminPassword() {
        return superAdminPassword;
    }

    public void setSuperAdminPassword(String superAdminPassword) {
        this.superAdminPassword = superAdminPassword;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public String getSauceUserName() {
        return sauceUserName;
    }

    public void setSauceUserName(String sauceUserName) {
        this.sauceUserName = sauceUserName;
    }

    public String getSauceAccessKey() {
        return sauceAccessKey;
    }

    public void setSauceAccessKey(String sauceAccessKey) {
        this.sauceAccessKey = sauceAccessKey;
    }

    public String getSauceURL() {
        return String.format("https://%s:%s@ondemand.saucelabs.com:443/wd/hub", sauceUserName, sauceAccessKey);
    }

    public void setSauceURL(String sauceURL) {
        this.sauceURL = sauceURL;
    }

}
