package playground.duck.typing;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TypeCoercer {

  private final Object coercionTargetObject;

  private TypeCoercer(Object coercionTargetObject) {
    this.coercionTargetObject = coercionTargetObject;
  }

  public static TypeCoercer of(final Object coercionTargetObject) {
    return new TypeCoercer(coercionTargetObject);
  }

  public <T> T to(final Class<T> targetInterfaceCandidate) {
    // Guard-clause against receiving null and classes as coercion targets
    if (targetInterfaceCandidate == null || !targetInterfaceCandidate.isInterface()) {
      throw new IllegalArgumentException("Type must be an interface");
    }

    if (this.targetObjectIsSubclassOfInterface(targetInterfaceCandidate)) {
      return targetInterfaceCandidate.cast(coercionTargetObject);
    }

    if (targetObjectImplementsInterface(targetInterfaceCandidate)) {
      return generateProxy(targetInterfaceCandidate);
    }

    // Fallback exception
    throw new ClassCastException(
        "Could not coerce type=[%s] to interface=[%s]"
            .formatted(
                coercionTargetObject.getClass().getName(), targetInterfaceCandidate.getName()));
  }

  private boolean targetObjectIsSubclassOfInterface(final Class<?> targetInterface) {
    return targetInterface.isAssignableFrom(this.coercionTargetObject.getClass());
  }

  private boolean targetObjectImplementsInterface(final Class<?> targetInterface) {
    final String notImplementedMethods = Arrays.stream(targetInterface.getMethods())
        .filter(Predicate.not(this::existsMathingMethodOnTargetObject))
        .map(Method::getName)
        .collect(Collectors.joining(", "));

    if (!notImplementedMethods.isEmpty()) {
      throw new ClassCastException(
          "Target object of type=[%s] does not implement interface method(s)=[%s]"
              .formatted(this.coercionTargetObject.getClass().getName(), notImplementedMethods));
    }

    return true;
  }

  private boolean existsMathingMethodOnTargetObject(final Method interfaceMethod) {
    try {
      this.coercionTargetObject
          .getClass()
          .getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());

      return true;
    } catch (NoSuchMethodException | SecurityException e) {
      return false;
    }
  }

  private <T> T generateProxy(final Class<T> targetInterface) {
    return targetInterface.cast(
        Proxy.newProxyInstance(
            targetInterface.getClassLoader(),
            new Class<?>[] { targetInterface },
            (proxy, method, args) -> coercionTargetObject
                .getClass()
                .getMethod(method.getName(), method.getParameterTypes())
                .invoke(coercionTargetObject, args)));
  }
}
