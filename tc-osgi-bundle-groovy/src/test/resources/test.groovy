import org.tc.osgi.bundle.groovy.module.service.impl.MonInterface

class Sample implements MonInterface {
  String sayIt(String name) { "Groovy says: Cool $name!" }
}
     