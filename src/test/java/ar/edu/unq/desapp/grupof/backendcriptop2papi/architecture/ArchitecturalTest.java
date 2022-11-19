package ar.edu.unq.desapp.grupof.backendcriptop2papi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

class ArchitecturalTest {

    @Test
    void serviceClassesShouldOnlyBeAccessedFromServiceOrControllerClasses() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupof.backendcriptop2papi.service");
        ArchRule myRule = classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

        myRule.check(importedClasses);
    }

    @Test
    void servicePublicMethodsShouldHaveTransactionalAnnotation() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupof.backendcriptop2papi.service");

        ArchRule myRule = methods().that()
                .areDeclaredInClassesThat()
                .resideInAnyPackage("..service..")
                .and()
                .arePublic()
                .should()
                .beAnnotatedWith(Transactional.class);

        myRule.check(importedClasses);
    }
}