package ar.edu.unq.desapp.grupof.backendcriptop2papi.architecture;


import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "ar/edu/unq/desapp/grupof/backendcriptop2papi")
public class DtoRulesTests {
    @ArchTest
    static final ArchRule DTOs_must_reside_in_a_dto_package =
            classes().that().haveNameMatching(".*DTO").should().resideInAPackage("..dto..")
                    .as("DTOs should reside in a package '..dto..'");
}
