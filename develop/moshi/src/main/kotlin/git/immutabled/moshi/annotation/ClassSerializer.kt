package git.immutabled.moshi.annotation

import com.squareup.moshi.JsonQualifier

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD,AnnotationTarget.FUNCTION,AnnotationTarget.TYPE_PARAMETER,AnnotationTarget.VALUE_PARAMETER)
@JsonQualifier
annotation class ClassSerializer