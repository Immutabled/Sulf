package git.immutabled.moshi

import com.squareup.moshi.JsonAdapter.Factory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import git.immutabled.moshi.adapter.*
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType
import java.util.function.Consumer

object MoshiUtil {

    private val builder: Moshi.Builder = Moshi.Builder()
        .add(UUIDJsonAdapter)
        .add(ListJsonAdapter)
        .add(git.immutabled.moshi.adapter.PairJsonAdapter)
        .add(ClassJsonAdapter)
        .add(JsonObjectAdapter)
        .add(SerializeNullAdapter)
        .add(IntRangeJsonAdapter)
        .add(AtomicIntegerJsonAdapter)
        .add(ColorJsonAdapter)
        .add(LocalDateTimeJsonAdapter)
        .addLast(KotlinJsonAdapterFactory())

    var instance: Moshi = builder
        .build()

    private var factories = getFactories().toMutableList()

    fun rebuild(use: Consumer<Moshi.Builder>) {
        use.accept(builder)

        instance = builder.build()
        factories = getFactories().toMutableList()
    }

    fun <T> addToPolymorphic(instance: Class<T>,type: Class<out T>,label: String) {

        val indexedValue = factories
            .withIndex()
            .firstOrNull{it.value is PolymorphicJsonAdapterFactory<*> && ReflectionUtil.getDeclaredField(it.value,"baseType") == instance}
            ?: throw IllegalStateException("Failed to find Polymorphic adapter for ${instance.simpleName}")

        val factory = (indexedValue.value as PolymorphicJsonAdapterFactory<T>).withSubtype(type,label)

        factories[indexedValue.index] = factory
        setFactories(factories)
    }

    fun setFactories(factories: List<Factory>) {
        ReflectionUtil.setDeclaredField(builder,"factories",factories)
        instance = builder.build()
    }

    fun getFactories():List<Factory> {
        return ReflectionUtil.getDeclaredField(builder,"factories") as List<Factory>
    }

    fun isJsonObject(value: String):Boolean {

        if (value.length < 2) {
            return false
        }

        return value[0] == '{' && value[value.lastIndex] == '}'
    }

    val LIST_STRING_TYPE: ParameterizedType = Types.newParameterizedType(List::class.java,String::class.java)
    val STRING_TO_ANY_MAP_TYPE: ParameterizedType = Types.newParameterizedType(Map::class.java,String::class.java,Any::class.java)

    const val PRETTY_PRINT_INDENT = "    "
}