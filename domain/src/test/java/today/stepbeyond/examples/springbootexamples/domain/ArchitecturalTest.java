package today.stepbeyond.examples.springbootexamples.domain;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static today.stepbeyond.examples.springbootexamples.domain.CustomArchUnitChecks.anyAnnotationElseThan;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

class ArchitecturalTest {

  @Test
  void useCasesAreEitherInterfacesOrSpringServices() {
    var importedClasses =
        considerPackagePath("today.stepbeyond.examples.springbootexamples.domain.usecases");

    ArchRule rule = classes().should().beAnnotatedWith(Service.class).orShould().beInterfaces();

    rule.check(importedClasses);
  }

  @Test
  void domainObjectsAreEitherSpringServicesOrNotAnnotated() {
    var importedClasses =
        considerPackagePath("today.stepbeyond.examples.springbootexamples.domain.core");

    ArchRule rule =
        classes()
            .should()
            .beAnnotatedWith(Service.class)
            .orShould()
            .notBeAnnotatedWith(anyAnnotationElseThan(List.of(Service.class)));

    rule.check(importedClasses);
  }

  @Test
  void infrastructureClassesAreEitherInterfacesOrRecords() {
    var importedClasses =
        considerPackagePath("today.stepbeyond.examples.springbootexamples.domain.infrastructure");

    ArchRule rule = classes().should().beInterfaces().orShould().beRecords();

    rule.check(importedClasses);
  }

  private static JavaClasses considerPackagePath(String packagePath) {
    return new ClassFileImporter(List.of(new ImportOption.DoNotIncludeTests()))
        .importPackages(packagePath);
  }
}
