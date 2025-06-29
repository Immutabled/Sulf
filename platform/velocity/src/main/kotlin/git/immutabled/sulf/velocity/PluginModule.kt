package git.immutabled.sulf.velocity

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 06, 23:04
 */
interface PluginModule {

    fun load(plugin: SulfPlugin)
    fun unload(plugin: SulfPlugin)
}
