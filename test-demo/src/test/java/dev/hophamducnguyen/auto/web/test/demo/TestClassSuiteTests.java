package dev.hophamducnguyen.auto.web.test.demo;

import org.junit.Test;
import org.junit.experimental.categories.Categories;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({SpecificBrowserTests.class, SpecificEnvironmentTests.class})
@IncludeClassNamePatterns({"^.*Tests?$"})
@IncludeTags({"smoke", "xyz"})
@ExcludeTags("live")
public class TestClassSuiteTests {
}