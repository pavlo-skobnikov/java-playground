package playground.duck.typing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TypeCoercerTest {

  @Test
  void coerce_toMutlipleTypesWithSingleCoercer_successful() {
    final FooBar fooBar = new FooBar();

    final TypeCoercer fooBarCoercer = TypeCoercer.of(fooBar);

    final Foo foo = fooBarCoercer.to(Foo.class);
    final Bar bar = fooBarCoercer.to(Bar.class);

    Assertions.assertEquals("foo", foo.foo());
    Assertions.assertEquals("bar", bar.bar());
  }

  @Test
  void coerce_whenGivenNull_throws() {
    final FooBar fooBar = new FooBar();

    final TypeCoercer fooBarCoercer = TypeCoercer.of(fooBar);

    Assertions.assertThrows(IllegalArgumentException.class, () -> fooBarCoercer.to(null));
  }

  @Test
  void coerce_whenGivenClassInsteadOfInterface_throws() {
    final FooBar fooBar = new FooBar();

    final TypeCoercer fooBarCoercer = TypeCoercer.of(fooBar);

    Assertions.assertThrows(IllegalArgumentException.class, () -> fooBarCoercer.to(Object.class));
  }

  @Test
  void coerce_whenGivenNotImplementedInterface_throws() {
    final FooBar fooBar = new FooBar();

    final TypeCoercer fooBarCoercer = TypeCoercer.of(fooBar);

    Assertions.assertThrows(ClassCastException.class, () -> fooBarCoercer.to(Baz.class));
  }

  class FooBar {
    public String foo() {
      return "foo";
    }

    public String bar() {
      return "bar";
    }
  }

  interface Foo {
    String foo();
  }

  interface Bar {
    String bar();
  }

  interface Baz {
    String baz();
  }
}
