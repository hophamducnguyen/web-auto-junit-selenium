package dev.hophamducnguyen.auto.web.common.core.webelement.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;

public class CustomElementFieldDecorator implements FieldDecorator {

    private final DefaultFieldDecorator defaultFieldDecorator;
    private final SearchContext searchContext;
    private final WebDriver webDriver;

    /**
     * The search context for the webelement. Mostly just a webdriver object.Used to find webelements on a webpage.
     **/
//    public CustomElementFieldDecorator(SearchContext searchContext, WebDriver webDriver) {
    public CustomElementFieldDecorator(WebDriver webDriver) {
        this.searchContext = webDriver;
        this.webDriver = webDriver;
        defaultFieldDecorator = new DefaultFieldDecorator(new DefaultElementLocatorFactory(searchContext));
    }

    /**
     * Selenium PageFactory search on all fields to decide how to decorate the field.
     **/
    @Override
    public Object decorate(ClassLoader loader, Field field) {
        // If it is a custom annotated webelement, then ensure proper initialisation via the adding of the callback method
        if (CustomWebElement.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(FindBy.class)) {
            return getEnhancedObject(field.getType(), getElementHandler(field), field.getAnnotation(FindBy.class));
        }
        // If it is a normal webelement, then use the default FieldDecorator implementation
        else {
            return defaultFieldDecorator.decorate(loader, field);
        }
    }

    private CustomElementLocator getElementHandler(Field field) {
        return new CustomElementLocator(getLocator(field));
    }

    private ElementLocator getLocator(Field field) {
        return new DefaultElementLocatorFactory(searchContext).createLocator(field);
    }

    private Object getEnhancedObject(Class<?> clzz, MethodInterceptor methodInterceptor, FindBy locator) {
        Enhancer e = new Enhancer();
        WebElementTransformer transformer = new WebElementTransformer();

        e.setSuperclass(clzz);
        e.setCallback(methodInterceptor);

        return e.create(new Class[]{WebDriver.class, By.class}, new Object[]{webDriver, transformer.transformFindByToBy(locator)});
    }
}
