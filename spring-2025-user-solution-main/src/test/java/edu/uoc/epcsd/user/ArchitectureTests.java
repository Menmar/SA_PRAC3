package edu.uoc.epcsd.user;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import static edu.uoc.epcsd.user.util.TestConstants.APPLICATION_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.BASE_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.DOMAIN_EXCEPTION_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.DOMAIN_MODEL_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.DOMAIN_REPOSITORY_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.DOMAIN_SERVICE_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.INFRASTRUCTURE_JPA_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.INFRASTRUCTURE_PACKAGE;
import static edu.uoc.epcsd.user.util.TestConstants.INFRASTRUCTURE_REST_PACKAGE;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

public class ArchitectureTests {


  private static JavaClasses importedClasses;

  @BeforeAll
  static void setUp() {
    importedClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages(BASE_PACKAGE);
  }

  @Test
  @DisplayName("Hexagonal Architecture is Satisfied")
  void hexagonalArchitectureTest() {
    onionPatternTest();
    outerLayers();
    concreteInfrastructureTest();
  }

  //"Onion Pattern"
  void onionPatternTest() {
    ArchRule onionRule = onionArchitecture().withOptionalLayers(true)
        .domainModels(DOMAIN_MODEL_PACKAGE, DOMAIN_EXCEPTION_PACKAGE)
        .domainServices(DOMAIN_SERVICE_PACKAGE, DOMAIN_REPOSITORY_PACKAGE).applicationServices(APPLICATION_PACKAGE)
        .adapter("persistence", INFRASTRUCTURE_JPA_PACKAGE).adapter("rest", INFRASTRUCTURE_REST_PACKAGE)
        .adapter("infrastructure", INFRASTRUCTURE_PACKAGE);

    onionRule.check(importedClasses);
  }

  //"Domain layer should not depend on application or infrastructure layers"
  void outerLayers() {
    ArchRule outerRule = noClasses().that().resideInAPackage(BASE_PACKAGE + ".domain..").should().dependOnClassesThat()
        .resideInAnyPackage(APPLICATION_PACKAGE, INFRASTRUCTURE_PACKAGE)
        .as("Domain layer should not depend on application or infrastructure layers");
    outerRule.check(importedClasses);
  }

  //"Application layer should not depend on concrete infrastructure"
  void concreteInfrastructureTest() {
    ArchRule applicationRule = noClasses().that().resideInAPackage(APPLICATION_PACKAGE).should().dependOnClassesThat()
        .resideInAnyPackage(INFRASTRUCTURE_JPA_PACKAGE, INFRASTRUCTURE_REST_PACKAGE)
        .as("Application layer should not depend on concrete infrastructure");

    applicationRule.check(importedClasses);
  }

  @Test
  @DisplayName("Naming Convention for Service Implementations")
  void serviceImplementationNamingTest() {
    ArchRule rule = classes().that().resideInAPackage(DOMAIN_SERVICE_PACKAGE).and().areAnnotatedWith(Service.class)
        .should().haveSimpleNameEndingWith("ServiceImpl")
        .as("Classes in 'domain.service' annotated with @Service should end with 'ServiceImpl'");

    rule.check(importedClasses);
  }
}
