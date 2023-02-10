package today.stepbeyond.examples.springbootexamples;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "today.stepbeyond.examples.springbootexamples")
public class ArchTests {

  @ArchTest
  public static final ArchRule freeOfCycles =
    slices().matching("today.stepbeyond.examples.springbootexamples.(*)..").should().beFreeOfCycles();


  @ArchTest
  public static final ArchRule layering =
    layeredArchitecture()
        .consideringOnlyDependenciesInLayers()
        .layer("App").definedBy("today.stepbeyond.examples.springbootexamples.application..")
        .layer("Domain Core").definedBy("today.stepbeyond.examples.springbootexamples.domain.core..")
        .layer("Domain Use Cases").definedBy("today.stepbeyond.examples.springbootexamples.domain.usecases..")
        .layer("Domain Ports").definedBy("today.stepbeyond.examples.springbootexamples.domain.infrastructure..")
        .layer("Infrastructure").definedBy("today.stepbeyond.examples.springbootexamples.infrastructure..")
        .whereLayer("App").mayNotBeAccessedByAnyLayer()
        .whereLayer("Domain Core").mayNotAccessAnyLayer()
        .whereLayer("Domain Use Cases").mayOnlyBeAccessedByLayers("Infrastructure", "App") // App for Integration Testing
        .whereLayer("Domain Ports").mayOnlyBeAccessedByLayers("Domain Use Cases", "Infrastructure", "App") // App for Integration Testing
        .whereLayer("Infrastructure").mayOnlyAccessLayers("Domain Ports", "Domain Core");

}
