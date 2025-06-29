package git.immutabled.sulf.velocity.whitelist.listener

import com.velocitypowered.api.event.Subscribe
import git.immutabled.sulf.velocity.template.TemplateModule
import git.immutabled.sulf.velocity.whitelist.WhitelistModule
import git.immutabled.sulf.velocity.whitelist.event.WhitelistDisableEvent

/**
 * @organization Mides Projects
 *
 * @author Inmutable
 * @since abril 07, 18:58
 */
class WhitelistFallbackListener(
    private val module: WhitelistModule,
    private val template: TemplateModule
) {

    @Subscribe
    fun onWhitelistConfig(event: WhitelistDisableEvent) {
        this.template.config.templates.find { it.name.equals(this.module.config.fallbackTemplate, true) }?.let {
            this.template.config.currentTemplate = this.module.config.fallbackTemplate
        }
    }
}