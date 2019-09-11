package dev.hophamducnguyen.auto.web.test.demo;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({"dev.hophamducnguyen.auto.web.test.demo"})
@ExcludePackages("dev.hophamducnguyen.auto.web.test.benchmarks")
public class TestPackageSuite {
}