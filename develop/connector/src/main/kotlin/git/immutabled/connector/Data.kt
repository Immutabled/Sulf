package git.immutabled.connector

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.PROPERTY_GETTER,AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Data(val id: String, val priority: Int = 4, val forceLocal: Boolean = false)