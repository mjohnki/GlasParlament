package de.glasparlament.organization

import android.widget.TextView
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import org.jetbrains.annotations.NotNull

class OrganizationItemModule : ItemModule<BodyOrganization>() {

    @NotNull
    override fun provideModuleConfig(): ItemModuleConfig {
        return object : ItemModuleConfig() {
            override fun withLayoutResource(): Int {
                return R.layout.organization_list_item
            }
        }
    }

    override fun onBind(@NotNull model: BodyOrganization, @NotNull viewBinder: ViewBinder) {
        val organizationName: TextView = viewBinder.findViewById(R.id.organizationName)
        organizationName.text = model.name
    }
}
