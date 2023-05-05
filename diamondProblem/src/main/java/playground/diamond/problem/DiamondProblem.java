package playground.diamond.problem;

public class DiamondProblem {

  // Top level interface defines a contract for a method.
  static interface DaddyInterface {
    void method();
  }

  // Two unrelated sub interfaces implement the same method.
  static interface SubDaddyInterfaceA extends DaddyInterface {
    default void method() {
      System.out.println("I'm a default implementation A");
    }
  }

  static interface SubDaddyInterfaceB extends DaddyInterface {
    default void method() {
      System.out.println("I'm a default implementation B");
    }
  }

  // A class that implements both interfaces will have a compilation error.
  // This is because the compiler doesn't know which method to use => the infamous diamond problem.
  static class InheritingClass implements SubDaddyInterfaceA, SubDaddyInterfaceB {
    // This class must implement the method, otherwise it won't compile.
    @Override
    public void method() {
      System.out.println("I'm an overriding implementation");
    }
  }
}
