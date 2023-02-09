package today.stepbeyond.examples.springbootexamples.domain;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import java.lang.annotation.Annotation;
import java.util.List;

public class CustomArchUnitChecks {

  public static DescribedPredicate<JavaAnnotation> anyAnnotationElseThan(
      List<Class<? extends Annotation>> clazzes) {
    return new AnyAnnotationElseThan(clazzes);
  }

  private static class AnyAnnotationElseThan extends DescribedPredicate<JavaAnnotation> {

    List<Class<? extends Annotation>> annotations;

    private AnyAnnotationElseThan(List<Class<? extends Annotation>> classes) {
      super("any annotation else than {}", classes);
      this.annotations = classes;
    }

    @Override
    public boolean test(JavaAnnotation javaAnnotation) {
      return annotations.stream()
          .map(Class::getSimpleName)
          .noneMatch(
              annotationName ->
                  annotationName.equals(javaAnnotation.getType().toErasure().getName()));
    }
  }
}
