package dev.hophamducnguyen.auto.web.common.core.webelement.support;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CustomElementLocator implements MethodInterceptor {

    private final ElementLocator locator;

    public CustomElementLocator(ElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (o instanceof CustomWebElement) {
            // Invokes the method of the original object
            try {
                return methodProxy.invokeSuper(o, objects);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }
        // Configure a normal webelement
        else if (o instanceof WebElement) {
            // Get the first default webelement which matches the locator
            WebElement displayedElement = locateElement();
            return method.invoke(displayedElement, objects);
        }

        return null;
    }

    private WebElement locateElement() {
        return proxyForLocator(ElementLocator.class.getClassLoader(), locator);
    }

    private WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
        InvocationHandler handler = new LocatingElementHandler(locator);
        WebElement proxy;

        proxy = (WebElement) Proxy.newProxyInstance(
                loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);

        return proxy;
    }
}
